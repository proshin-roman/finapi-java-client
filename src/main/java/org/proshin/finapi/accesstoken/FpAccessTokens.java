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
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.QueryString;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class FpAccessTokens implements AccessTokens {

    private final Endpoint endpoint;
    private final String tokenUrl;
    private final String revokeUrl;

    public FpAccessTokens(final Endpoint endpoint) {
        this(endpoint, "/oauth/token", "/oauth/revoke");
    }

    public FpAccessTokens(final Endpoint endpoint, final String tokenUrl, final String revokeUrl) {
        this.endpoint = endpoint;
        this.tokenUrl = tokenUrl;
        this.revokeUrl = revokeUrl;
    }

    @Override
    public AccessToken clientToken(final String clientId, final String clientSecret) {
        return new ClientAccessToken(
            new JSONObject(
                this.endpoint.post(
                    this.tokenUrl + '?' +
                        new QueryString(
                            new UrlEncodedPair("grant_type", "client_credentials"),
                            new UrlEncodedPair("client_id", clientId),
                            new UrlEncodedPair("client_secret", clientSecret)
                        ).get(),
                    new StringEntity(
                        "",
                        ContentType.APPLICATION_JSON
                    ),
                    HttpStatus.SC_OK
                )
            )
        );
    }

    @Override
    public AccessToken userToken(
        final String clientId,
        final String clientSecret,
        final String username,
        final String password
    ) {
        return new UserAccessToken(
            new JSONObject(
                this.endpoint.post(
                    this.tokenUrl + '?' +
                        new QueryString(
                            new UrlEncodedPair("grant_type", "password"),
                            new UrlEncodedPair("client_id", clientId),
                            new UrlEncodedPair("client_secret", clientSecret),
                            new UrlEncodedPair("username", username),
                            new UrlEncodedPair("password", password)
                        ).get(),
                    new StringEntity(
                        "",
                        ContentType.APPLICATION_JSON
                    ),
                    HttpStatus.SC_OK
                )
            )
        );
    }

    @Override
    public AccessToken userToken(final String clientId, final String clientSecret, final String refreshToken) {
        return new UserAccessToken(
            new JSONObject(
                this.endpoint.post(
                    this.tokenUrl + '?' +
                        new QueryString(
                            new UrlEncodedPair("grant_type", "refresh_token"),
                            new UrlEncodedPair("client_id", clientId),
                            new UrlEncodedPair("client_secret", clientSecret),
                            new UrlEncodedPair("refresh_token", refreshToken)
                        ).get(),
                    new StringEntity(
                        "",
                        ContentType.APPLICATION_JSON
                    ),
                    HttpStatus.SC_OK
                )
            )
        );
    }

    /**
     * Revoke user's access token.
     *
     * @param clientToken Client's token for authorization.
     * @param userToken User's token for revoking.
     * @param tokensToRevoke Which tokens should be revoked.
     */
    @Override
    public void revoke(final AccessToken clientToken, final AccessToken userToken, final RevokeToken tokensToRevoke) {
        final List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("token", userToken.accessToken()));
        switch (tokensToRevoke) {
            case ACCESS_TOKEN_ONLY: {
                parameters.add(new UrlEncodedPair("token_type_hint", "access_token"));
                break;
            }
            case REFRESH_TOKEN_ONLY: {
                parameters.add(new UrlEncodedPair("token_type_hint", "refresh_token"));
                break;
            }
            case ALL:
            default: {
                // do nothing: keep parameter empty
            }
        }
        this.endpoint.post(
            this.revokeUrl + '?' + new QueryString(parameters).get(),
            clientToken,
            new StringEntity(
                "",
                ContentType.create("application/json", StandardCharsets.UTF_8)
            ),
            HttpStatus.SC_OK
        );
    }
}
