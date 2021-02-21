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
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeaderElement;
import org.cactoos.io.InputOf;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.exception.FinapiAuthenticationException;
import org.proshin.finapi.exception.FinapiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FpEndpoint implements Endpoint {

    @SuppressWarnings("staticfree")
    private static final Logger LOGGER = LoggerFactory.getLogger(FpEndpoint.class);

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
    public String get(final String path, final AccessToken token, final Iterable<NameValuePair> parameters) {
        try {
            final URIBuilder builder = new URIBuilder(this.endpoint + path);
            builder.setParameters(new ListOf<>(parameters));
            final HttpUriRequest get = new HttpGet(builder.build());
            get.addHeader(new AuthorizationHeader(token.accessToken()));
            return this.execute(get);
        } catch (final URISyntaxException e) {
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
    public String delete(final String path, final AccessToken token, final Iterable<NameValuePair> parameters) {
        try {
            final URIBuilder builder = new URIBuilder(this.endpoint + path);
            builder.setParameters(new ListOf<>(parameters));
            final HttpUriRequest delete = new HttpDelete(builder.build());
            delete.addHeader(new AuthorizationHeader(token.accessToken()));
            return this.execute(delete);
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(
                new UncheckedText(
                    new FormattedText(
                        "Couldn't delete '%s'",
                        path
                    )
                ).asString(),
                e
            );
        }
    }

    @Override
    public HttpPost post(final String path) {
        return new HttpPost(this.endpoint + path);
    }

    /**
     * @param path Path to make a request to.
     * @param entity Body of the request.
     * @param expected Expected result code.
     * @return a body of the respose in JSON format
     * @todo #16 Remove duplicated code by refactoring all "post..." methods into a single method or even a class.
     */
    @Override
    public String post(final String path, final HttpEntity entity, final int expected) {
        final HttpPost post = new HttpPost(this.endpoint + path);
        post.setEntity(entity);
        return this.execute(post, expected);
    }

    @Override
    public HttpPost post(final String path, final AccessToken token) {
        final HttpPost post = this.post(path);
        post.addHeader(new AuthorizationHeader(token.accessToken()));
        return post;
    }

    @Override
    public String post(final String path, final AccessToken token, final int expected) {
        final HttpUriRequest post = new HttpPost(this.endpoint + path);
        post.addHeader(new AuthorizationHeader(token.accessToken()));
        return this.execute(post, expected);
    }

    @Override
    public String post(final String path, final AccessToken token, final HttpEntity entity, final int expected) {
        final HttpPost post = new HttpPost(this.endpoint + path);
        post.addHeader(new AuthorizationHeader(token.accessToken()));
        post.setEntity(entity);
        return this.execute(post, expected);
    }

    @Override
    public String patch(final String path, final AccessToken token, final HttpEntity entity, final int expected) {
        final HttpPatch patch = new HttpPatch(this.endpoint + path);
        patch.addHeader(new AuthorizationHeader(token.accessToken()));
        patch.setEntity(entity);
        return this.execute(patch, expected);
    }

    @Override
    public String patch(final String path, final AccessToken token, final Jsonable body) {
        return this.patch(
            path, token,
            new StringEntity(
                body.asString(),
                ContentType.APPLICATION_JSON
            ),
            HttpStatus.SC_OK
        );
    }

    private String execute(final HttpUriRequest request) {
        return this.execute(request, HttpStatus.SC_OK);
    }

    private String execute(final HttpUriRequest request, final int expected) {
        try {
            final HttpResponse response = this.client.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
                throw new FinapiAuthenticationException(response);
            } else if (statusCode != expected) {
                throw new FinapiException(expected, response);
            }
            return new UncheckedText(
                new TextOf(
                    new InputOf(response.getEntity().getContent()),
                    StandardCharsets.UTF_8
                )
            ).asString();
        } catch (final IOException e) {
            throw new RuntimeException(
                new UncheckedText(
                    new FormattedText(
                        "Couldn't get '%s'",
                        request.getURI()
                    )
                ).asString(),
                e
            );
        }
    }

    @SuppressWarnings("staticfree")
    private static final class AuthorizationHeader implements Header {

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
