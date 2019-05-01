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
package org.proshin.finapi.mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.account.Type;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.mock.in.Account;
import org.proshin.finapi.mock.in.BatchUpdateParameters;
import org.proshin.finapi.mock.in.CategorizationParameter;
import org.proshin.finapi.mock.in.Connection;
import org.proshin.finapi.mock.in.Transaction;
import org.proshin.finapi.mock.out.CategorizationResult;
import org.proshin.finapi.mock.out.CategorizationResults;

public class FpMocksAndTestsTest {
    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10017);
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
    public void testMockBatchUpdate() {
        server
            .when(
                HttpRequest.request("/api/v1/tests/mockBatchUpdate")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"mockBankConnectionUpdates\": [" +
                        "    {" +
                        "      \"bankConnectionId\": 42," +
                        "      \"simulateBankLoginError\": true," +
                        "      \"mockAccountsData\": [" +
                        "        {" +
                        "          \"accountId\": 43," +
                        "          \"accountBalance\": 99.99," +
                        "          \"newTransactions\": [" +
                        "            {" +
                        "              \"amount\": -99.99," +
                        "              \"purpose\": \"Restaurantbesuch\"," +
                        "              \"counterpart\": \"Bar Centrale\"," +
                        "              \"counterpartIban\": \"DE13700800000061110500\"," +
                        "              \"counterpartBlz\": \"70080000\"," +
                        "              \"counterpartBic\": \"DRESDEFF700\"," +
                        "              \"counterpartAccountNumber\": \"61110500\"," +
                        "              \"bookingDate\": \"2018-01-01\"," +
                        "              \"valueDate\": \"2018-02-02\"" +
                        "            }" +
                        "          ]" +
                        "        }" +
                        "      ]" +
                        "    }, {" +
                        "      \"bankConnectionId\": 43" +
                        "    }" +
                        "  ]," +
                        "  \"triggerNotifications\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("")
            );
        new FpMocksAndTests(
            new FpEndpoint("http://127.0.0.1:10017"),
            new FakeAccessToken("user-token")
        ).mockBatchUpdate(
            new BatchUpdateParameters(true,
                new Connection(42L, true,
                    new Account(43L, new BigDecimal("99.99"),
                        new Transaction(new BigDecimal("-99.99"))
                            .withPurpose("Restaurantbesuch")
                            .withCounterpart("Bar Centrale")
                            .withCounterpartIban("DE13700800000061110500")
                            .withCounterpartBlz("70080000")
                            .withCounterpartBic("DRESDEFF700")
                            .withCounterpartAccountNumber("61110500")
                            .withBookingDate(LocalDate.of(2018, 1, 1))
                            .withValueDate(LocalDate.of(2018, 2, 2))
                    )
                ),
                new Connection(43L)
            )
        );
    }

    @Test
    public void testCheckCategorization() {
        server
            .when(
                HttpRequest.request("/api/v1/tests/checkCategorization")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"transactionData\": [" +
                        "    {" +
                        "      \"transactionId\": \"transaction\"," +
                        "      \"accountTypeId\": 1," +
                        "      \"amount\": -99.99," +
                        "      \"purpose\": \"Restaurantbesuch\"," +
                        "      \"counterpart\": \"Bar Centrale\"," +
                        "      \"counterpartIban\": \"DE13700800000061110500\"," +
                        "      \"counterpartAccountNumber\": \"61110500\"," +
                        "      \"counterpartBlz\": \"70080000\"," +
                        "      \"counterpartBic\": \"DRESDEFF700\"," +
                        "      \"mcCode\": \"5542\"," +
                        "      \"typeCodeZka\": \"999\"" +
                        "    }" +
                        "  ]" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{\"categorizationCheckResult\":[{}]}")
            );
        final CategorizationResults results = new FpMocksAndTests(
            new FpEndpoint("http://127.0.0.1:10017"),
            new FakeAccessToken("user-token")
        ).checkCategorization(
            new CategorizationParameter("transaction", Type.Checking, new BigDecimal("-99.99"))
                .withPurpose("Restaurantbesuch")
                .withCounterpart("Bar Centrale")
                .withCounterpartIban("DE13700800000061110500")
                .withCounterpartAccountNumber("61110500")
                .withCounterpartBlz("70080000")
                .withCounterpartBic("DRESDEFF700")
                .withMcCode("5542")
                .withTypeCodeZka("999")
        );
        final CategorizationResult result = results.results().iterator().next();
    }
}
