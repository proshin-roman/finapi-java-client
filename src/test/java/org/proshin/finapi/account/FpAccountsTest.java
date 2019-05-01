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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.account.in.DailyBalancesCriteria;
import org.proshin.finapi.account.in.FpQueryCriteria;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public class FpAccountsTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10003);
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
                HttpRequest.request("/api/v1/accounts/2")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer random-token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        final Account account = new FpAccounts(
            new FpEndpoint("http://127.0.0.1:10003"),
            new FakeAccessToken("random-token")
        ).one(2L);
    }

    @Test
    public void testQuery() {
        server
            .when(
                HttpRequest.request("/api/v1/accounts")
                    .withQueryStringParameter("ids", "1%2C2")
                    .withQueryStringParameter("search", "just+a+word")
                    .withQueryStringParameter("accountTypeIds", "1%2C4")
                    .withQueryStringParameter("bankConnectionIds", "3%2C4")
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
            new FpEndpoint("http://127.0.0.1:10003"),
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
    public void testDailyBalances() {
        server
            .when(
                HttpRequest.request("/api/v1/accounts/dailyBalances")
                    .withQueryStringParameter("accountIds", "1%2C2")
                    .withQueryStringParameter("startDate", "2018-01-01")
                    .withQueryStringParameter("endDate", "2018-01-02")
                    .withQueryStringParameter("withProjection", "false")
                    .withQueryStringParameter("page", "1")
                    .withQueryStringParameter("perPage", "20")
                    .withQueryStringParameter("order", "date%2Casc", "balance%2Cdesc")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer random-token")
            )
            .respond(
                HttpResponse.response("{\"accounts\": [{}]}")
            );
        new FpAccounts(
            new FpEndpoint("http://127.0.0.1:10003"),
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
