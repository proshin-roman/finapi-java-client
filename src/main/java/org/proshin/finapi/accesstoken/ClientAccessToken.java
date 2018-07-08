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
package org.proshin.finapi.accesstoken;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.cactoos.io.InputOf;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.TextOf;
import org.json.JSONObject;
import org.proshin.finapi.AccessToken;
import org.proshin.finapi.Endpoint;
import org.proshin.finapi.exception.UnexpectedStatusCode;

public final class ClientAccessToken implements AccessToken {

    private final UncheckedScalar<JSONObject> origin;

    public ClientAccessToken(final Endpoint endpoint, final String clientId, final String clientSecret) {
        this(
            new UncheckedScalar<>(
                new StickyScalar<>(() -> {
                    try {
                        final HttpPost post = endpoint.post("/oauth/token");
                        final List<NameValuePair> parameters = new ArrayList<>();
                        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
                        parameters.add(new BasicNameValuePair("client_id", clientId));
                        parameters.add(new BasicNameValuePair("client_secret", clientSecret));
                        post.setEntity(new UrlEncodedFormEntity(parameters));
                        final HttpResponse response = endpoint.client().execute(post);
                        if (response.getStatusLine().getStatusCode() != 200) {
                            throw new UnexpectedStatusCode(200, response.getStatusLine().getStatusCode());
                        }
                        return new JSONObject(
                            new TextOf(
                                new InputOf(response.getEntity().getContent()),
                                StandardCharsets.UTF_8
                            ).asString()
                        );
                    } catch (Exception e) {
                        throw new RuntimeException("Couldn't get an access token", e);
                    }
                })
            )
        );
    }

    public ClientAccessToken(final UncheckedScalar<JSONObject> origin) {
        this.origin = origin;
    }

    @Override
    public String accessToken() {
        return this.origin.value().getString("access_token");
    }

    @Override
    public String tokenType() {
        return this.origin.value().getString("token_type");
    }

    @Override
    public Optional<String> refreshToken() {
        return Optional.empty();
    }

    @Override
    public int expiresIn() {
        return this.origin.value().getInt("expires_in");
    }

    @Override
    public String scope() {
        return this.origin.value().getString("scope");
    }
}
