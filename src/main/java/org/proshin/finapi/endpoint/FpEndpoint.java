/*
 * Copyright 2018 Roman Proshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.proshin.finapi.endpoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeaderElement;
import org.cactoos.io.InputOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.proshin.finapi.AccessToken;
import org.proshin.finapi.Endpoint;
import org.proshin.finapi.exception.UnexpectedStatusCode;

public final class FpEndpoint implements Endpoint {

    private final HttpClient client;
    private final String endpoint;

    public FpEndpoint(final String endpoint) {
        this(HttpClientBuilder.create().build(), endpoint);
    }

    public FpEndpoint(final HttpClient client, final String endpoint) {
        this.client = client;
        this.endpoint = endpoint;
    }

    @Override
    public HttpClient client() {
        return this.client;
    }

    @Override
    public String get(final String path, final AccessToken token) {
        final HttpGet get = new HttpGet(this.endpoint + path);
        get.addHeader(new AuthorizationHeader(token.accessToken()));
        try {
            final HttpResponse response = this.client.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new UnexpectedStatusCode(200, response.getStatusLine().getStatusCode());
            }
            return new TextOf(
                new InputOf(
                    response.getEntity().getContent()
                ),
                StandardCharsets.UTF_8
            ).asString();
        } catch (IOException e) {
            throw new RuntimeException(
                new UncheckedText(
                    new FormattedText(
                        "Couldn't get '%s'",
                        path
                    )
                ).asString(),
                e
            );
        }
    }

    @Override
    public void delete(final String path, final AccessToken token) {
        try {
            final HttpDelete delete = new HttpDelete(this.endpoint + path);
            delete.addHeader(new AuthorizationHeader(token.accessToken()));
            final HttpResponse response = this.client.execute(delete);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new UnexpectedStatusCode(200, response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Couldn't delete '%s'",
                        path
                    )
                ).asString()
            );
        }
    }

    @Override
    public HttpPost post(String path) {
        return new HttpPost(this.endpoint + path);
    }

    @Override
    public HttpPost post(String path, AccessToken token) {
        final HttpPost post = this.post(path);
        post.addHeader(new AuthorizationHeader(token.accessToken()));
        return post;
    }

    @Override
    public String post(final String path, final AccessToken token, final HttpEntity entity, final int expected) {
        try {
            final HttpPost post = new HttpPost(this.endpoint + path);
            post.addHeader(new AuthorizationHeader(token.accessToken()));
            post.setEntity(entity);
            final HttpResponse response = this.client.execute(post);
            if (response.getStatusLine().getStatusCode() != expected) {
                throw new UnexpectedStatusCode(expected, response.getStatusLine().getStatusCode());
            }
            return new TextOf(
                new InputOf(response.getEntity().getContent()),
                StandardCharsets.UTF_8
            ).asString();
        } catch (IOException e) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Couldn't post to '%s'",
                        path
                    )
                ).asString()
            );
        }
    }

    private static class AuthorizationHeader implements Header {

        private final String token;

        private AuthorizationHeader(final String token) {
            this.token = token;
        }

        @Override
        public HeaderElement[] getElements() throws ParseException {
            return new HeaderElement[]{
                new BasicHeaderElement(this.getName(), this.getValue())
            };
        }

        @Override
        public String getName() {
            return "Authorization";
        }

        @Override
        public String getValue() {
            return "Bearer " + this.token;
        }
    }
}
