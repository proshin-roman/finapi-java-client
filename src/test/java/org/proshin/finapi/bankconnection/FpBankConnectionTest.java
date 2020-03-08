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
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.bankconnection.in.EditBankConnectionParameters;
import org.proshin.finapi.bankconnection.out.Status;
import org.proshin.finapi.bankconnection.out.TwoStepProcedure;
import org.proshin.finapi.bankconnection.out.Type;
import org.proshin.finapi.bankconnection.out.UpdateResult;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public class FpBankConnectionTest extends TestWithMockedEndpoint {

    @Test
    public void testParsingJsonStructure() {
        final FpBankConnection connection = new FpBankConnection(
            this.endpoint(),
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
        assertThat(connection.id()).isEqualTo(42L);
        assertThat(connection.bank().id()).isEqualTo(277672L);
        assertThat(connection.name()).isEqualTo(Optional.of("Bank Connection"));

        assertThat(connection.credentials().bankingUserId()).isEqualTo(Optional.of("user ID"));
        assertThat(connection.credentials().bankingCustomerId()).isEqualTo(Optional.of("customer ID"));
        assertThat(connection.credentials().bankingPin()).isEqualTo(Optional.of("pin"));

        assertThat(connection.type()).isEqualTo(Type.DEMO);

        assertThat(connection.status().update()).isEqualTo(Status.UpdateStatus.READY);
        assertThat(connection.status().categorization()).isEqualTo(Status.CategorizationStatus.PENDING);

        assertThat(connection.lastManualUpdate().isPresent()).isTrue();
        assertThat(connection.lastManualUpdate().get().result()).isEqualTo(UpdateResult.Result.INTERNAL_SERVER_ERROR);
        assertThat(connection.lastManualUpdate().get().errorMessage())
            .isEqualTo(Optional.of("Internal server error"));
        assertThat(connection.lastManualUpdate().get().errorType())
            .isEqualTo(Optional.of(UpdateResult.ErrorType.TECHNICAL));
        assertThat(connection.lastManualUpdate().get().timestamp())
            .isEqualTo(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get());

        assertThat(connection.lastAutoUpdate().isPresent()).isTrue();
        assertThat(connection.lastAutoUpdate().get().result()).isEqualTo(UpdateResult.Result.BANK_SERVER_REJECTION);
        assertThat(connection.lastAutoUpdate().get().errorMessage())
            .isEqualTo(Optional.of("Internal bank error"));
        assertThat(connection.lastAutoUpdate().get().errorType())
            .isEqualTo(Optional.of(UpdateResult.ErrorType.BUSINESS));
        assertThat(connection.lastAutoUpdate().get().timestamp())
            .isEqualTo(new OffsetDateTimeOf("2018-01-11 00:00:00.000").get());

        assertThat(connection.twoStepProcedures().defaultOne().isPresent()).isTrue();
        assertThat(connection.twoStepProcedures().defaultOne().get().id()).isEqualTo("955");

        connection.twoStepProcedures().all().forEach(tsp -> {
            assertThat(tsp.id()).isEqualTo("955");
            assertThat(tsp.name()).isEqualTo("mobileTAN");
            assertThat(tsp.type()).isEqualTo(Optional.of(TwoStepProcedure.Type.TEXT));
            assertThat(tsp.implicitExecute()).isTrue();
        });

        assertThat(connection.ibanOnlyDirectDebitSupported()).isTrue();
        assertThat(connection.ibanOnlyMoneyTransferSupported()).isTrue();
        //noinspection deprecation
        assertThat(connection.collectiveMoneyTransferSupported()).isTrue();

        assertThat(connection.accounts()).containsExactlyInAnyOrder(1L, 2L, 3L);
        assertThat(connection.owners().iterator().next().firstName()).isEqualTo(Optional.of("Max"));
    }

    @Test
    public void testEdit() {
        this.server()
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
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject().put("id", 42L),
            "/api/v1/bankConnections"
        ).edit(new EditBankConnectionParameters()
            .withUserId("new user ID")
            .withCustomerId("new customer ID")
            .withPin("new pin")
            .withDefaultTwoStepProcedure("956")
            .withName("new name")
        );
    }

    @Test
    public void testDelete() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/bankConnections/42")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
            ).respond(
            HttpResponse.response().withStatusCode(200)
        );

        new FpBankConnection(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject().put("id", 42L),
            "/api/v1/bankConnections"
        ).delete();
    }
}
