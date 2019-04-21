/*
 * Copyright 2019 Roman Proshin
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
package org.proshin.finapi.client;

import java.util.Optional;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.client.in.EditClientParameters;
import org.proshin.finapi.client.out.Configuration;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.user.User;
import org.proshin.finapi.user.Users;
import org.proshin.finapi.user.in.FpCreateParameters;

public class FpClientTest {

    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private ClientAndServer server;

    @Before
    public void startMockServer() {
        this.server = startClientAndServer(10002);
    }

    @Test
    public void testConfiguration() {
        this.server.when(
            HttpRequest.request("/api/v1/clientConfiguration/")
                .withMethod("GET")
                .withHeader("Authorization", "Bearer random-token"))
            .respond(
                HttpResponse.response('{' +
                    "  \"isAutomaticBatchUpdateEnabled\": true," +
                    "  \"userNotificationCallbackUrl\": \"https://bank.server.com/notification\"," +
                    "  \"userSynchronizationCallbackUrl\": \"https://bank.server.com/synchronization\"," +
                    "  \"refreshTokensValidityPeriod\": 123," +
                    "  \"userAccessTokensValidityPeriod\": 234," +
                    "  \"clientAccessTokensValidityPeriod\": 345," +
                    "  \"maxUserLoginAttempts\": 3," +
                    "  \"isUserAutoVerificationEnabled\": true," +
                    "  \"isMandatorAdmin\": false," +
                    "  \"isWebScrapingEnabled\": true," +
                    "  \"availableBankGroups\": [" +
                    "    \"DE\"," +
                    "    \"AT\"," +
                    "    \"IT\"" +
                    "  ]," +
                    "  \"applicationName\": \"My App\"," +
                    "  \"paymentsEnabled\": true," +
                    "  \"pinStorageAvailableInWebForm\": true" +
                    '}')
            );
        final Configuration configuration = new FpClient(
            new FpEndpoint("http://127.0.0.1:10002"),
            new FakeAccessToken("random-token")
        ).configuration();
        assertThat(configuration.isAutomaticBatchUpdateEnabled(), is(true));
        assertThat(
            configuration.userNotificationCallbackUrl(),
            is(Optional.of("https://bank.server.com/notification"))
        );
        assertThat(
            configuration.userSynchronizationCallbackUrl(),
            is(Optional.of("https://bank.server.com/synchronization"))
        );
        assertThat(configuration.refreshTokensValidityPeriod(), is(Optional.of(123)));
        assertThat(configuration.userAccessTokensValidityPeriod(), is(Optional.of(234)));
        assertThat(configuration.clientAccessTokensValidityPeriod(), is(Optional.of(345)));
        assertThat(configuration.maxUserLoginAttempts(), is(3));
        assertThat(configuration.isUserAutoVerificationEnabled(), is(true));
        assertThat(configuration.isMandatorAdmin(), is(false));
        assertThat(configuration.isWebScrapingEnabled(), is(true));
        assertThat(configuration.availableBankGroups(), CoreMatchers.hasItems("DE", "AT", "IT"));
        assertThat(configuration.applicationName(), is(Optional.of("My App")));
        assertThat(configuration.paymentsEnabled(), is(true));
        assertThat(configuration.pinStorageAvailableInWebForm(), is(true));
    }

    /**
     * @todo #164 Implement a new matcher that will match that the given instance submits the right JSON structure to
     *  the test server
     */
    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testEdit() {
        this.server.when(
            HttpRequest.request("/api/v1/clientConfiguration/")
                .withMethod("PATCH")
                .withHeader("Authorization", "Bearer random-token")
                .withBody(new JsonBody('{' +
                    "  \"userNotificationCallbackUrl\": \"https://bank.server.com/notification\"," +
                    "  \"userSynchronizationCallbackUrl\": \"https://bank.server.com/synchronization\"," +
                    "  \"refreshTokensValidityPeriod\": 123," +
                    "  \"userAccessTokensValidityPeriod\": 234," +
                    "  \"clientAccessTokensValidityPeriod\": 345," +
                    "  \"isPinStorageAvailableInWebForm\": true," +
                    "  \"applicationName\": \"New name\"" +
                    '}')))
            .respond(
                HttpResponse.response('{' +
                    "  \"isAutomaticBatchUpdateEnabled\": true," +
                    "  \"userNotificationCallbackUrl\": \"https://bank.server.com/notification\"," +
                    "  \"userSynchronizationCallbackUrl\": \"https://bank.server.com/synchronization\"," +
                    "  \"refreshTokensValidityPeriod\": 123," +
                    "  \"userAccessTokensValidityPeriod\": 234," +
                    "  \"clientAccessTokensValidityPeriod\": 345," +
                    "  \"maxUserLoginAttempts\": 3," +
                    "  \"isUserAutoVerificationEnabled\": true," +
                    "  \"isMandatorAdmin\": false," +
                    "  \"isWebScrapingEnabled\": true," +
                    "  \"availableBankGroups\": [" +
                    "    \"DE\"," +
                    "    \"AT\"," +
                    "    \"IT\"" +
                    "  ]," +
                    "  \"applicationName\": \"New name\"," +
                    "  \"paymentsEnabled\": true," +
                    "  \"pinStorageAvailableInWebForm\": true" +
                    '}')
            );
        new FpClient(
            new FpEndpoint("http://127.0.0.1:10002"),
            new FakeAccessToken("random-token")
        ).edit(
            new EditClientParameters()
                .withUserNotificationCallbackUrl("https://bank.server.com/notification")
                .withUserSynchronizationCallbackUrl("https://bank.server.com/synchronization")
                .withRefreshTokensValidityPeriod(123)
                .withUserAccessTokensValidityPeriod(234)
                .withClientAccessTokensValidityPeriod(345)
                .withPinStorageAvailableInWebForm(true)
                .withApplicationName("New name")
        );
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testUsers() {
        this.server.when(
            HttpRequest.request("/api/v1/users/")
                .withMethod("POST")
                .withHeader("Authorization", "Bearer random-token"))
            .respond(
                HttpResponse.response("{}")
                    .withStatusCode(201)
            );
        final Users users = new FpClient(
            new FpEndpoint("http://127.0.0.1:10002"),
            new FakeAccessToken("random-token")
        ).users();
        final User user = users.create(new FpCreateParameters());
    }

    @After
    public void stopMockServer() {
        this.server.stop();
    }
}
