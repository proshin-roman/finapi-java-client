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
import org.cactoos.iterable.IterableOfLongs;
import org.hamcrest.BaseMatcher;
import static org.hamcrest.CoreMatchers.hasItems;
import org.hamcrest.Description;
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
import org.mockserver.model.JsonBody;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.label.Label;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.transaction.in.EditTransactionParameters;
import org.proshin.finapi.transaction.in.SplitTransactionParameters;
import org.proshin.finapi.transaction.in.Subtransaction;

public class FpTransactionTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10007);
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
    public void test() {
        final Transaction tx = new FpTransaction(
            new FpEndpoint("http://127.0.0.1:10007"),
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
        assertThat(tx.id(), is(1L));
        assertThat(tx.parent(), is(Optional.of(2L)));
        assertThat(tx.account(), is(3L));
        assertThat(tx.valueDate(), is(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get()));
        assertThat(tx.bankBookingDate(), is(new OffsetDateTimeOf("2018-01-02 00:00:00.000").get()));
        assertThat(tx.finapiBookingDate(), is(new OffsetDateTimeOf("2018-01-03 00:00:00.000").get()));
        assertThat(tx.amount(), is(new BigDecimal("-99.99")));
        assertThat(tx.purpose(), is(Optional.of("Restaurantbesuch")));
        assertThat(tx.counterpart().name(), is(Optional.of("Bar Centrale")));
        assertThat(tx.counterpart().accountNumber(), is(Optional.of("0061110500")));
        assertThat(tx.counterpart().iban(), is(Optional.of("DE13700800000061110500")));
        assertThat(tx.counterpart().blz(), is(Optional.of("70080000")));
        assertThat(tx.counterpart().bic(), is(Optional.of("DRESDEFF700")));
        assertThat(tx.counterpart().bankName(), is(Optional.of("Commerzbank vormals Dresdner Bank")));
        assertThat(tx.counterpart().mandateReference(), is(Optional.of("MR123")));
        assertThat(tx.counterpart().customerReference(), is(Optional.of("CUR123")));
        assertThat(tx.counterpart().creditorId(), is(Optional.of("CRI123")));
        assertThat(tx.counterpart().debitorId(), is(Optional.of("CRI098")));
        assertThat(tx.type().type(), is(Optional.of("Überweisungsauftrag")));
        assertThat(tx.type().typeCodeZka(), is(Optional.of("999")));
        assertThat(tx.type().typeCodeSwift(), is(Optional.of("RAPRDE51")));
        assertThat(tx.sepaPurposeCode(), is(Optional.of("OTHR")));
        assertThat(tx.primanota(), is(Optional.of("Primanota")));
        assertThat(tx.category().isPresent(), is(true));
        assertThat(tx.category().get().id(), is(4L));
        assertThat(tx.labels(), hasItems(new LabelMatcher(1L, "test")));
        assertThat(tx.isPotentialDuplicate(), is(true));
        assertThat(tx.isAdjustingEntry(), is(true));
        assertThat(tx.isNew(), is(true));
        assertThat(tx.importDate(), is(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get()));
        assertThat(tx.children(), hasItems(1L, 2L, 3L));
        assertThat(tx.payPalData().isPresent(), is(true));
        assertThat(tx.payPalData().get().invoiceNumber(), is(Optional.of("INV2-KXVU-7Z64-DT6W-MG2X")));
        assertThat(tx.payPalData().get().fee(), is(Optional.of(new BigDecimal("-0.99"))));
        assertThat(tx.payPalData().get().net(), is(Optional.of(new BigDecimal("9.99"))));
        assertThat(tx.payPalData().get().auctionSite(), is(Optional.of("eBay")));
        assertThat(tx.endToEndReference(), is(Optional.of("001100550526")));
        assertThat(tx.compensationAmount(), is(Optional.of(new BigDecimal("-1.11"))));
        assertThat(tx.originalAmount(), is(Optional.of(new BigDecimal("-9.99"))));
        assertThat(tx.differentDebitor(), is(Optional.of("DIFD70204")));
        assertThat(tx.differentCreditor(), is(Optional.of("DIFC98450")));

    }

    @Test
    public void testSplit() {
        server.when(
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
            new FpEndpoint("http://127.0.0.1:10007"),
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
        server
            .when(
                HttpRequest.request("/api/v1/transactions/123/restore")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpTransaction(
            new FpEndpoint("http://127.0.0.1:10007"),
            new FakeAccessToken("user-token"),
            new JSONObject("{\"id\":123}"),
            "/api/v1/transactions"
        ).restore();
    }

    @Test
    public void testEdit() {
        server
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
            new FpEndpoint("http://127.0.0.1:10007"),
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
        server
            .when(
                HttpRequest.request("/api/v1/transactions/123")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response().withStatusCode(200)
            );
        new FpTransaction(
            new FpEndpoint("http://127.0.0.1:10007"),
            new FakeAccessToken("user-token"),
            new JSONObject("{\"id\":123}"),
            "/api/v1/transactions"
        ).delete();
    }

    private static final class LabelMatcher extends BaseMatcher<Label> {

        private final Long id;
        private final String name;

        private LabelMatcher(final Long id, final String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean matches(final Object item) {
            final Label label = (Label) item;
            return label.id().equals(this.id) && label.name().equals(this.name);
        }

        @Override
        public void describeTo(final Description description) {

        }
    }
}
