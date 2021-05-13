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

import java.util.Optional;
import org.apache.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.Parameter;
import org.mockserver.model.ParameterBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

final class FpAccessTokensTest extends TestWithMockedEndpoint {

    @Test
    void testThatClientTokenReturnsValidToken() {
        final String clientId = "client ID #1";
        final String clientSecret = "client secret #1";
        this.server()
            .when(
                HttpRequest.request("/oauth/token")
                    .withMethod("POST")
                    .withBody(
                        new ParameterBody(
                            new Parameter("grant_type", "client_credentials"),
                            new Parameter("client_id", clientId),
                            new Parameter("client_secret", clientSecret)
                        )))
            .respond(
                HttpResponse.response(
                    String.join("",
                        "{",
                        "\"access_token\": \"access token\",",
                        "\"token_type\": \"bearer\",",
                        "\"expires_in\": 156,",
                        "\"scope\": \"all\"",
                        "}"
                    )
                )
            );
        final AccessToken token = new FpAccessTokens(this.endpoint()).clientToken(clientId, clientSecret);
        assertThat(token.accessToken()).isEqualTo("access token");
        assertThat(token.tokenType()).isEqualTo("bearer");
        assertThat(token.refreshToken()).isNotPresent();
        assertThat(token.expiresIn()).isEqualTo(156);
        assertThat(token.scope()).isEqualTo("all");
    }

    @Test
    void testThatUserTokenReturnsValidToken() {
        final String clientId = "client ID #2";
        final String clientSecret = "client secret #2";
        final String username = "username #2";
        final String password = "password #2";
        this.server()
            .when(
                HttpRequest.request("/oauth/token")
                    .withMethod("POST")
                    .withBody(
                        new ParameterBody(
                            new Parameter("grant_type", "password"),
                            new Parameter("client_id", clientId),
                            new Parameter("client_secret", clientSecret),
                            new Parameter("username", username),
                            new Parameter("password", password)
                        )))
            .respond(
                HttpResponse.response(
                    String.join("",
                        "{",
                        "\"access_token\": \"access token\",",
                        "\"token_type\": \"bearer\",",
                        "\"refresh_token\": \"refresh token\",",
                        "\"expires_in\": 156,",
                        "\"scope\": \"all\"",
                        "}"
                    )
                )
            );
        final AccessToken token = new FpAccessTokens(this.endpoint())
            .userToken(clientId, clientSecret, username, password);
        assertThat(token.accessToken()).isEqualTo("access token");
        assertThat(token.tokenType()).isEqualTo("bearer");
        assertThat(token.refreshToken()).isEqualTo(Optional.of("refresh token"));
        assertThat(token.expiresIn()).isEqualTo(156);
        assertThat(token.scope()).isEqualTo("all");
    }

    @Test
    void testGettingUserTokenUsingRefreshToken() {
        final String clientId = "client ID #2";
        final String clientSecret = "client secret #2";
        final String refreshToken = "refresh token";
        this.server()
            .when(
                HttpRequest.request("/oauth/token")
                    .withMethod("POST")
                    .withBody(
                        new ParameterBody(
                            new Parameter("grant_type", "refresh_token"),
                            new Parameter("client_id", clientId),
                            new Parameter("client_secret", clientSecret),
                            new Parameter("refresh_token", refreshToken)
                        )))
            .respond(
                HttpResponse.response(
                    String.join("",
                        "{",
                        "\"access_token\": \"access token\",",
                        "\"token_type\": \"bearer\",",
                        "\"refresh_token\": \"refresh token\",",
                        "\"expires_in\": 156,",
                        "\"scope\": \"all\"",
                        "}"
                    )
                )
            );
        final AccessToken token = new FpAccessTokens(this.endpoint())
            .userToken(clientId, clientSecret, refreshToken);
        assertThat(token.accessToken()).isEqualTo("access token");
        assertThat(token.tokenType()).isEqualTo("bearer");
        assertThat(token.refreshToken()).isEqualTo(Optional.of("refresh token"));
        assertThat(token.expiresIn()).isEqualTo(156);
        assertThat(token.scope()).isEqualTo("all");
    }

    @Test
    void testRevokeTokenAccessTokenOnly() {
        final String clientId = "client ID #2";
        final String clientSecret = "client secret #2";
        final String refreshToken = "refresh token";
        this.server()
            .when(
                HttpRequest.request("/oauth/revoke")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer client-token")
                    .withBody(
                        new ParameterBody(
                            new Parameter("token", "user-token"),
                            new Parameter("token_type_hint", "access_token")
                        )))
            .respond(
                HttpResponse.response().withStatusCode(HttpStatus.SC_OK)
            );
        new FpAccessTokens(this.endpoint())
            .revoke(
                new FakeAccessToken("client-token"),
                new FakeAccessToken("user-token"),
                AccessTokens.RevokeToken.ACCESS_TOKEN_ONLY
            );
    }

    @Test
    void testRevokeTokenRefreshTokenOnly() {
        final String clientId = "client ID #2";
        final String clientSecret = "client secret #2";
        final String refreshToken = "refresh token";
        this.server()
            .when(
                HttpRequest.request("/oauth/revoke")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer client-token")
                    .withBody(
                        new ParameterBody(
                            new Parameter("token", "user-token"),
                            new Parameter("token_type_hint", "refresh_token")
                        )))
            .respond(
                HttpResponse.response().withStatusCode(HttpStatus.SC_OK)
            );
        new FpAccessTokens(this.endpoint())
            .revoke(
                new FakeAccessToken("client-token"),
                new FakeAccessToken("user-token"),
                AccessTokens.RevokeToken.REFRESH_TOKEN_ONLY
            );
    }
}
