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
import org.apache.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.client.in.EditClientParameters;
import org.proshin.finapi.client.out.Configuration;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.user.User;
import org.proshin.finapi.user.Users;
import org.proshin.finapi.user.in.FpCreateParameters;

public class FpClientTest extends TestWithMockedEndpoint {

    @Test
    public void testConfiguration() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/clientConfiguration")
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
                    "  \"isXs2aEnabled\": true," +
                    "  \"isStandalonePaymentsEnabled\": true," +
                    "  \"availableBankGroups\": [" +
                    "    \"DE\"," +
                    "    \"AT\"," +
                    "    \"IT\"" +
                    "  ]," +
                    "  \"applicationName\": \"My App\"," +
                    "  \"finTSProductRegistrationNumber\": \"fintsRegNumber\"," +
                    "  \"storeSecretsAvailableInWebForm\": true," +
                    "  \"supportSubjectDefault\": \"Some subject\"," +
                    "  \"supportEmail\": \"support@email.com\"," +
                    "  \"paymentsEnabled\": true," +
                    "  \"pinStorageAvailableInWebForm\": true" +
                    '}')
            );
        final Configuration configuration = new FpClient(
            this.endpoint(),
            new FakeAccessToken("random-token")
        ).configuration();
        assertThat(configuration.isAutomaticBatchUpdateEnabled()).isTrue();
        assertThat(configuration.userNotificationCallbackUrl())
            .isEqualTo(Optional.of("https://bank.server.com/notification"));
        assertThat(configuration.userSynchronizationCallbackUrl())
            .isEqualTo(Optional.of("https://bank.server.com/synchronization"));
        assertThat(configuration.refreshTokensValidityPeriod()).isEqualTo(Optional.of(123));
        assertThat(configuration.userAccessTokensValidityPeriod()).isEqualTo(Optional.of(234));
        assertThat(configuration.clientAccessTokensValidityPeriod()).isEqualTo(Optional.of(345));
        assertThat(configuration.maxUserLoginAttempts()).isEqualTo(3);
        assertThat(configuration.isUserAutoVerificationEnabled()).isTrue();
        assertThat(configuration.isMandatorAdmin()).isFalse();
        assertThat(configuration.isWebScrapingEnabled()).isTrue();
        assertThat(configuration.isStandalonePaymentsEnabled()).isTrue();
        assertThat(configuration.availableBankGroups()).containsExactlyInAnyOrder("DE", "AT", "IT");
        assertThat(configuration.applicationName()).isEqualTo(Optional.of("My App"));
        assertThat(configuration.finTSProductRegistrationNumber()).isEqualTo(Optional.of("fintsRegNumber"));
        assertThat(configuration.storeSecretsAvailableInWebForm()).isTrue();
        assertThat(configuration.supportSubjectDefault()).isEqualTo(Optional.of("Some subject"));
        assertThat(configuration.supportEmail()).isEqualTo(Optional.of("support@email.com"));
        assertThat(configuration.paymentsEnabled()).isTrue();
        assertThat(configuration.pinStorageAvailableInWebForm()).isTrue();
    }

    /**
     * @todo #164 Implement a new matcher that will match that the given instance submits the right JSON structure to
     *  the test server
     */
    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testEdit() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/clientConfiguration")
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
            this.endpoint(),
            new FakeAccessToken("random-token")
        ).edit(
            new EditClientParameters()
                .withUserNotificationCallbackUrl("https://bank.server.com/notification")
                .withUserSynchronizationCallbackUrl("https://bank.server.com/synchronization")
                .withRefreshTokensValidityPeriod(123)
                .withUserAccessTokensValidityPeriod(234)
                .withClientAccessTokensValidityPeriod(345)
                .withPinStorageAvailableInWebForm(true)
                .withStoreSecretsAvailableInWebForm(true)
                .withApplicationName("New name")
                .withSupportSubjectDefault("Some subject")
                .withSupportEmail("test@email.com")
                .withFinTSProductRegistrationNumber("finTSProductRegistrationNumber")
        );
    }

    @Test
    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    public void testUsers() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/users")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer random-token"))
            .respond(
                HttpResponse.response("{}")
                    .withStatusCode(HttpStatus.SC_CREATED)
            );
        final Users users = new FpClient(
            this.endpoint(),
            new FakeAccessToken("random-token")
        ).users();
        final User user = users.create(new FpCreateParameters());
    }
}
