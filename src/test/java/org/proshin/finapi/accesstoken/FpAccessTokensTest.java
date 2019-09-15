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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

public final class FpAccessTokensTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10013);
    }

    @Before
    public void reset() {
        server.reset();
    }

    @AfterClass
    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    public static void stopMockServer() {
        server.stop();
    }

    @Test
    public void testThatClientTokenReturnsValidToken() {
        final String clientId = "client ID #1";
        final String clientSecret = "client secret #1";
        server.when(
            HttpRequest.request("/oauth/token")
                .withMethod("POST")
                .withQueryStringParameter("grant_type", "client_credentials")
                .withQueryStringParameter("client_id", clientId)
                .withQueryStringParameter("client_secret", clientSecret)
        ).respond(
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
        final AccessToken token =
            new FpAccessTokens(
                new FpEndpoint("http://127.0.0.1:10013")
            ).clientToken(clientId, clientSecret);
        assertThat(token.accessToken(), is("access token"));
        assertThat(token.tokenType(), is("bearer"));
        assertThat(token.refreshToken().isPresent(), is(false));
        assertThat(token.expiresIn(), is(156));
        assertThat(token.scope(), is("all"));
    }

    @Test
    public void testThatUserTokenReturnsValidToken() {
        final String clientId = "client ID #2";
        final String clientSecret = "client secret #2";
        final String username = "username #2";
        final String password = "password #2";
        server.when(
            HttpRequest.request("/oauth/token")
                .withMethod("POST")
                .withQueryStringParameter("grant_type", "password")
                .withQueryStringParameter("client_id", clientId)
                .withQueryStringParameter("client_secret", clientSecret)
                .withQueryStringParameter("username", username)
                .withQueryStringParameter("password", password)
        ).respond(
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
        final AccessToken token = new FpAccessTokens(
            new FpEndpoint("http://127.0.0.1:10013")
        ).userToken(clientId, clientSecret, username, password);
        assertThat(token.accessToken(), is("access token"));
        assertThat(token.tokenType(), is("bearer"));
        assertThat(token.refreshToken(), is(Optional.of("refresh token")));
        assertThat(token.expiresIn(), is(156));
        assertThat(token.scope(), is("all"));
    }

    @Test
    public void testGettingUserTokenUsingRefreshToken() {
        final String clientId = "client ID #2";
        final String clientSecret = "client secret #2";
        final String refreshToken = "refresh token";
        server.when(
            HttpRequest.request("/oauth/token")
                .withMethod("POST")
                .withQueryStringParameter("grant_type", "refresh_token")
                .withQueryStringParameter("client_id", clientId)
                .withQueryStringParameter("client_secret", clientSecret)
                .withQueryStringParameter("refresh_token", refreshToken)
        ).respond(
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
        final AccessToken token = new FpAccessTokens(
            new FpEndpoint("http://127.0.0.1:10013")
        ).userToken(clientId, clientSecret, refreshToken);
        assertThat(token.accessToken(), is("access token"));
        assertThat(token.tokenType(), is("bearer"));
        assertThat(token.refreshToken(), is(Optional.of("refresh token")));
        assertThat(token.expiresIn(), is(156));
        assertThat(token.scope(), is("all"));
    }

    @Test
    public void testRevokeTokenAccessTokenOnly() {
        final String clientId = "client ID #2";
        final String clientSecret = "client secret #2";
        final String refreshToken = "refresh token";
        server.when(
            HttpRequest.request("/oauth/revoke")
                .withMethod("POST")
                .withHeader("Authorization", "Bearer client-token")
                .withQueryStringParameter("token", "user-token")
                .withQueryStringParameter("token_type_hint", "access_token")
        ).respond(
            HttpResponse.response().withStatusCode(200)
        );
        new FpAccessTokens(
            new FpEndpoint("http://127.0.0.1:10013")
        ).revoke(
            new FakeAccessToken("client-token"),
            new FakeAccessToken("user-token"),
            AccessTokens.RevokeToken.ACCESS_TOKEN_ONLY
        );
    }

    @Test
    public void testRevokeTokenRefreshTokenOnly() {
        final String clientId = "client ID #2";
        final String clientSecret = "client secret #2";
        final String refreshToken = "refresh token";
        server.when(
            HttpRequest.request("/oauth/revoke")
                .withMethod("POST")
                .withHeader("Authorization", "Bearer client-token")
                .withQueryStringParameter("token", "user-token")
                .withQueryStringParameter("token_type_hint", "refresh_token")
        ).respond(
            HttpResponse.response().withStatusCode(200)
        );
        new FpAccessTokens(
            new FpEndpoint("http://127.0.0.1:10013")
        ).revoke(
            new FakeAccessToken("client-token"),
            new FakeAccessToken("user-token"),
            AccessTokens.RevokeToken.REFRESH_TOKEN_ONLY
        );
    }
}
