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
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicNameValuePair;
import org.cactoos.io.InputOf;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.accesstoken.ClientAccessToken;
import org.proshin.finapi.accesstoken.UserAccessToken;
import org.proshin.finapi.exception.FinapiException;

@Slf4j
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
    public String get(final String path, final AccessToken token, final Iterable<NameValuePair> parameters) {
        try {
            URIBuilder builder = new URIBuilder(this.endpoint + path);
            builder.setParameters(new ListOf<>(parameters));
            final HttpGet get = new HttpGet(builder.build());
            get.addHeader(new AuthorizationHeader(token.accessToken()));
            final HttpResponse response = this.client.execute(get);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new FinapiException(200, response);
            }
            final String responseBody = new TextOf(
                new InputOf(response.getEntity().getContent()),
                StandardCharsets.UTF_8
            ).asString();
            log.info("Response body was: {}", responseBody);
            return responseBody;
        } catch (final IOException | URISyntaxException e) {
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
                throw new FinapiException(200, response);
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
                throw new FinapiException(expected, response);
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

    @Override
    public AccessToken clientToken(final String clientId, final String clientSecret) {
        try {
            final HttpPost post = new HttpPost(this.endpoint + "/oauth/token");
            final List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
            parameters.add(new BasicNameValuePair("client_id", clientId));
            parameters.add(new BasicNameValuePair("client_secret", clientSecret));
            post.setEntity(new UrlEncodedFormEntity(parameters));
            final HttpResponse response = this.client.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new FinapiException(200, response);
            }
            return new ClientAccessToken(new JSONObject(
                new TextOf(
                    new InputOf(response.getEntity().getContent()),
                    StandardCharsets.UTF_8
                ).asString()
            )
            );
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get an access token", e);
        }
    }

    @Override
    public AccessToken userToken(final String clientId, final String clientSecret, final String username,
        final String password) {
        try {
            final HttpPost post = new HttpPost(this.endpoint + "/oauth/token");
            final List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair("grant_type", "password"));
            parameters.add(new BasicNameValuePair("client_id", clientId));
            parameters.add(new BasicNameValuePair("client_secret", clientSecret));
            parameters.add(new BasicNameValuePair("username", username));
            parameters.add(new BasicNameValuePair("password", password));
            post.setEntity(new UrlEncodedFormEntity(parameters));
            final HttpResponse response = this.client.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new FinapiException(
                    200,
                    response.getStatusLine().getStatusCode(),
                    new JSONObject(
                        new TextOf(
                            new InputOf(response.getEntity().getContent()),
                            StandardCharsets.UTF_8
                        ).asString()
                    )
                );
            }
            return new UserAccessToken(new JSONObject(
                new TextOf(
                    new InputOf(response.getEntity().getContent()),
                    StandardCharsets.UTF_8
                ).asString()
            )
            );
        } catch (IOException e) {
            throw new RuntimeException("Couldn't access the endpoint due to technical problem", e);
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
