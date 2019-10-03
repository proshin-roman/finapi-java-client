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
import org.junit.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.account.in.MoneyTransferParameters;
import org.proshin.finapi.account.out.SepaRequestingResponse;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.LocalDateOf;

public class FpMoneyTransferTest extends TestWithMockedEndpoint {

    @Test
    public void testRequest() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/accounts/requestSepaMoneyTransfer")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer access-token")
                    .withBody(new JsonBody('{' +
                        "  \"recipientName\": \"Recipient #1\"," +
                        "  \"recipientIban\": \"DE13700800000061110500\"," +
                        "  \"recipientBic\": \"DRESDEFF700\"," +
                        "  \"clearingAccountId\": \"BA-TUYEF7D24CGK6\"," +
                        "  \"amount\": 123.45," +
                        "  \"purpose\": \"Test Payment #1\"," +
                        "  \"sepaPurposeCode\": \"OTHR1\"," +
                        "  \"accountId\": 1," +
                        "  \"bankingPin\": \"123456\"," +
                        "  \"storePin\": true," +
                        "  \"twoStepProcedureId\": \"955\"," +
                        "  \"challengeResponse\": \"0123\"," +
                        "  \"executionDate\": \"2019-04-03\"," +
                        "  \"singleBooking\": true," +
                        "  \"additionalMoneyTransfers\": [" +
                        "    {" +
                        "      \"recipientName\": \"Recipient #2\"," +
                        "      \"recipientIban\": \"DE13700800000061110501\"," +
                        "      \"recipientBic\": \"DRESDEFF701\"," +
                        "      \"clearingAccountId\": \"BA-TUYEF7D24CGK1\"," +
                        "      \"amount\": 234.56," +
                        "      \"purpose\": \"Test Payment #2\"," +
                        "      \"sepaPurposeCode\": \"OTHR2\"" +
                        "    }" +
                        "  ]" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}")
            );
        final SepaRequestingResponse request = new FpMoneyTransfer(
            this.endpoint(),
            new FakeAccessToken("access-token"),
            "/api/v1/accounts/"
        ).request(
            new MoneyTransferParameters()
                .withAccount(1L)
                .withBankingPin("123456")
                .withStoringPin()
                .withTwoStepProcedure("955")
                .withChallengeResponse("0123")
                .withExecutionDate(new LocalDateOf("2019-04-03").get())
                .asSingleBooking()
                .withRecipients(
                    new MoneyTransferParameters.Recipient()
                        .withName("Recipient #1")
                        .withIban("DE13700800000061110500")
                        .withBic("DRESDEFF700")
                        .withClearingAccount("BA-TUYEF7D24CGK6")
                        .withAmount(new BigDecimal("123.45"))
                        .withPurpose("Test Payment #1")
                        .withSepaPurposeCode("OTHR1"),
                    new MoneyTransferParameters.Recipient()
                        .withName("Recipient #2")
                        .withIban("DE13700800000061110501")
                        .withBic("DRESDEFF701")
                        .withClearingAccount("BA-TUYEF7D24CGK1")
                        .withAmount(new BigDecimal("234.56"))
                        .withPurpose("Test Payment #2")
                        .withSepaPurposeCode("OTHR2")
                )
        );
    }

    @Test
    public void testExecute() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/accounts/executeSepaMoneyTransfer")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer access-token")
                    .withBody(new JsonBody('{' +
                        "  \"accountId\": 1," +
                        "  \"bankingTan\": \"098765\"" +
                        '}'))
            ).respond(HttpResponse.response("{}"));
        new FpMoneyTransfer(
            this.endpoint(),
            new FakeAccessToken("access-token"),
            "/api/v1/accounts/"
        ).execute(1L, "098765");
    }
}
