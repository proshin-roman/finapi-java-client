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
import org.junit.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.account.Type;
import org.proshin.finapi.bankconnection.in.ImportBankConnectionParameters;
import org.proshin.finapi.bankconnection.in.UpdateBankConnectionParameters;
import org.proshin.finapi.fake.FakeAccessToken;

public class FpBankConnectionsTest extends TestWithMockedEndpoint {

    @Test
    public void testOne() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/bankConnections/42")
                    .withHeader("Authorization", "Bearer user-token")
                    .withMethod("GET")
            ).respond(
            HttpResponse.response("{}")
        );
        new FpBankConnections(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).one(42L);
    }

    @Test
    public void testQuery() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/bankConnections")
                    .withHeader("Authorization", "Bearer user-token")
                    .withMethod("GET")
                    .withQueryStringParameter("ids", "42,43,44")
            ).respond(
            HttpResponse.response("{\"connections\":[{\"id\":42}]}")
        );
        final Iterable<BankConnection> connections = new FpBankConnections(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).query(new IterableOfLongs(42L, 43L, 44L));
        final Iterator<BankConnection> iterator = connections.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next().id(), is(42L));
    }

    @Test
    public void testImportNew() throws Exception {
        this.server()
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
                        "  \"accountTypes\": [ \"Checking\", \"Security\" ]," +
                        "  \"challengeResponse\": \"0123\"" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        final Future<BankConnection> future = new FpBankConnections(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).importNew(
            new ImportBankConnectionParameters()
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
        this.server()
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
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).update(new UpdateBankConnectionParameters()
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
        this.server()
            .when(
                HttpRequest.request("/api/v1/bankConnections")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{\"identifiers\":[]}")
            );
        new FpBankConnections(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).deleteAll();
    }
}
