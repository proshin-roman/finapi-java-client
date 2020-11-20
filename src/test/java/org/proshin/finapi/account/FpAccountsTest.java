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
import org.cactoos.iterable.IterableOfLongs;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.account.in.DailyBalancesCriteria;
import org.proshin.finapi.account.in.FpQueryCriteria;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

final class FpAccountsTest extends TestWithMockedEndpoint {

    @Test
    void testOne() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/accounts/2")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer random-token")
            )
            .respond(
                HttpResponse.response("{}")
            );

        final Account account = new FpAccounts(
            this.endpoint(),
            new FakeAccessToken("random-token")
        ).one(2L);
    }

    @Test
    void testQuery() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/accounts")
                    .withQueryStringParameter("ids", "1,2")
                    .withQueryStringParameter("search", "just%20a%20word")
                    .withQueryStringParameter("accountTypes", "Checking,Security")
                    .withQueryStringParameter("bankConnectionIds", "3,4")
                    .withQueryStringParameter("minLastSuccessfulUpdate", "2018-01-01")
                    .withQueryStringParameter("maxLastSuccessfulUpdate", "2018-01-02")
                    .withQueryStringParameter("minBalance", "123.45")
                    .withQueryStringParameter("maxBalance", "234.56")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer random-token")
            )
            .respond(
                HttpResponse.response("{\"accounts\": [{}]}")
            );
        new FpAccounts(
            this.endpoint(),
            new FakeAccessToken("random-token")
        ).query(new FpQueryCriteria()
            .withIds(new IterableOfLongs(1L, 2L))
            .withSearch("just a word")
            .withTypes(Type.Checking, Type.Security)
            .withBankConnections(new IterableOfLongs(3L, 4L))
            .withMinLastSuccessfulUpdate(new OffsetDateTimeOf("2018-01-01 02:03:04.555").get())
            .withMaxLastSuccessfulUpdate(new OffsetDateTimeOf("2018-01-02 03:04:05.666").get())
            .withMinBalance(new BigDecimal("123.45"))
            .withMaxBalance(new BigDecimal("234.56"))
        ).iterator().next();
    }

    @Test
    void testDailyBalances() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/accounts/dailyBalances")
                    .withQueryStringParameter("accountIds", "1,2")
                    .withQueryStringParameter("startDate", "2018-01-01")
                    .withQueryStringParameter("endDate", "2018-01-02")
                    .withQueryStringParameter("withProjection", "false")
                    .withQueryStringParameter("page", "1")
                    .withQueryStringParameter("perPage", "20")
                    .withQueryStringParameter("order", "date,asc", "balance,desc")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer random-token")
            )
            .respond(
                HttpResponse.response("{\"accounts\": [{}]}")
            );
        new FpAccounts(
            this.endpoint(),
            new FakeAccessToken("random-token")
        ).dailyBalances(
            new DailyBalancesCriteria()
                .withAccounts(new IterableOfLongs(1L, 2L))
                .withStartDate(new OffsetDateTimeOf("2018-01-01 02:03:04.555").get())
                .withEndDate(new OffsetDateTimeOf("2018-01-02 02:03:04.555").get())
                .withoutProjection()
                .withPage(1, 20)
                .orderBy("date,asc", "balance,desc")
        );
    }
}
