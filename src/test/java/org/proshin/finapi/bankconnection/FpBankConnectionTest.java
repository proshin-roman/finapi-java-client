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

import java.util.Optional;
import org.hamcrest.BaseMatcher;
import static org.hamcrest.CoreMatchers.hasItem;
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
import org.proshin.finapi.bankconnection.in.FpEditParameters;
import org.proshin.finapi.bankconnection.out.Status;
import org.proshin.finapi.bankconnection.out.TwoStepProcedure;
import org.proshin.finapi.bankconnection.out.Type;
import org.proshin.finapi.bankconnection.out.UpdateResult;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public class FpBankConnectionTest {

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10014);
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
    public void testParsingJsonStructure() {
        final FpBankConnection connection = new FpBankConnection(
            new FpEndpoint("http://10014"),
            new FakeAccessToken("user-token"),
            new JSONObject('{' +
                "  \"id\": 42," +
                "  \"bankId\": 277672," +
                "  \"bank\": {" +
                "    \"id\": 277672," +
                "  }," +
                "  \"name\": \"Bank Connection\"," +
                "  \"bankingUserId\": \"user ID\"," +
                "  \"bankingCustomerId\": \"customer ID\"," +
                "  \"bankingPin\": \"pin\"," +
                "  \"type\": \"DEMO\"," +
                "  \"updateStatus\": \"READY\"," +
                "  \"categorizationStatus\": \"PENDING\"," +
                "  \"lastManualUpdate\": {" +
                "    \"result\": \"INTERNAL_SERVER_ERROR\"," +
                "    \"errorMessage\": \"Internal server error\"," +
                "    \"errorType\": \"TECHNICAL\"," +
                "    \"timestamp\": \"2018-01-01 00:00:00.000\"" +
                "  }," +
                "  \"lastAutoUpdate\": {" +
                "    \"result\": \"BANK_SERVER_REJECTION\"," +
                "    \"errorMessage\": \"Internal bank error\"," +
                "    \"errorType\": \"BUSINESS\"," +
                "    \"timestamp\": \"2018-01-11 00:00:00.000\"" +
                "  }," +
                "  \"twoStepProcedures\": [" +
                "    {" +
                "      \"procedureId\": \"955\"," +
                "      \"procedureName\": \"mobileTAN\"," +
                "      \"procedureChallengeType\": \"TEXT\"," +
                "      \"implicitExecute\": true" +
                "    }" +
                "  ]," +
                "  \"ibanOnlyMoneyTransferSupported\": true," +
                "  \"ibanOnlyDirectDebitSupported\": true," +
                "  \"collectiveMoneyTransferSupported\": true," +
                "  \"defaultTwoStepProcedureId\": \"955\"," +
                "  \"accountIds\": [1, 2, 3]," +
                "  \"owners\": [" +
                "    {" +
                "      \"firstName\": \"Max\"," +
                "      \"lastName\": \"Mustermann\"," +
                "      \"salutation\": \"Herr\"," +
                "      \"title\": \"Dr.\"," +
                "      \"email\": \"email@localhost.de\"," +
                "      \"dateOfBirth\": \"1980-01-01\"," +
                "      \"postCode\": \"80000\"," +
                "      \"country\": \"Deutschland\"," +
                "      \"city\": \"München\"," +
                "      \"street\": \"Musterstraße\"," +
                "      \"houseNumber\": \"99\"" +
                "    }" +
                "  ]" +
                '}'),
            "/api/v1/bankConnections"
        );
        assertThat(connection.id(), is(42L));
        assertThat(connection.bank().id(), is(277672L));
        assertThat(connection.name(), is(Optional.of("Bank Connection")));

        assertThat(connection.credentials().bankingUserId(), is(Optional.of("user ID")));
        assertThat(connection.credentials().bankingCustomerId(), is(Optional.of("customer ID")));
        assertThat(connection.credentials().bankingPin(), is(Optional.of("pin")));

        assertThat(connection.type(), is(Type.DEMO));

        assertThat(connection.status().update(), is(Status.UpdateStatus.READY));
        assertThat(connection.status().categorization(), is(Status.CategorizationStatus.PENDING));

        assertThat(connection.lastManualUpdate().isPresent(), is(true));
        assertThat(connection.lastManualUpdate().get().result(), is(UpdateResult.Result.INTERNAL_SERVER_ERROR));
        assertThat(connection.lastManualUpdate().get().errorMessage(), is(Optional.of("Internal server error")));
        assertThat(connection.lastManualUpdate().get().errorType(), is(Optional.of(UpdateResult.ErrorType.TECHNICAL)));
        assertThat(
            connection.lastManualUpdate().get().timestamp(),
            is(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get())
        );

        assertThat(connection.lastAutoUpdate().isPresent(), is(true));
        assertThat(connection.lastAutoUpdate().get().result(), is(UpdateResult.Result.BANK_SERVER_REJECTION));
        assertThat(connection.lastAutoUpdate().get().errorMessage(), is(Optional.of("Internal bank error")));
        assertThat(connection.lastAutoUpdate().get().errorType(), is(Optional.of(UpdateResult.ErrorType.BUSINESS)));
        assertThat(
            connection.lastAutoUpdate().get().timestamp(),
            is(new OffsetDateTimeOf("2018-01-11 00:00:00.000").get())
        );

        assertThat(connection.twoStepProcedures().defaultOne().isPresent(), is(true));
        assertThat(connection.twoStepProcedures().defaultOne().get().id(), is("955"));
        assertThat(
            connection.twoStepProcedures().all(),
            hasItem(
                new TwoStepProcedureMatcher(
                    "955",
                    "mobileTAN",
                    TwoStepProcedure.Type.TEXT,
                    true
                )
            )
        );

        assertThat(connection.ibanOnlyDirectDebitSupported(), is(true));
        assertThat(connection.ibanOnlyMoneyTransferSupported(), is(true));
        //noinspection deprecation
        assertThat(connection.collectiveMoneyTransferSupported(), is(true));

        assertThat(connection.accounts(), hasItems(1L, 2L, 3L));
        assertThat(connection.owners().iterator().next().firstName(), is(Optional.of("Max")));
    }

    @Test
    public void testEdit() {
        server
            .when(
                HttpRequest.request("/api/v1/bankConnections/42")
                    .withMethod("PATCH")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"bankingUserId\": \"new user ID\"," +
                        "  \"bankingCustomerId\": \"new customer ID\"," +
                        "  \"bankingPin\": \"new pin\"," +
                        "  \"defaultTwoStepProcedureId\": \"956\"," +
                        "  \"name\": \"new name\"" +
                        '}'))
            ).respond(
            HttpResponse.response("{}")
        );

        new FpBankConnection(
            new FpEndpoint("http://127.0.0.1:10014"),
            new FakeAccessToken("user-token"),
            new JSONObject().put("id", 42L),
            "/api/v1/bankConnections"
        ).edit(new FpEditParameters()
            .withUserId("new user ID")
            .withCustomerId("new customer ID")
            .withPin("new pin")
            .withDefaultTwoStepProcedure("956")
            .withName("new name")
        );
    }

    @Test
    public void testDelete() {
        server
            .when(
                HttpRequest.request("/api/v1/bankConnections/42")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
            ).respond(
            HttpResponse.response().withStatusCode(200)
        );

        new FpBankConnection(
            new FpEndpoint("http://127.0.0.1:10014"),
            new FakeAccessToken("user-token"),
            new JSONObject().put("id", 42L),
            "/api/v1/bankConnections"
        ).delete();
    }

    private static final class TwoStepProcedureMatcher extends BaseMatcher<TwoStepProcedure> {

        private final String id;
        private final String name;
        private final TwoStepProcedure.Type type;
        private final boolean implicit;

        private TwoStepProcedureMatcher(final String id, final String name, final TwoStepProcedure.Type type,
            final boolean implicit) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.implicit = implicit;
        }

        @Override
        public boolean matches(final Object item) {
            final TwoStepProcedure tsp = (TwoStepProcedure) item;
            //noinspection UnclearExpression,OverlyComplexBooleanExpression
            return this.id.equals(tsp.id())
                && this.name.equals(tsp.name())
                && this.type == tsp.type().get()
                && this.implicit == tsp.implicitExecute();
        }

        @Override
        public void describeTo(final Description description) {

        }
    }
}
