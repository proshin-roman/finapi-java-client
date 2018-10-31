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

import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.fake.FakeEndpoint;
import org.proshin.finapi.fake.FakeRoute;
import org.proshin.finapi.fake.matcher.path.ExactPathMatcher;
import org.proshin.finapi.user.in.FpCreateParameters;

public final class UsersTest {

    @Test
    public void testVerified() {
        assertThat(
            new FpUsers(
                new FakeEndpoint(
                    new FakeRoute(
                        '{' +
                            "  \"userId\": \"user ID\"," +
                            "  \"isUserVerified\": true" +
                            '}'
                    )
                ),
                new FakeAccessToken("fake token")
            ).verified("user ID"),
            is(true)
        );
    }

    @Test
    public void testCreate() {
        final User user = new FpUsers(
            new FakeEndpoint(
                new FakeRoute(
                    '{' +
                        "  \"id\": \"user ID\"," +
                        "  \"password\": \"random password\"," +
                        "  \"email\": \"user's email\"," +
                        "  \"phone\": \"user's phone\"," +
                        "  \"isAutoUpdateEnabled\": false" +
                        '}'
                )
            ),
            new FakeAccessToken("fake token")
        ).create(new FpCreateParameters());
        assertThat(user.id(), is("user ID"));
        assertThat(user.password(), is("random password"));
        assertThat(user.email(), is(Optional.of("user's email")));
        assertThat(user.phone(), is(Optional.of("user's phone")));
        assertThat(user.isAutoUpdateEnabled(), is(false));
    }

    @Test
    public void testRequestPasswordChange() {
        assertThat(
            new FpUsers(
                new FakeEndpoint(
                    new FakeRoute(
                        '{' +
                            "  \"userId\": \"userId\"," +
                            "  \"userEmail\": \"user's email\"," +
                            "  \"passwordChangeToken\": \"random token\"" +
                            '}'
                    )
                ),
                new FakeAccessToken("fake token")
            ).requestPasswordChange("userId"),
            is("random token")
        );
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testExecutePasswordChange() {
        new FpUsers(
            new FakeEndpoint(
                new FakeRoute(
                    new ExactPathMatcher("/api/v1/users/executePasswordChange"),
                    ""
                )
            ),
            new FakeAccessToken("fake token")
        ).executePasswordChange("user ID", "password", "token");
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testVerify() {
        new FpUsers(
            new FakeEndpoint(
                new FakeRoute(
                    new ExactPathMatcher("/api/v1/users/verify/user-1"),
                    ""
                )
            ),
            new FakeAccessToken("fake token")
        ).verify("user-1");
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testDeleteUnverified() {
        new FpUsers(
            new FakeEndpoint(
                new FakeRoute(
                    new ExactPathMatcher("/api/v1/users/user-1"),
                    ""
                )
            ),
            new FakeAccessToken("fake token")
        ).deleteUnverified("user-1");
    }
}
