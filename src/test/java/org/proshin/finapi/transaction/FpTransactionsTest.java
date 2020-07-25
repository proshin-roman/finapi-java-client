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
import org.apache.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.cactoos.iterable.IterableOfLongs;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.Direction;
import org.proshin.finapi.primitives.LocalDateOf;
import org.proshin.finapi.transaction.in.DeleteTransactionsCriteria;
import org.proshin.finapi.transaction.in.EditTransactionsParameters;
import org.proshin.finapi.transaction.in.TransactionsCriteria;
import org.proshin.finapi.transaction.out.TransactionsPage;

public final class FpTransactionsTest extends TestWithMockedEndpoint {

    @Test
    public void testOne() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions/123")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(HttpResponse.response("{}"));
        new FpTransactions(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).one(123L);
    }

    @Test
    public void testQuery() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions")
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
            this.endpoint(),
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
        assertThat(page.paging().page()).isEqualTo(1);
        assertThat(page.paging().perPage()).isEqualTo(20);
        assertThat(page.paging().pageCount()).isEqualTo(10);
        assertThat(page.paging().totalCount()).isEqualTo(200);
        assertThat(page.income()).isEqualTo(new BigDecimal("23.45"));
        assertThat(page.spending()).isEqualTo(new BigDecimal("34.56"));
        assertThat(page.balance()).isEqualTo(new BigDecimal("59.99"));
        page.items().iterator().next();
    }

    @Test
    public void testEdit() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions")
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
            this.endpoint(),
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
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions/triggerCategorization")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody("{\"bankConnectionIds\": [1, 2, 3]}"))
            )
            .respond(HttpResponse.response().withStatusCode(HttpStatus.SC_OK));
        new FpTransactions(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).triggerCategorization(new IterableOfLongs(1L, 2L, 3L));
    }

    @Test
    public void testDeleteAll() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/transactions")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("maxDeletionDate", "2019-01-01")
                    .withQueryStringParameter("safeMode", "false")
                    .withQueryStringParameter("rememberDeletion", "true")
            )
            .respond(HttpResponse.response("{\"identifiers\":[]}"));
        new FpTransactions(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).deleteAll(
            new DeleteTransactionsCriteria()
                .withMaxDeletionDate(new LocalDateOf("2019-01-01").get())
                .withUnsafeMode()
                .withRememberingDeletion()
        );
    }
}
