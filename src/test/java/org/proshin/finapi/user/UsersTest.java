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
package org.proshin.finapi.user;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.user.in.FpCreateParameters;

public final class UsersTest extends TestWithMockedEndpoint {

    @Test
    public void testAuthorized() {
        this.server()
            .when(
                request("/api/v1/users")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer random-token")
            )
            .respond(
                response("{}")
            );
        new FpUsers(
            this.endpoint(),
            new FakeAccessToken("random-token")
        ).authorized();
    }

    @Test
    public void testVerified() {
        this.server()
            .when(
                request("/api/v1/users/verificationStatus")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer fake token")
                    .withQueryStringParameter("userId", "user+id")
            )
            .respond(
                response('{' +
                    "  \"userId\": \"username\"," +
                    "  \"isUserVerified\": true" +
                    '}')
            );
        new FpUsers(
            this.endpoint(),
            new FakeAccessToken("fake token")
        ).verified("user id");
    }

    @Test
    public void testCreate() {
        this.server()
            .when(
                request("/api/v1/users")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer fake token")
                    .withBody(new JsonBody('{' +
                        "  \"id\": \"username\"," +
                        "  \"password\": \"password\"," +
                        "  \"email\": \"email@localhost.de\"," +
                        "  \"phone\": \"+49 99 999999-999\"," +
                        "  \"isAutoUpdateEnabled\": true" +
                        '}'))
            )
            .respond(
                response("{}").withStatusCode(201)
            );
        final User user = new FpUsers(
            this.endpoint(),
            new FakeAccessToken("fake token")
        ).create(
            new FpCreateParameters()
                .withId("username")
                .withPassword("password")
                .withEmail("email@localhost.de")
                .withPhone("+49 99 999999-999")
                .withAutoUpdateEnabled()
        );
    }

    @Test
    public void testRequestPasswordChange() {
        this.server()
            .when(
                request("/api/v1/users/requestPasswordChange")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer fake token")
                    .withBody(new JsonBody('{' +
                        "  \"userId\": \"username\"" +
                        '}'))
            )
            .respond(
                response('{' +
                    "  \"userId\": \"username\"," +
                    "  \"userEmail\": \"email@localhost.de\"," +
                    "  \"passwordChangeToken\": \"EnCRyPTEDPassWordCHAnGEToKen==\"" +
                    '}')
            );
        final String token = new FpUsers(
            this.endpoint(),
            new FakeAccessToken("fake token")
        ).requestPasswordChange("username");
        assertThat(token).isEqualTo("EnCRyPTEDPassWordCHAnGEToKen==");
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testExecutePasswordChange() {
        this.server()
            .when(
                request("/api/v1/users/executePasswordChange")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer fake token")
                    .withBody(new JsonBody('{' +
                        "  \"userId\": \"user ID\"," +
                        "  \"password\": \"password\"," +
                        "  \"passwordChangeToken\": \"token\"" +
                        '}'))
            )
            .respond(
                response("")
            );
        new FpUsers(
            this.endpoint(),
            new FakeAccessToken("fake token")
        ).executePasswordChange("user ID", "password", "token");
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testVerify() {
        this.server()
            .when(
                request("/api/v1/users/verify/user-1")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer fake token")
            )
            .respond(
                response("")
            );
        new FpUsers(
            this.endpoint(),
            new FakeAccessToken("fake token")
        ).verify("user-1");
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testDeleteUnverified() {
        this.server()
            .when(
                request("/api/v1/users/user-1")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer fake token")
            )
            .respond(
                response("{}")
            );
        new FpUsers(
            this.endpoint(),
            new FakeAccessToken("fake token")
        ).deleteUnverified("user-1");
    }
}
