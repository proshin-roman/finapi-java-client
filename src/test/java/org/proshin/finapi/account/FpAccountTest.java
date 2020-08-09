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
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.account.out.Capability;
import org.proshin.finapi.account.out.Order;
import org.proshin.finapi.account.out.Status;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.BankingInterface;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public final class FpAccountTest extends TestWithMockedEndpoint {

    @Test
    public void testFields() {
        final Account account = new FpAccount(
            this.endpoint(),
            new FakeAccessToken("secure user token"),
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"bankConnectionId\": 2," +
                "  \"accountName\": \"Testaccount\"," +
                "  \"iban\": \"DE89370400440532013000\"," +
                "  \"accountNumber\": \"12345678\"," +
                "  \"subAccountNumber\": \"1234\"," +
                "  \"accountHolderName\": \"Herr Max Mustermann\"," +
                "  \"accountHolderId\": \"XXXXX\"," +
                "  \"accountCurrency\": \"EUR\"," +
                "  \"accountTypeId\": 1," +
                "  \"accountTypeName\": \"Checking\"," +
                "  \"accountType\": \"Checking\"," +
                "  \"balance\": 91.99," +
                "  \"overdraft\": 92.99," +
                "  \"overdraftLimit\": 93.99," +
                "  \"availableFunds\": 94.99," +
                "  \"lastSuccessfulUpdate\": \"2018-01-01 02:03:04.555\"," +
                "  \"lastUpdateAttempt\": \"2018-01-02 03:04:05.666\"," +
                "  \"isNew\": true," +
                "  \"status\": \"UPDATED\"," +
                "  \"supportedOrders\": [" +
                "    \"SEPA_MONEY_TRANSFER\"," +
                "    \"SEPA_COLLECTIVE_MONEY_TRANSFER\"," +
                "    \"SEPA_BASIC_DIRECT_DEBIT\"," +
                "    \"SEPA_BASIC_COLLECTIVE_DIRECT_DEBIT\"," +
                "    \"SEPA_B2B_DIRECT_DEBIT\"," +
                "    \"SEPA_B2B_COLLECTIVE_DIRECT_DEBIT\"" +
                "  ]," +
                "  \"interfaces\": [" +
                "    {" +
                "      \"interface\": \"FINTS_SERVER\"," +
                "      \"status\": \"UPDATED\"," +
                "      \"capabilities\": [" +
                "        \"DATA_DOWNLOAD\"," +
                "        \"IBAN_ONLY_SEPA_MONEY_TRANSFER\"," +
                "        \"IBAN_ONLY_SEPA_DIRECT_DEBIT\"," +
                "        \"SEPA_MONEY_TRANSFER\"," +
                "        \"SEPA_COLLECTIVE_MONEY_TRANSFER\"," +
                "        \"SEPA_BASIC_DIRECT_DEBIT\"," +
                "        \"SEPA_BASIC_COLLECTIVE_DIRECT_DEBIT\"," +
                "        \"SEPA_B2B_DIRECT_DEBIT\"," +
                "        \"SEPA_B2B_COLLECTIVE_DIRECT_DEBIT\"" +
                "      ]," +
                "      \"lastSuccessfulUpdate\": \"2018-01-01 00:00:00.000\"," +
                "      \"lastUpdateAttempt\": \"2018-01-02 00:00:00.000\"" +
                "    }" +
                "  ]," +
                "  \"clearingAccounts\": [" +
                "    {" +
                "      \"clearingAccountId\": \"Clearing account ID\"," +
                "      \"clearingAccountName\": \"Clearing account name\"" +
                "    }" +
                "  ]" +
                '}'),
            ""
        );
        assertThat(account.id()).isEqualTo(1L);
        assertThat(account.name()).isEqualTo(Optional.of("Testaccount"));
        assertThat(account.number()).isEqualTo("12345678");
        assertThat(account.subNumber()).isEqualTo(Optional.of("1234"));
        assertThat(account.iban()).isEqualTo(Optional.of("DE89370400440532013000"));
        assertThat(account.holder().name()).isEqualTo(Optional.of("Herr Max Mustermann"));
        assertThat(account.holder().id()).isEqualTo(Optional.of("XXXXX"));
        assertThat(account.currency()).isEqualTo(Optional.of("EUR"));
        assertThat(account.type()).isEqualTo(Type.Checking);
        assertThat(account.balance()).isEqualTo(Optional.of(new BigDecimal("91.99")));
        assertThat(account.overdraft()).isEqualTo(Optional.of(new BigDecimal("92.99")));
        assertThat(account.overdraftLimit()).isEqualTo(Optional.of(new BigDecimal("93.99")));
        assertThat(account.availableFunds()).isEqualTo(Optional.of(new BigDecimal("94.99")));
        assertThat(account.lastSuccessfulUpdate()).isEqualTo(
            (Optional.of(new OffsetDateTimeOf("2018-01-01 02:03:04.555").get()))
        );
        assertThat(account.lastUpdateAttempt()).isEqualTo(Optional.of(new OffsetDateTimeOf("2018-01-02 03:04:05.666")
            .get()));
        assertThat(account.isNew()).isTrue();
        assertThat(account.status()).isEqualTo(Status.UPDATED);
        assertThat(account.supportedOrders())
            .containsExactlyInAnyOrder(
                Order.SEPA_MONEY_TRANSFER,
                Order.SEPA_COLLECTIVE_MONEY_TRANSFER,
                Order.SEPA_BASIC_DIRECT_DEBIT,
                Order.SEPA_BASIC_COLLECTIVE_DIRECT_DEBIT,
                Order.SEPA_B2B_DIRECT_DEBIT,
                Order.SEPA_B2B_COLLECTIVE_DIRECT_DEBIT
            );

        account.clearingAccounts().forEach(clearingAccount -> {
            assertThat(clearingAccount.id()).isEqualTo("Clearing account ID");
            assertThat(clearingAccount.name()).isEqualTo("Clearing account name");
        });

        account.interfaces().forEach(ai -> {
            assertThat(ai.bankingInterface()).isEqualTo(BankingInterface.FINTS_SERVER);
            assertThat(ai.capabilities())
                .containsExactlyInAnyOrder(
                    Capability.DATA_DOWNLOAD,
                    Capability.SEPA_MONEY_TRANSFER,
                    Capability.SEPA_COLLECTIVE_MONEY_TRANSFER,
                    Capability.SEPA_BASIC_DIRECT_DEBIT,
                    Capability.SEPA_BASIC_COLLECTIVE_DIRECT_DEBIT,
                    Capability.SEPA_B2B_DIRECT_DEBIT,
                    Capability.SEPA_B2B_COLLECTIVE_DIRECT_DEBIT,
                    Capability.IBAN_ONLY_SEPA_MONEY_TRANSFER,
                    Capability.IBAN_ONLY_SEPA_DIRECT_DEBIT
                );
            assertThat(ai.status()).isEqualTo(Status.UPDATED);
            assertThat(ai.lastSuccessfulUpdate())
                .isEqualTo(Optional.of(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get()));
            assertThat(
                ai.lastUpdateAttempt()).isEqualTo(
                Optional.of(new OffsetDateTimeOf("2018-01-02 00:00:00.000").get()));
        });
    }
}
