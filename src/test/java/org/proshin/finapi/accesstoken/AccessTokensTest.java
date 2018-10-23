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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.proshin.finapi.exception.NoFieldException;
import org.proshin.finapi.fake.FakeEndpoint;
import org.proshin.finapi.fake.FakeRoute;

public final class AccessTokensTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatClientTokenReturnsValidToken() {
        final AccessToken token = new FpAccessTokens(
            new FakeEndpoint(
                new FakeRoute(
                    String.join("",
                        "{",
                        "\"access_token\": \"access token\",",
                        "\"token_type\": \"bearer\",",
                        "\"expires_in\": 156,",
                        "\"scope\": \"all\"",
                        "}"
                    )
                )
            )
        ).clientToken("client ID #1", "client secret #1");

        assertThat(token.accessToken(), is("access token"));
        assertThat(token.tokenType(), is("bearer"));
        assertThat(token.refreshToken().isPresent(), is(false));
        assertThat(token.expiresIn(), is(156));
        assertThat(token.scope(), is("all"));
    }

    @Test
    public void testThatUserTokenReturnsValidToken() {
        final AccessToken token = new FpAccessTokens(
            new FakeEndpoint(
                new FakeRoute(
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
            )
        ).userToken("client ID #2", "client secret #2", "username #2", "password #2");

        assertThat(token.accessToken(), is("access token"));
        assertThat(token.tokenType(), is("bearer"));
        assertThat(token.refreshToken(), is(Optional.of("refresh token")));
        assertThat(token.expiresIn(), is(156));
        assertThat(token.scope(), is("all"));
    }

    @Test
    public void testThatUserTokenFailsIfNoRefreshTokenIsSent() {
        final AccessToken token = new FpAccessTokens(
            new FakeEndpoint(
                new FakeRoute(
                    String.join("",
                        "{",
                        "\"access_token\": \"access token\",",
                        "\"token_type\": \"bearer\",",
                        "\"expires_in\": 156,",
                        "\"scope\": \"all\"",
                        "}"
                    )
                )
            )
        ).userToken("client ID #3", "client secret #3", "username #3", "password #3");

        assertThat(token.accessToken(), is("access token"));
        assertThat(token.tokenType(), is("bearer"));
        assertThat(token.expiresIn(), is(156));
        assertThat(token.scope(), is("all"));

        this.expectedException.expect(NoFieldException.class);
        this.expectedException.expectMessage("Field 'refresh_token' may not be null for user's access token");
        token.refreshToken();
    }
}
