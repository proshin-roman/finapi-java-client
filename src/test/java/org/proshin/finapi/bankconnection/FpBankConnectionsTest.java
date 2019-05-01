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
package org.proshin.finapi.bankconnection;

import java.util.Iterator;
import java.util.concurrent.Future;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.IterableOfLongs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.account.Type;
import org.proshin.finapi.bankconnection.in.ImportParameters;
import org.proshin.finapi.bankconnection.in.UpdateParameters;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

public class FpBankConnectionsTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10015);
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
                HttpRequest.request("/api/v1/bankConnections/42")
                    .withHeader("Authorization", "Bearer user-token")
                    .withMethod("GET")
            ).respond(
            HttpResponse.response("{}")
        );
        new FpBankConnections(
            new FpEndpoint("http://127.0.0.1:10015"),
            new FakeAccessToken("user-token")
        ).one(42L);
    }

    @Test
    public void testQuery() {
        server
            .when(
                HttpRequest.request("/api/v1/bankConnections")
                    .withHeader("Authorization", "Bearer user-token")
                    .withMethod("GET")
                    .withQueryStringParameter("ids", "42,43,44")
            ).respond(
            HttpResponse.response("{\"connections\":[{\"id\":42}]}")
        );
        final Iterable<BankConnection> connections = new FpBankConnections(
            new FpEndpoint("http://127.0.0.1:10015"),
            new FakeAccessToken("user-token")
        ).query(new IterableOfLongs(42L, 43L, 44L));
        final Iterator<BankConnection> iterator = connections.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next().id(), is(42L));
    }

    @Test
    public void testImportNew() throws Exception {
        server
            .when(
                HttpRequest.request("/api/v1/bankConnections/import")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"bankId\": 277672," +
                        "  \"bankingUserId\": \"user id\"," +
                        "  \"bankingCustomerId\": \"customer id\"," +
                        "  \"bankingPin\": \"pin\"," +
                        "  \"storePin\": true," +
                        "  \"name\": \"Bank connection\"," +
                        "  \"skipPositionsDownload\": true," +
                        "  \"loadOwnerData\": true," +
                        "  \"maxDaysForDownload\": 365," +
                        "  \"accountTypeIds\": [1, 4]," +
                        "  \"challengeResponse\": \"0123\"" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        final Future<BankConnection> future = new FpBankConnections(
            new FpEndpoint("http://127.0.0.1:10015"),
            new FakeAccessToken("user-token")
        ).importNew(
            new ImportParameters()
                .withBank(277672L)
                .withUserId("user id")
                .withCustomerId("customer id")
                .withPin("pin")
                .withStorePin()
                .withName("Bank connection")
                .withSkipPositionsDownload()
                .withLoadOwnerData()
                .withMaxDaysForDownload(365)
                .withAccountTypes(new IterableOf<>(Type.Checking, Type.Security))
                .withChallengeResponse("0123")
        );
        final BankConnection connection = future.get();
    }

    @Test
    public void testUpdate() throws Exception {
        server
            .when(
                HttpRequest.request("/api/v1/bankConnections/update")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"bankConnectionId\": 42," +
                        "  \"bankingPin\": \"pin\"," +
                        "  \"storePin\": true," +
                        "  \"importNewAccounts\": true," +
                        "  \"skipPositionsDownload\": true," +
                        "  \"loadOwnerData\": true," +
                        "  \"challengeResponse\": \"0123\"" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}")
            );
        final Future<BankConnection> future = new FpBankConnections(
            new FpEndpoint("http://127.0.0.1:10015"),
            new FakeAccessToken("user-token")
        ).update(new UpdateParameters()
            .withBankConnection(42L)
            .withPin("pin")
            .withStorePin()
            .withImportNewAccounts()
            .withSkipPositionsDownload()
            .withLoadOwnerData()
            .withChallengeResponse("0123")
        );
        final BankConnection connection = future.get();
    }

    @Test
    public void testDeleteAll() {
        server
            .when(
                HttpRequest.request("/api/v1/bankConnections")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{\"identifiers\":[]}")
            );
        new FpBankConnections(
            new FpEndpoint("http://127.0.0.1:10015"),
            new FakeAccessToken("user-token")
        ).deleteAll();
    }
}
