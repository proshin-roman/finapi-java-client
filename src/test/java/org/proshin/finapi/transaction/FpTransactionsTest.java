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
import org.cactoos.iterable.IterableOfLongs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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
import org.proshin.finapi.primitives.Direction;
import org.proshin.finapi.primitives.LocalDateOf;
import org.proshin.finapi.transaction.in.DeleteTransactionsCriteria;
import org.proshin.finapi.transaction.in.EditTransactionsParameters;
import org.proshin.finapi.transaction.in.TransactionsCriteria;
import org.proshin.finapi.transaction.out.TransactionsPage;

public class FpTransactionsTest {
    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10008);
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
    public void testOne() {
        server
            .when(
                HttpRequest.request("/api/v1/transactions/123")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(HttpResponse.response("{}"));
        new FpTransactions(
            new FpEndpoint("http://127.0.0.1:10008"),
            new FakeAccessToken("user-token")
        ).one(123L);
    }

    @Test
    public void testQuery() {
        server
            .when(
                HttpRequest.request("/api/v1/transactions/")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("ids", "1%2C2%2C3")
                    .withQueryStringParameter("view", "bankView")
                    .withQueryStringParameter("search", "just+a+word")
                    .withQueryStringParameter("counterpart", "just+counterpart")
                    .withQueryStringParameter("purpose", "just+purpose")
                    .withQueryStringParameter("accountIds", "3%2C4%2C5")
                    .withQueryStringParameter("minBankBookingDate", "2018-01-01")
                    .withQueryStringParameter("maxBankBookingDate", "2018-02-02")
                    .withQueryStringParameter("minFinapiBookingDate", "2018-02-03")
                    .withQueryStringParameter("maxFinapiBookingDate", "2018-02-04")
                    .withQueryStringParameter("minAmount", "-99.99")
                    .withQueryStringParameter("maxAmount", "89.01")
                    .withQueryStringParameter("direction", "income")
                    .withQueryStringParameter("labelIds", "4%2C5%2C6")
                    .withQueryStringParameter("categoryIds", "5%2C6%2C7")
                    .withQueryStringParameter("includeChildCategories", "false")
                    .withQueryStringParameter("isNew", "true")
                    .withQueryStringParameter("isPotentialDuplicate", "true")
                    .withQueryStringParameter("isAdjustingEntry", "true")
                    .withQueryStringParameter("minImportDate", "2018-01-05")
                    .withQueryStringParameter("maxImportDate", "2018-01-06")
                    .withQueryStringParameter("page", "3")
                    .withQueryStringParameter("perPage", "21")
                    .withQueryStringParameter("order", "id%2Cdesc", "date%2Casc")
            )
            .respond(HttpResponse.response('{' +
                "  \"transactions\": [{}]," +
                "  \"paging\": {" +
                "    \"page\": 1," +
                "    \"perPage\": 20," +
                "    \"pageCount\": 10," +
                "    \"totalCount\": 200" +
                "  }," +
                "  \"income\": 23.45," +
                "  \"spending\": 34.56," +
                "  \"balance\": 59.99" +
                '}'));
        final TransactionsPage page = new FpTransactions(
            new FpEndpoint("http://127.0.0.1:10008"),
            new FakeAccessToken("user-token")
        ).query(
            new TransactionsCriteria()
                .withIds(new IterableOfLongs(1L, 2L, 3L))
                .withView(TransactionsCriteria.View.BANK)
                .withSearch("just a word")
                .withCounterpart("just counterpart")
                .withPurpose("just purpose")
                .withAccounts(new IterableOfLongs(3L, 4L, 5L))
                .withMinBankBookingDate(new LocalDateOf("2018-01-01").get())
                .withMaxBankBookingDate(new LocalDateOf("2018-02-02").get())
                .withMinFinapiBookingDate(new LocalDateOf("2018-02-03").get())
                .withMaxFinapiBookingDate(new LocalDateOf("2018-02-04").get())
                .withMinAmount(new BigDecimal("-99.99"))
                .withMaxAmount(new BigDecimal("89.01"))
                .withDirection(Direction.INCOME)
                .withLabels(new IterableOfLongs(4L, 5L, 6L))
                .withCategories(new IterableOfLongs(5L, 6L, 7L))
                .withoutIncludingChildCategories()
                .withIsNew(true)
                .withIsPotentialDuplicate(true)
                .withIsAdjustingEntry(true)
                .withMinImportDate(new LocalDateOf("2018-01-05").get())
                .withMaxImportDate(new LocalDateOf("2018-01-06").get())
                .withPage(3, 21)
                .orderBy("id,desc", "date,asc")
        );
        assertThat(page.paging().page(), is(1));
        assertThat(page.paging().perPage(), is(20));
        assertThat(page.paging().pageCount(), is(10));
        assertThat(page.paging().totalCount(), is(200));
        assertThat(page.income(), is(new BigDecimal("23.45")));
        assertThat(page.spending(), is(new BigDecimal("34.56")));
        assertThat(page.balance(), is(new BigDecimal("59.99")));
        page.items().iterator().next();
    }

    @Test
    public void testEdit() {
        server
            .when(
                HttpRequest.request("/api/v1/transactions/")
                    .withMethod("PATCH")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"isNew\": true," +
                        "  \"isPotentialDuplicate\": true," +
                        "  \"categoryId\": 123," +
                        "  \"trainCategorization\": true," +
                        "  \"labelIds\": [1, 2, 3]," +
                        "  \"ids\": [2, 3, 4]," +
                        "  \"accountIds\": [3, 4, 5]" +
                        '}'))
            )
            .respond(HttpResponse.response("{\"identifiers\":[]}"));
        new FpTransactions(
            new FpEndpoint("http://127.0.0.1:10008"),
            new FakeAccessToken("user-token")
        ).edit(
            new EditTransactionsParameters()
                .withIsNew(true)
                .withIsPotentialDuplicate(true)
                .withCategory(123L)
                .withTrainCategorization(true)
                .withLabels(new IterableOfLongs(1L, 2L, 3L))
                .withIds(new IterableOfLongs(2L, 3L, 4L))
                .withAccounts(new IterableOfLongs(3L, 4L, 5L))
        );
    }

    @Test
    public void testTriggerCategorization() {
        server
            .when(
                HttpRequest.request("/api/v1/transactions/triggerCategorization")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody("{\"bankConnectionIds\": [1, 2, 3]}"))
            )
            .respond(HttpResponse.response().withStatusCode(200));
        new FpTransactions(
            new FpEndpoint("http://127.0.0.1:10008"),
            new FakeAccessToken("user-token")
        ).triggerCategorization(new IterableOfLongs(1L, 2L, 3L));
    }

    @Test
    public void testDeleteAll() {
        server
            .when(
                HttpRequest.request("/api/v1/transactions/")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("maxDeletionDate", "2019-01-01")
                    .withQueryStringParameter("safeMode", "false")
                    .withQueryStringParameter("rememberDeletion", "true")
            )
            .respond(HttpResponse.response("{\"identifiers\":[]}"));
        new FpTransactions(
            new FpEndpoint("http://127.0.0.1:10008"),
            new FakeAccessToken("user-token")
        ).deleteAll(
            new DeleteTransactionsCriteria()
                .withMaxDeletionDate(new LocalDateOf("2019-01-01").get())
                .withUnsafeMode()
                .withRememberingDeletion()
        );
    }
}
