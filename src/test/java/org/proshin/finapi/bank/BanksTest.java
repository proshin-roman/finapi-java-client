/*
 * Copyright 2018 Roman Proshin
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
package org.proshin.finapi.bank;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.IterableOfLongs;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import static org.proshin.finapi.bank.Bank.DataSource.FINTS_SERVER;
import static org.proshin.finapi.bank.Bank.DataSource.WEB_SCRAPER;
import org.proshin.finapi.bank.in.BanksCriteria;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.primitives.paging.PagingCriteria;

public final class BanksTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10016);
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
                HttpRequest.request("/api/v1/banks/123")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpBanks(
            new FpEndpoint("http://127.0.0.1:10016"),
            new FakeAccessToken("user-token")
        ).one(123L);
    }

    @Test
    public void testSearch() {
        server
            .when(
                HttpRequest.request("/api/v1/banks")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("ids", "1%2C2%2C3")
                    .withQueryStringParameter("search", "just+a+word")
                    .withQueryStringParameter("isSupported", "true")
                    .withQueryStringParameter("pinsAreVolatile", "true")
                    .withQueryStringParameter("supportedDataSources", "WEB_SCRAPER%2CFINTS_SERVER")
                    .withQueryStringParameter("location", "DE")
                    .withQueryStringParameter("isTestBank", "true")
                    .withQueryStringParameter("page", "2")
                    .withQueryStringParameter("perPage", "20")
                    .withQueryStringParameter("order", "id%2Casc", "name%2Cdesc")
            )
            .respond(
                HttpResponse.response("{\"banks\":[{}]}")
            );
        final Page<Bank> banks = new FpBanks(
            new FpEndpoint("http://127.0.0.1:10016"),
            new FakeAccessToken("user-token")
        ).search(
            new BanksCriteria()
                .withIds(new IterableOfLongs(1L, 2L, 3L))
                .withSearch("just a word")
                .withSupporting(true)
                .withPinsAreVolatile(true)
                .withSupportedDataSources(new IterableOf<>(WEB_SCRAPER, FINTS_SERVER))
                .withLocation(new IterableOf<>("DE"))
                .withTestBank(true)
                .withPaging(new PagingCriteria(2, 20, "id,asc", "name,desc"))
        );
        final Bank bank = banks.items().iterator().next();
    }
}
