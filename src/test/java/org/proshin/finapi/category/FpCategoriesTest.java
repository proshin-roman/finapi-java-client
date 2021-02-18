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
package org.proshin.finapi.category;

import java.math.BigDecimal;
import org.apache.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.cactoos.iterable.IterableOfLongs;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.account.Type;
import org.proshin.finapi.category.in.CashFlowsCriteria;
import org.proshin.finapi.category.in.CategoriesCriteria;
import org.proshin.finapi.category.in.CreateCategoryParameters;
import org.proshin.finapi.category.in.TrainCategorizationParameters;
import org.proshin.finapi.category.in.TrainCategorizationTransaction;
import org.proshin.finapi.category.out.CashFlows;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.Direction;
import org.proshin.finapi.primitives.LocalDateOf;
import org.proshin.finapi.primitives.paging.Page;

public final class FpCategoriesTest extends TestWithMockedEndpoint {

    @Test
    public void testOne() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/categories/378")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpCategories(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).one(378L);
    }

    @Test
    public void testQuery() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/categories")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("ids", "1,2,3")
                    .withQueryStringParameter("search", "just a word")
                    .withQueryStringParameter("isCustom", "true")
                    .withQueryStringParameter("page", "12")
                    .withQueryStringParameter("perPage", "23")
                    .withQueryStringParameter("order", "id,asc", "name,desc")
            )
            .respond(
                HttpResponse.response("{\"categories\":[{}]}")
            );
        final Page<Category> page = new FpCategories(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).query(
            new CategoriesCriteria()
                .withIds(new IterableOfLongs(1L, 2L, 3L))
                .withSearch("just a word")
                .withIsCustom(true)
                .withPage(12, 23)
                .orderBy("id,asc", "name,desc")
        );
        page.items().iterator().next();
    }

    @Test
    public void testCashFlows() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/categories/cashFlows")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("search", "just a word")
                    .withQueryStringParameter("counterpart", "just counterpart")
                    .withQueryStringParameter("purpose", "just purpose")
                    .withQueryStringParameter("accountIds", "1,2,3")
                    .withQueryStringParameter("minBankBookingDate", "2019-01-01")
                    .withQueryStringParameter("maxBankBookingDate", "2019-01-02")
                    .withQueryStringParameter("minFinapiBookingDate", "2019-01-03")
                    .withQueryStringParameter("maxFinapiBookingDate", "2019-01-04")
                    .withQueryStringParameter("minAmount", "99.99")
                    .withQueryStringParameter("maxAmount", "999.99")
                    .withQueryStringParameter("direction", "all")
                    .withQueryStringParameter("labelIds", "2,3,4")
                    .withQueryStringParameter("categoryIds", "3,4,5")
                    .withQueryStringParameter("isNew", "true")
                    .withQueryStringParameter("minImportDate", "2019-01-05")
                    .withQueryStringParameter("maxImportDate", "2019-01-06")
                    .withQueryStringParameter("includeSubCashFlows", "false")
                    .withQueryStringParameter("order", "id,asc", "name,desc")
            )
            .respond(
                HttpResponse.response('{' +
                    "  \"cashFlows\": [{}]," +
                    "  \"totalIncome\": 199.99," +
                    "  \"totalSpending\": -99.99," +
                    "  \"totalBalance\": 100" +
                    '}')
            );
        final CashFlows cashFlows = new FpCategories(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).cashFlows(
            new CashFlowsCriteria()
                .withSearch("just a word")
                .withCounterpart("just counterpart")
                .withPurpose("just purpose")
                .withAccounts(new IterableOfLongs(1L, 2L, 3L))
                .withMinBankBookingDate(new LocalDateOf("2019-01-01").get())
                .withMaxBankBookingDate(new LocalDateOf("2019-01-02").get())
                .withMinFinapiBookingDate(new LocalDateOf("2019-01-03").get())
                .withMaxFinapiBookingDate(new LocalDateOf("2019-01-04").get())
                .withMinAmount(new BigDecimal("99.99"))
                .withMaxAmount(new BigDecimal("999.99"))
                .withDirection(Direction.ALL)
                .withLabels(new IterableOfLongs(2L, 3L, 4L))
                .withCategories(new IterableOfLongs(3L, 4L, 5L))
                .withIsNew(true)
                .withMinImportDate(new LocalDateOf("2019-01-05").get())
                .withMaxImportDate(new LocalDateOf("2019-01-06").get())
                .withoutSubCashFlows()
                .withOrdering("id,asc", "name,desc")
        );
        cashFlows.iterator().next();
        assertThat(cashFlows.income()).isEqualTo(new BigDecimal("199.99"));
        assertThat(cashFlows.spending()).isEqualTo(new BigDecimal("-99.99"));
        assertThat(cashFlows.balance()).isEqualTo(new BigDecimal("100"));
    }

    @Test
    public void testCreate() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/categories")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"name\": \"Sport & Fitness\"," +
                        "  \"parentId\": 373" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(HttpStatus.SC_CREATED)
            );
        new FpCategories(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreateCategoryParameters()
                .withName("Sport & Fitness")
                .withParent(373L)
        );
    }

    @Test
    public void testTrainCategorization() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/categories/trainCategorization")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"transactionData\": [" +
                        "    {" +
                        "      \"accountTypeId\": 1," +
                        "      \"amount\": -99.99," +
                        "      \"purpose\": \"Restaurantbesuch\"," +
                        "      \"counterpart\": \"Bar Centrale\"," +
                        "      \"counterpartIban\": \"DE13700800000061110500\"," +
                        "      \"counterpartAccountNumber\": \"61110500\"," +
                        "      \"counterpartBlz\": \"70080000\"," +
                        "      \"counterpartBic\": \"DRESDEFF700\"," +
                        "      \"mcCode\": \"5542\"" +
                        "    }" +
                        "  ]," +
                        "  \"categoryId\": 378" +
                        '}'))
            )
            .respond(
                HttpResponse.response().withStatusCode(HttpStatus.SC_OK)
            );
        new FpCategories(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).trainCategorization(
            new TrainCategorizationParameters()
                .withTransactions(
                    new TrainCategorizationTransaction()
                        .withAccountType(Type.Checking)
                        .withAmount(new BigDecimal("-99.99"))
                        .withPurpose("Restaurantbesuch")
                        .withCounterpart("Bar Centrale")
                        .withCounterpartIban("DE13700800000061110500")
                        .withCounterpartAccountNumber("61110500")
                        .withCounterpartBlz("70080000")
                        .withCounterpartBic("DRESDEFF700")
                        .withMcCode("5542")
                )
                .withCategory(378L)
        );
    }

    @Test
    public void testDeleteAll() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/categories")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(HttpResponse.response("{\"identifiers\":[1,2,3]}"));
        new FpCategories(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).deleteAll();
    }
}
