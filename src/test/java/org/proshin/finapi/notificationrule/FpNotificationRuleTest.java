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
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.json.JSONObject;
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

public class FpNotificationRuleTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10011);
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
    public void testWithParams() {
        final FpNotificationRule rule = new FpNotificationRule(
            new FpEndpoint("http://127.0.0.1"),
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
        assertThat(rule.id(), is(1L));
        assertThat(rule.triggerEvent(), is(TriggerEvent.NEW_ACCOUNT_BALANCE));
        assertThat(rule.params(), is(new MapOf<>(new MapEntry<>("accountIds", "1,2,3"))));
        assertThat(rule.callbackHandle(), is(Optional.of("handle")));
        assertThat(rule.includeDetails(), is(true));
    }

    @Test
    public void testWithoutParams() {
        final FpNotificationRule rule = new FpNotificationRule(
            new FpEndpoint("http://127.0.0.1:10011"),
            new FakeAccessToken("user-token"),
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"triggerEvent\": \"NEW_ACCOUNT_BALANCE\"," +
                "  \"callbackHandle\": \"handle\"," +
                "  \"includeDetails\": true" +
                '}'),
            "/api/v1/notificationRules"
        );
        assertThat(rule.id(), is(1L));
        assertThat(rule.triggerEvent(), is(TriggerEvent.NEW_ACCOUNT_BALANCE));
        assertThat(rule.params().size(), is(0));
        assertThat(rule.callbackHandle(), is(Optional.of("handle")));
        assertThat(rule.includeDetails(), is(true));
    }

    @Test
    public void testDelete() {
        server
            .when(
                HttpRequest.request("/api/v1/notificationRules/123")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response().withStatusCode(200)
            );
        new FpNotificationRule(
            new FpEndpoint("http://127.0.0.1:10011"),
            new FakeAccessToken("user-token"),
            new JSONObject("{\"id\": 123}"),
            "/api/v1/notificationRules"
        ).delete();
    }
}
