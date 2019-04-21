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
package org.proshin.finapi.security;

import org.cactoos.iterable.IterableOfLongs;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.security.in.SecuritiesCriteria;

public class FpSecuritiesTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10009);
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
                HttpRequest.request("/api/v1/securities/123")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpSecurities(
            new FpEndpoint("http://127.0.0.1:10009"),
            new FakeAccessToken("user-token")
        ).one(123L);
    }

    @Test
    public void testQuery() {
        server
            .when(
                HttpRequest.request("/api/v1/securities/")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("ids", "1%2C2%2C3")
                    .withQueryStringParameter("search", "just+a+word")
                    .withQueryStringParameter("accountIds", "2%2C3%2C4")
                    .withQueryStringParameter("page", "12")
                    .withQueryStringParameter("perPage", "23")
                    .withQueryStringParameter("order", "id%2Casc", "name%2Cdesc")
            )
            .respond(
                HttpResponse.response("{\"securities\":[{}]}")
            );
        new FpSecurities(
            new FpEndpoint("http://127.0.0.1:10009"),
            new FakeAccessToken("user-token")
        ).query(
            new SecuritiesCriteria()
                .withIds(new IterableOfLongs(1L, 2L, 3L))
                .withSearch("just a word")
                .withAccounts(new IterableOfLongs(2L, 3L, 4L))
                .withPage(12, 23)
                .orderBy("id,asc", "name,desc")
        ).items().iterator().next();
    }
}
