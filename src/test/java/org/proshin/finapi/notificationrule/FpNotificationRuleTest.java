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
package org.proshin.finapi.notificationrule;

import java.util.Optional;
import org.apache.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

public final class FpNotificationRuleTest extends TestWithMockedEndpoint {

    @Test
    public void testWithParams() {
        final NotificationRule rule = new FpNotificationRule(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"triggerEvent\": \"NEW_ACCOUNT_BALANCE\"," +
                "  \"params\": {" +
                "    \"accountIds\": \"1,2,3\"" +
                "  }," +
                "  \"callbackHandle\": \"handle\"," +
                "  \"includeDetails\": true" +
                '}'),
            "/api/v1/notificationRules"
        );
        assertThat(rule.id()).isEqualTo(1L);
        assertThat(rule.triggerEvent()).isEqualTo(TriggerEvent.NEW_ACCOUNT_BALANCE);
        assertThat(rule.params()).isEqualTo(new MapOf<>(new MapEntry<>("accountIds", "1,2,3")));
        assertThat(rule.callbackHandle()).isEqualTo(Optional.of("handle"));
        assertThat(rule.includeDetails()).isTrue();
    }

    @Test
    public void testWithoutParams() {
        final NotificationRule rule = new FpNotificationRule(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"triggerEvent\": \"NEW_ACCOUNT_BALANCE\"," +
                "  \"callbackHandle\": \"handle\"," +
                "  \"includeDetails\": true" +
                '}'),
            "/api/v1/notificationRules"
        );
        assertThat(rule.id()).isEqualTo(1L);
        assertThat(rule.triggerEvent()).isEqualTo(TriggerEvent.NEW_ACCOUNT_BALANCE);
        assertThat(rule.params().size()).isEqualTo(0);
        assertThat(rule.callbackHandle()).isEqualTo(Optional.of("handle"));
        assertThat(rule.includeDetails()).isTrue();
    }

    @Test
    public void testDelete() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules/123")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response().withStatusCode(HttpStatus.SC_OK)
            );
        new FpNotificationRule(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject("{\"id\": 123}"),
            "/api/v1/notificationRules"
        ).delete();
    }
}
