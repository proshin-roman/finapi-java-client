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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.proshin.finapi.endpoint.Endpoint;
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
    public ClientAccessToken clientToken(final String clientId, final String clientSecret) {
        final List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", clientId));
        parameters.add(new BasicNameValuePair("client_secret", clientSecret));
        final UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(parameters);
        } catch (final UnsupportedEncodingException exception) {
            throw new RuntimeException("Couldn't instantiate an entity for POST request", exception);
        }
        return new FpClientAccessToken(
            new JSONObject(
                this.endpoint.post(this.tokenUrl, entity, 200)
            )
        );
    }

    @Override
    public UserAccessToken userToken(
        final String clientId,
        final String clientSecret,
        final String username,
        final String password
    ) {
        final List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new UrlEncodedPair("grant_type", "password"));
        parameters.add(new UrlEncodedPair("client_id", clientId));
        parameters.add(new UrlEncodedPair("client_secret", clientSecret));
        parameters.add(new UrlEncodedPair("username", username));
        parameters.add(new UrlEncodedPair("password", password));
        final UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(parameters);
        } catch (final UnsupportedEncodingException exception) {
            throw new RuntimeException("Couldn't instantiate an entity for POST request", exception);
        }
        return new FpUserAccessToken(
            new JSONObject(
                this.endpoint.post(this.tokenUrl, entity, 200)
            )
        );
    }

    @Override
    public UserAccessToken userToken(final String clientId, final String clientSecret, final String refreshToken) {
        final List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new UrlEncodedPair("grant_type", "refresh_token"));
        parameters.add(new UrlEncodedPair("client_id", clientId));
        parameters.add(new UrlEncodedPair("client_secret", clientSecret));
        parameters.add(new UrlEncodedPair("refresh_token", refreshToken));
        final UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(parameters);
        } catch (final UnsupportedEncodingException exception) {
            throw new RuntimeException("Couldn't instantiate an entity for POST request", exception);
        }
        return new FpUserAccessToken(
            new JSONObject(
                this.endpoint.post(this.tokenUrl, entity, 200)
            )
        );
    }

    /**
     * Revoke user's access token.
     *
     * @param clientToken Client's token for authorization.
     * @param userToken User's token for revoking.
     * @param tokensToRevoke Which tokens should be revoked.
     * @todo #32 Test this method: assert that methods submits the right request
     */
    @Override
    public void revoke(
        final ClientAccessToken clientToken,
        final UserAccessToken userToken,
        final RevokeToken tokensToRevoke
    ) {
        final List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("token", userToken.accessToken()));
        switch (tokensToRevoke) {
            case ACCESS_TOKEN_ONLY: {
                parameters.add(new BasicNameValuePair("token_type_hint", "access_token"));
                break;
            }
            case REFRESH_TOKEN_ONLY: {
                parameters.add(new BasicNameValuePair("token_type_hint", "refresh_token"));
                break;
            }
            case ALL:
            default: {
                // do nothing: keep parameter empty
            }
        }
        final UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(parameters);
        } catch (final UnsupportedEncodingException exception) {
            throw new RuntimeException("Couldn't instantiate an entity for POST request", exception);
        }
        this.endpoint.post(this.revokeUrl, clientToken, entity, 200);
    }
}
