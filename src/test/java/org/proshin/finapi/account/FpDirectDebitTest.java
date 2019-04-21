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
package org.proshin.finapi.account;

import java.math.BigDecimal;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.account.in.DirectDebitParameters;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.LocalDateOf;

public class FpDirectDebitTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10005);
    }

    @Test
    public void testRequest() {
        server
            .when(
                HttpRequest.request("/api/v1/accounts/requestSepaDirectDebit")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer access-token")
                    .withBody(new JsonBody('{' +
                        "  \"accountId\": 1," +
                        "  \"bankingPin\": \"123456\"," +
                        "  \"storePin\": true," +
                        "  \"twoStepProcedureId\": \"955\"," +
                        "  \"directDebitType\": \"B2B\"," +
                        "  \"sequenceType\": \"OOFF\"," +
                        "  \"executionDate\": \"2018-01-01\"," +
                        "  \"singleBooking\": true," +
                        "  \"directDebits\": [" +
                        "    {" +
                        "      \"debitorName\": \"Debitor\"," +
                        "      \"debitorIban\": \"DE13700800000061110500\"," +
                        "      \"debitorBic\": \"DRESDEFF700\"," +
                        "      \"amount\": 99.99," +
                        "      \"purpose\": \"Test Payment\"," +
                        "      \"sepaPurposeCode\": \"OTHR\"," +
                        "      \"mandateId\": \"1\"," +
                        "      \"mandateDate\": \"2018-01-02\"," +
                        "      \"creditorId\": \"Creditor\"," +
                        "      \"endToEndId\": \"001100550526\"" +
                        "    }" +
                        "  ]" +
                        '}'))
            )
            .respond(HttpResponse.response("{}"));
        new FpDirectDebit(
            new FpEndpoint("http://127.0.0.1:10005"),
            new FakeAccessToken("access-token"),
            "/api/v1/accounts/"
        ).request(
            new DirectDebitParameters()
                .withAccount(1L)
                .withBankingPin("123456")
                .withStoringPin()
                .withTwoStepProcedure("955")
                .withDirectDebitType(DirectDebitParameters.DirectDebitType.B2B)
                .withSequenceType(DirectDebitParameters.SequenceType.OOFF)
                .withExecutionDate(new LocalDateOf("2018-01-01").get())
                .asSingleBooking()
                .withDebtors(
                    new DirectDebitParameters.Debtor()
                        .withName("Debitor")
                        .withIban("DE13700800000061110500")
                        .withBic("DRESDEFF700")
                        .withAmount(new BigDecimal("99.99"))
                        .withPurpose("Test Payment")
                        .withSepaPurposeCode("OTHR")
                        .withMandateId("1")
                        .withMandateDate(new LocalDateOf("2018-01-02").get())
                        .withCreditorId("Creditor")
                        .withEndToEndId("001100550526")
                )
        );
    }

    @Test
    public void testExecute() {
        server
            .when(
                HttpRequest.request("/api/v1/accounts/executeSepaDirectDebit")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer access-token")
                    .withBody(new JsonBody('{' +
                        "  \"accountId\": 1," +
                        "  \"bankingTan\": \"0123\"" +
                        '}'))
            )
            .respond(HttpResponse.response("{}"));
        new FpDirectDebit(
            new FpEndpoint("http://127.0.0.1:10005"),
            new FakeAccessToken("access-token"),
            "/api/v1/accounts/"
        ).execute(1L, "0123");
    }

    @AfterClass
    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    public static void stopMockServer() {
        server.stop();
    }
}
