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
import java.util.Optional;
import java.util.concurrent.Future;
import org.cactoos.iterable.IterableOf;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Ignore;
import org.junit.Test;
import org.proshin.finapi.bankconnection.in.ImportParameters;
import org.proshin.finapi.bankconnection.in.UpdateParameters;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.fake.FakeEndpoint;
import org.proshin.finapi.fake.FakeRoute;
import org.proshin.finapi.fake.matcher.path.ExactPathMatcher;

public class FpBankConnectionsTest {

    @Test
    public void testOne() {
        final BankConnection connection =
            new FpBankConnections(
                new FakeEndpoint(
                    new FakeRoute(
                        new ExactPathMatcher("/api/v1/bankConnections/42"),
                        '{' +
                            "  \"id\": 42," +
                            "  \"bankId\": 277672," +
                            "  \"bank\": {" +
                            "    \"id\": 277672," +
                            "    \"name\": \"FinAPI Test Bank\"," +
                            "    \"loginHint\": \"Bitte geben Sie Ihre Online-Identifikation und Ihre PIN ein.\"," +
                            "    \"bic\": \"TESTBANKING\"," +
                            "    \"blz\": \"00000000\"," +
                            "    \"blzs\": []," +
                            "    \"loginFieldUserId\": \"Onlinebanking-ID\"," +
                            "    \"loginFieldCustomerId\": \"Kunden-ID\"," +
                            "    \"loginFieldPin\": \"PIN\"," +
                            "    \"isCustomerIdPassword\": true," +
                            "    \"isSupported\": true," +
                            "    \"supportedDataSources\": [" +
                            "      \"FINTS_SERVER\"," +
                            "      \"WEB_SCRAPER\"" +
                            "    ]," +
                            "    \"pinsAreVolatile\": true," +
                            "    \"location\": \"DE\"," +
                            "    \"city\": \"München\"," +
                            "    \"isTestBank\": true," +
                            "    \"popularity\": 95," +
                            "    \"health\": 100," +
                            "    \"lastCommunicationAttempt\": \"2018-01-01 00:00:00.000\"," +
                            "    \"lastSuccessfulCommunication\": \"2018-01-01 00:00:00.000\"" +
                            "  }," +
                            "  \"name\": \"Bank Connection\"," +
                            "  \"bankingUserId\": \"XXXXX\"," +
                            "  \"bankingCustomerId\": \"XXXXX\"," +
                            "  \"bankingPin\": \"XXXXX\"," +
                            "  \"type\": \"DEMO\"," +
                            "  \"updateStatus\": \"READY\"," +
                            "  \"categorizationStatus\": \"READY\"," +
                            "  \"lastManualUpdate\": {" +
                            "    \"result\": \"INTERNAL_SERVER_ERROR\"," +
                            "    \"errorMessage\": \"Internal server error\"," +
                            "    \"errorType\": \"TECHNICAL\"," +
                            "    \"timestamp\": \"2018-01-01 00:00:00.000\"" +
                            "  }," +
                            "  \"lastAutoUpdate\": {" +
                            "    \"result\": \"INTERNAL_SERVER_ERROR\"," +
                            "    \"errorMessage\": \"Internal server error\"," +
                            "    \"errorType\": \"TECHNICAL\"," +
                            "    \"timestamp\": \"2018-01-01 00:00:00.000\"" +
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
                            "  \"accountIds\": [" +
                            "    1," +
                            "    2," +
                            "    3" +
                            "  ]," +
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
                            '}'
                    )
                ),
                new FakeAccessToken("random token")
            ).one(42L);
        this.assertBankConnection(connection);
    }

    /**
     * @todo #46 Fix a path in org.proshin.finapi.bankconnection.FpBankConnections#query(java.lang.Iterable) - it
     *  should start with /
     */
    @Test
    @Ignore
    public void testQuery() {
        final Iterable<BankConnection> connections = new FpBankConnections(
            new FakeEndpoint(
                new FakeRoute(
                    new ExactPathMatcher("/api/v1/bankConnections"),
                    '{' +
                        "  \"connections\": [" +
                        "    {" +
                        "      \"id\": 42," +
                        "      \"bankId\": 277672," +
                        "      \"bank\": {" +
                        "        \"id\": 277672," +
                        "        \"name\": \"FinAPI Test Bank\"," +
                        "        \"loginHint\": \"Bitte geben Sie Ihre Online-Identifikation und Ihre PIN ein.\"," +
                        "        \"bic\": \"TESTBANKING\"," +
                        "        \"blz\": \"00000000\"," +
                        "        \"blzs\": []," +
                        "        \"loginFieldUserId\": \"Onlinebanking-ID\"," +
                        "        \"loginFieldCustomerId\": \"Kunden-ID\"," +
                        "        \"loginFieldPin\": \"PIN\"," +
                        "        \"isCustomerIdPassword\": true," +
                        "        \"isSupported\": true," +
                        "        \"supportedDataSources\": [" +
                        "          \"FINTS_SERVER\"," +
                        "          \"WEB_SCRAPER\"" +
                        "        ]," +
                        "        \"pinsAreVolatile\": true," +
                        "        \"location\": \"DE\"," +
                        "        \"city\": \"München\"," +
                        "        \"isTestBank\": true," +
                        "        \"popularity\": 95," +
                        "        \"health\": 100," +
                        "        \"lastCommunicationAttempt\": \"2018-01-01 00:00:00.000\"," +
                        "        \"lastSuccessfulCommunication\": \"2018-01-01 00:00:00.000\"" +
                        "      }," +
                        "      \"name\": \"Bank Connection\"," +
                        "      \"bankingUserId\": \"XXXXX\"," +
                        "      \"bankingCustomerId\": \"XXXXX\"," +
                        "      \"bankingPin\": \"XXXXX\"," +
                        "      \"type\": \"DEMO\"," +
                        "      \"updateStatus\": \"READY\"," +
                        "      \"categorizationStatus\": \"READY\"," +
                        "      \"lastManualUpdate\": {" +
                        "        \"result\": \"INTERNAL_SERVER_ERROR\"," +
                        "        \"errorMessage\": \"Internal server error\"," +
                        "        \"errorType\": \"TECHNICAL\"," +
                        "        \"timestamp\": \"2018-01-01 00:00:00.000\"" +
                        "      }," +
                        "      \"lastAutoUpdate\": {" +
                        "        \"result\": \"INTERNAL_SERVER_ERROR\"," +
                        "        \"errorMessage\": \"Internal server error\"," +
                        "        \"errorType\": \"TECHNICAL\"," +
                        "        \"timestamp\": \"2018-01-01 00:00:00.000\"" +
                        "      }," +
                        "      \"twoStepProcedures\": [" +
                        "        {" +
                        "          \"procedureId\": \"955\"," +
                        "          \"procedureName\": \"mobileTAN\"," +
                        "          \"procedureChallengeType\": \"TEXT\"," +
                        "          \"implicitExecute\": true" +
                        "        }" +
                        "      ]," +
                        "      \"ibanOnlyMoneyTransferSupported\": true," +
                        "      \"ibanOnlyDirectDebitSupported\": true," +
                        "      \"collectiveMoneyTransferSupported\": true," +
                        "      \"defaultTwoStepProcedureId\": \"955\"," +
                        "      \"accountIds\": [" +
                        "        1," +
                        "        2," +
                        "        3" +
                        "      ]," +
                        "      \"owners\": [" +
                        "        {" +
                        "          \"firstName\": \"Max\"," +
                        "          \"lastName\": \"Mustermann\"," +
                        "          \"salutation\": \"Herr\"," +
                        "          \"title\": \"Dr.\"," +
                        "          \"email\": \"email@localhost.de\"," +
                        "          \"dateOfBirth\": \"1980-01-01\"," +
                        "          \"postCode\": \"80000\"," +
                        "          \"country\": \"Deutschland\"," +
                        "          \"city\": \"München\"," +
                        "          \"street\": \"Musterstraße\"," +
                        "          \"houseNumber\": \"99\"" +
                        "        }" +
                        "      ]" +
                        "    }" +
                        "  ]" +
                        '}'
                )
            ),
            new FakeAccessToken("random token")
        ).query(new IterableOf<>(42L));

        final Iterator<BankConnection> iterator = connections.iterator();
        assertThat(iterator.hasNext(), is(true));
        final BankConnection connection = iterator.next();
        assertThat(iterator.hasNext(), is(false));

        this.assertBankConnection(connection);
    }

    @Test
    public void testImportNew() throws Exception {
        final Future<BankConnection> future = new FpBankConnections(
            new FakeEndpoint(
                new FakeRoute(
                    new ExactPathMatcher("/api/v1/bankConnections/import"),
                    '{' +
                        "  \"id\": 42," +
                        "  \"bankId\": 277672," +
                        "  \"bank\": {" +
                        "    \"id\": 277672," +
                        "    \"name\": \"FinAPI Test Bank\"," +
                        "    \"loginHint\": \"Bitte geben Sie Ihre Online-Identifikation und Ihre PIN ein.\"," +
                        "    \"bic\": \"TESTBANKING\"," +
                        "    \"blz\": \"00000000\"," +
                        "    \"blzs\": []," +
                        "    \"loginFieldUserId\": \"Onlinebanking-ID\"," +
                        "    \"loginFieldCustomerId\": \"Kunden-ID\"," +
                        "    \"loginFieldPin\": \"PIN\"," +
                        "    \"isCustomerIdPassword\": true," +
                        "    \"isSupported\": true," +
                        "    \"supportedDataSources\": [" +
                        "      \"FINTS_SERVER\"," +
                        "      \"WEB_SCRAPER\"" +
                        "    ]," +
                        "    \"pinsAreVolatile\": true," +
                        "    \"location\": \"DE\"," +
                        "    \"city\": \"München\"," +
                        "    \"isTestBank\": true," +
                        "    \"popularity\": 95," +
                        "    \"health\": 100," +
                        "    \"lastCommunicationAttempt\": \"2018-01-01 00:00:00.000\"," +
                        "    \"lastSuccessfulCommunication\": \"2018-01-01 00:00:00.000\"" +
                        "  }," +
                        "  \"name\": \"Bank Connection\"," +
                        "  \"bankingUserId\": \"XXXXX\"," +
                        "  \"bankingCustomerId\": \"XXXXX\"," +
                        "  \"bankingPin\": \"XXXXX\"," +
                        "  \"type\": \"DEMO\"," +
                        "  \"updateStatus\": \"READY\"," +
                        "  \"categorizationStatus\": \"READY\"," +
                        "  \"lastManualUpdate\": {" +
                        "    \"result\": \"INTERNAL_SERVER_ERROR\"," +
                        "    \"errorMessage\": \"Internal server error\"," +
                        "    \"errorType\": \"TECHNICAL\"," +
                        "    \"timestamp\": \"2018-01-01 00:00:00.000\"" +
                        "  }," +
                        "  \"lastAutoUpdate\": {" +
                        "    \"result\": \"INTERNAL_SERVER_ERROR\"," +
                        "    \"errorMessage\": \"Internal server error\"," +
                        "    \"errorType\": \"TECHNICAL\"," +
                        "    \"timestamp\": \"2018-01-01 00:00:00.000\"" +
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
                        "  \"accountIds\": [" +
                        "    1," +
                        "    2," +
                        "    3" +
                        "  ]," +
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
                        '}'
                )
            ),
            new FakeAccessToken("random token")
        ).importNew(
            new ImportParameters()
                .withUserId("user id")
                .withPin("pin")
        );
        final BankConnection connection = future.get();
        this.assertBankConnection(connection);
    }

    @Test
    public void testUpdate() throws Exception {
        final Future<BankConnection> future = new FpBankConnections(
            new FakeEndpoint(
                new FakeRoute(
                    new ExactPathMatcher("/api/v1/bankConnections/update"),
                    '{' +
                        "  \"id\": 42," +
                        "  \"bankId\": 277672," +
                        "  \"bank\": {" +
                        "    \"id\": 277672," +
                        "    \"name\": \"FinAPI Test Bank\"," +
                        "    \"loginHint\": \"Bitte geben Sie Ihre Online-Identifikation und Ihre PIN ein.\"," +
                        "    \"bic\": \"TESTBANKING\"," +
                        "    \"blz\": \"00000000\"," +
                        "    \"blzs\": []," +
                        "    \"loginFieldUserId\": \"Onlinebanking-ID\"," +
                        "    \"loginFieldCustomerId\": \"Kunden-ID\"," +
                        "    \"loginFieldPin\": \"PIN\"," +
                        "    \"isCustomerIdPassword\": true," +
                        "    \"isSupported\": true," +
                        "    \"supportedDataSources\": [" +
                        "      \"FINTS_SERVER\"," +
                        "      \"WEB_SCRAPER\"" +
                        "    ]," +
                        "    \"pinsAreVolatile\": true," +
                        "    \"location\": \"DE\"," +
                        "    \"city\": \"München\"," +
                        "    \"isTestBank\": true," +
                        "    \"popularity\": 95," +
                        "    \"health\": 100," +
                        "    \"lastCommunicationAttempt\": \"2018-01-01 00:00:00.000\"," +
                        "    \"lastSuccessfulCommunication\": \"2018-01-01 00:00:00.000\"" +
                        "  }," +
                        "  \"name\": \"Bank Connection\"," +
                        "  \"bankingUserId\": \"XXXXX\"," +
                        "  \"bankingCustomerId\": \"XXXXX\"," +
                        "  \"bankingPin\": \"XXXXX\"," +
                        "  \"type\": \"DEMO\"," +
                        "  \"updateStatus\": \"READY\"," +
                        "  \"categorizationStatus\": \"READY\"," +
                        "  \"lastManualUpdate\": {" +
                        "    \"result\": \"INTERNAL_SERVER_ERROR\"," +
                        "    \"errorMessage\": \"Internal server error\"," +
                        "    \"errorType\": \"TECHNICAL\"," +
                        "    \"timestamp\": \"2018-01-01 00:00:00.000\"" +
                        "  }," +
                        "  \"lastAutoUpdate\": {" +
                        "    \"result\": \"INTERNAL_SERVER_ERROR\"," +
                        "    \"errorMessage\": \"Internal server error\"," +
                        "    \"errorType\": \"TECHNICAL\"," +
                        "    \"timestamp\": \"2018-01-01 00:00:00.000\"" +
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
                        "  \"accountIds\": [" +
                        "    1," +
                        "    2," +
                        "    3" +
                        "  ]," +
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
                        '}'
                )
            ),
            new FakeAccessToken("random token")
        ).update(new UpdateParameters().withPin("pin"));
        final BankConnection connection = future.get();
        this.assertBankConnection(connection);
    }

    @Test
    public void testDeleteAll() {
        assertThat(
            new FpBankConnections(
                new FakeEndpoint(
                    new FakeRoute(
                        new ExactPathMatcher("/api/v1/bankConnections"),
                        '{' +
                            "  \"identifiers\": [" +
                            "    1," +
                            "    2," +
                            "    3" +
                            "  ]" +
                            '}'
                    )
                ),
                new FakeAccessToken("random token")
            ).deleteAll(),
            hasItems(1L, 2L, 3L)
        );
    }

    /**
     * @todo #46 Assert all fields of the bank connection and make sure they are parsed as expected. Think about
     *  refactoring this method to a new matcher.
     */
    @SuppressWarnings("MethodMayBeStatic")
    private void assertBankConnection(final BankConnection connection) {
        assertThat(connection.id(), is(42L));
        assertThat(connection.bank().id(), is(277672L));
        assertThat(connection.name(), is(Optional.of("Bank Connection")));
        assertThat(connection.credentials().bankingUserId(), is(Optional.of("XXXXX")));
        assertThat(connection.credentials().bankingCustomerId(), is(Optional.of("XXXXX")));
        assertThat(connection.credentials().bankingPin(), is(Optional.of("XXXXX")));
    }
}
