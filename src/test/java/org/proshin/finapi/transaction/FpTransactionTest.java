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
package org.proshin.finapi.transaction;

import java.math.BigDecimal;
import java.util.Optional;
import org.apache.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.cactoos.iterable.IterableOfLongs;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.transaction.in.EditTransactionParameters;
import org.proshin.finapi.transaction.in.SplitTransactionParameters;
import org.proshin.finapi.transaction.in.Subtransaction;

public class FpTransactionTest extends TestWithMockedEndpoint {

    @Test
    public void test() {
        final Transaction tx = new FpTransaction(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"parentId\": 2," +
                "  \"accountId\": 3," +
                "  \"valueDate\": \"2018-01-01 00:00:00.000\"," +
                "  \"bankBookingDate\": \"2018-01-02 00:00:00.000\"," +
                "  \"finapiBookingDate\": \"2018-01-03 00:00:00.000\"," +
                "  \"amount\": -99.99," +
                "  \"purpose\": \"Restaurantbesuch\"," +
                "  \"counterpartName\": \"Bar Centrale\"," +
                "  \"counterpartAccountNumber\": \"0061110500\"," +
                "  \"counterpartIban\": \"DE13700800000061110500\"," +
                "  \"counterpartBlz\": \"70080000\"," +
                "  \"counterpartBic\": \"DRESDEFF700\"," +
                "  \"counterpartBankName\": \"Commerzbank vormals Dresdner Bank\"," +
                "  \"counterpartMandateReference\": \"MR123\"," +
                "  \"counterpartCustomerReference\": \"CUR123\"," +
                "  \"counterpartCreditorId\": \"CRI123\"," +
                "  \"counterpartDebitorId\": \"CRI098\"," +
                "  \"type\": \"Überweisungsauftrag\"," +
                "  \"typeCodeZka\": \"999\"," +
                "  \"typeCodeSwift\": \"RAPRDE51\"," +
                "  \"sepaPurposeCode\": \"OTHR\"," +
                "  \"primanota\": \"Primanota\"," +
                "  \"category\": {" +
                "    \"id\": 4," +
                "    \"name\": \"Sport & Fitness\"," +
                "    \"parentId\": 373," +
                "    \"parentName\": \"Freizeit, Hobbys & Soziales\"," +
                "    \"isCustom\": true," +
                "    \"children\": [" +
                "      1," +
                "      2," +
                "      3" +
                "    ]" +
                "  }," +
                "  \"labels\": [" +
                "    {" +
                "      \"id\": 1," +
                "      \"name\": \"test\"" +
                "    }" +
                "  ]," +
                "  \"isPotentialDuplicate\": true," +
                "  \"isAdjustingEntry\": true," +
                "  \"isNew\": true," +
                "  \"importDate\": \"2018-01-01 00:00:00.000\"," +
                "  \"children\": [" +
                "    1," +
                "    2," +
                "    3" +
                "  ]," +
                "  \"paypalData\": {" +
                "    \"invoiceNumber\": \"INV2-KXVU-7Z64-DT6W-MG2X\"," +
                "    \"fee\": -0.99," +
                "    \"net\": 9.99," +
                "    \"auctionSite\": \"eBay\"" +
                "  }," +
                "  \"endToEndReference\": \"001100550526\"," +
                "  \"compensationAmount\": -1.11," +
                "  \"originalAmount\": -9.99," +
                "  \"differentDebitor\": \"DIFD70204\"," +
                "  \"differentCreditor\": \"DIFC98450\"" +
                '}'),
            "/api/v1/transactions"
        );
        assertThat(tx.id()).isEqualTo(1L);
        assertThat(tx.parent()).isEqualTo(Optional.of(2L));
        assertThat(tx.account()).isEqualTo(3L);
        assertThat(tx.valueDate()).isEqualTo(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get());
        assertThat(tx.bankBookingDate()).isEqualTo(new OffsetDateTimeOf("2018-01-02 00:00:00.000").get());
        assertThat(tx.finapiBookingDate()).isEqualTo(new OffsetDateTimeOf("2018-01-03 00:00:00.000").get());
        assertThat(tx.amount()).isEqualTo(new BigDecimal("-99.99"));
        assertThat(tx.purpose()).isEqualTo(Optional.of("Restaurantbesuch"));
        assertThat(tx.counterpart().name()).isEqualTo(Optional.of("Bar Centrale"));
        assertThat(tx.counterpart().accountNumber()).isEqualTo(Optional.of("0061110500"));
        assertThat(tx.counterpart().iban()).isEqualTo(Optional.of("DE13700800000061110500"));
        assertThat(tx.counterpart().blz()).isEqualTo(Optional.of("70080000"));
        assertThat(tx.counterpart().bic()).isEqualTo(Optional.of("DRESDEFF700"));
        assertThat(tx.counterpart().bankName()).isEqualTo(Optional.of("Commerzbank vormals Dresdner Bank"));
        assertThat(tx.counterpart().mandateReference()).isEqualTo(Optional.of("MR123"));
        assertThat(tx.counterpart().customerReference()).isEqualTo(Optional.of("CUR123"));
        assertThat(tx.counterpart().creditorId()).isEqualTo(Optional.of("CRI123"));
        assertThat(tx.counterpart().debitorId()).isEqualTo(Optional.of("CRI098"));
        assertThat(tx.type().type()).isEqualTo(Optional.of("Überweisungsauftrag"));
        assertThat(tx.type().typeCodeZka()).isEqualTo(Optional.of("999"));
        assertThat(tx.type().typeCodeSwift()).isEqualTo(Optional.of("RAPRDE51"));
        assertThat(tx.sepaPurposeCode()).isEqualTo(Optional.of("OTHR"));
        assertThat(tx.primanota()).isEqualTo(Optional.of("Primanota"));
        assertThat(tx.category().isPresent()).isTrue();
        assertThat(tx.category().get().id()).isEqualTo(4L);
        tx.labels().forEach(label -> {
            assertThat(label.id()).isEqualTo(1L);
            assertThat(label.name()).isEqualTo("test");
        });
        assertThat(tx.isPotentialDuplicate()).isTrue();
        assertThat(tx.isAdjustingEntry()).isTrue();
        assertThat(tx.isNew()).isTrue();
        assertThat(tx.importDate()).isEqualTo(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get());
        assertThat(tx.children()).containsExactlyInAnyOrder(1L, 2L, 3L);
        assertThat(tx.payPalData().isPresent()).isTrue();
        assertThat(tx.payPalData().get().invoiceNumber()).isEqualTo(Optional.of("INV2-KXVU-7Z64-DT6W-MG2X"));
        assertThat(tx.payPalData().get().fee()).isEqualTo(Optional.of(new BigDecimal("-0.99")));
        assertThat(tx.payPalData().get().net()).isEqualTo(Optional.of(new BigDecimal("9.99")));
        assertThat(tx.payPalData().get().auctionSite()).isEqualTo(Optional.of("eBay"));
        assertThat(tx.endToEndReference()).isEqualTo(Optional.of("001100550526"));
        assertThat(tx.compensationAmount()).isEqualTo(Optional.of(new BigDecimal("-1.11")));
        assertThat(tx.originalAmount()).isEqualTo(Optional.of(new BigDecimal("-9.99")));
        assertThat(tx.differentDebitor()).isEqualTo(Optional.of("DIFD70204"));
        assertThat(tx.differentCreditor()).isEqualTo(Optional.of("DIFC98450"));
    }

    @Test
    public void testSplit() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions/123/split")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"subTransactions\": [" +
                        "    {" +
                        "      \"amount\": -99.99," +
                        "      \"categoryId\": 378," +
                        "      \"purpose\": \"Restaurantbesuch\"," +
                        "      \"counterpart\": \"TueV Bayern\"," +
                        "      \"counterpartAccountNumber\": \"61110500\"," +
                        "      \"counterpartIban\": \"DE13700800000061110500\"," +
                        "      \"counterpartBic\": \"DRESDEFF700\"," +
                        "      \"counterpartBlz\": \"70080000\"," +
                        "      \"labelIds\": [" +
                        "        1," +
                        "        2," +
                        "        3" +
                        "      ]" +
                        "    }" +
                        "  ]" +
                        '}'))
            ).respond(
            HttpResponse.response("{}")
        );
        new FpTransaction(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject("{\"id\":123}"),
            "/api/v1/transactions"
        ).split(
            new SplitTransactionParameters()
                .withSubtransactions(
                    new Subtransaction()
                        .withAmount(new BigDecimal("-99.99"))
                        .withCategory(378L)
                        .withPurpose("Restaurantbesuch")
                        .withCounterpart("TueV Bayern")
                        .withCounterpartAccountNumber("61110500")
                        .withCounterpartIban("DE13700800000061110500")
                        .withCounterpartBic("DRESDEFF700")
                        .withCounterpartBlz("70080000")
                        .withLabels(new IterableOfLongs(1L, 2L, 3L))
                )
        );
    }

    @Test
    public void testRestore() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions/123/restore")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpTransaction(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject("{\"id\":123}"),
            "/api/v1/transactions"
        ).restore();
    }

    @Test
    public void testEdit() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions/123")
                    .withMethod("PATCH")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"isNew\": true," +
                        "  \"isPotentialDuplicate\": true," +
                        "  \"categoryId\": 378," +
                        "  \"trainCategorization\": true," +
                        "  \"labelIds\": [" +
                        "    1," +
                        "    2," +
                        "    3" +
                        "  ]" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpTransaction(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject("{\"id\":123}"),
            "/api/v1/transactions"
        ).edit(
            new EditTransactionParameters()
                .withIsNew(true)
                .withIsPotentialDuplicate(true)
                .withCategory(378L)
                .withTrainCategorization(true)
                .withLabels(new IterableOfLongs(1L, 2L, 3L))
        );
    }

    @Test
    public void testDelete() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions/123")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response().withStatusCode(HttpStatus.SC_OK)
            );
        new FpTransaction(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject("{\"id\":123}"),
            "/api/v1/transactions"
        ).delete();
    }
}
