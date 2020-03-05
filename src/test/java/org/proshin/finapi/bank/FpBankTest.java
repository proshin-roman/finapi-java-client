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
package org.proshin.finapi.bank;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.proshin.finapi.bank.Bank.DataSource.FINTS_SERVER;
import static org.proshin.finapi.bank.Bank.DataSource.WEB_SCRAPER;
import org.proshin.finapi.bank.out.BankInterface;
import static org.proshin.finapi.bank.out.BankInterface.BankInterfaceProperty.REDIRECT_APPROACH;
import org.proshin.finapi.bank.out.LoginCredential;
import org.proshin.finapi.primitives.BankingInterface;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.tppcredential.TppAuthenticationGroup;

public class FpBankTest {

    @Test
    public void test() {
        final FpBank bank = new FpBank(
            new JSONObject('{' +
                "  \"id\": 277672," +
                "  \"name\": \"FinAPI Test Bank\"," +
                "  \"loginHint\": \"Bitte geben Sie Ihre Online-Identifikation und Ihre PIN ein.\"," +
                "  \"bic\": \"TESTBANKING\"," +
                "  \"blzs\": []," +
                "  \"blz\": \"00000000\"," +
                "  \"location\": \"DE\"," +
                "  \"city\": \"München\"," +
                "  \"isSupported\": true," +
                "  \"isTestBank\": true," +
                "  \"popularity\": 95," +
                "  \"health\": 100," +
                "  \"loginFieldUserId\": \"Onlinebanking-ID\"," +
                "  \"loginFieldCustomerId\": \"Kunden-ID\"," +
                "  \"loginFieldPin\": \"PIN\"," +
                "  \"pinsAreVolatile\": true," +
                "  \"isCustomerIdPassword\": true," +
                "  \"supportedDataSources\": [" +
                "    \"FINTS_SERVER\"," +
                "    \"WEB_SCRAPER\"" +
                "  ]," +
                "  \"interfaces\": [" +
                "    {" +
                "      \"interface\": \"FINTS_SERVER\"," +
                "      \"tppAuthenticationGroup\": {" +
                "        \"id\": 1," +
                "        \"name\": \"AirBank XS2A CZ\"" +
                "      }," +
                "      \"loginCredentials\": [" +
                "        {" +
                "          \"label\": \"Nutzerkennung\"," +
                "          \"isSecret\": true," +
                "          \"isVolatile\": true" +
                "        }" +
                "      ]," +
                "      \"properties\": [" +
                "        \"REDIRECT_APPROACH\"" +
                "      ]," +
                "      \"loginHint\": \"Bitte geben Sie nur die ersten fünf Stellen Ihrer PIN ein.\"" +
                "    }" +
                "  ]," +
                "  \"lastCommunicationAttempt\": \"2018-01-01 00:00:00.000\"," +
                "  \"lastSuccessfulCommunication\": \"2018-01-02 00:00:00.000\"" +
                '}')
        );

        assertThat(bank.id()).isEqualTo(277672L);
        assertThat(bank.name()).isEqualTo("FinAPI Test Bank");
        assertThat(bank.loginHint()).isEqualTo(Optional.of(
            "Bitte geben Sie Ihre Online-Identifikation und Ihre PIN ein."));
        assertThat(bank.bic()).isEqualTo(Optional.of("TESTBANKING"));
        assertThat(bank.blz()).isEqualTo("00000000");
        assertThat(bank.loginFields().userId()).isEqualTo(Optional.of("Onlinebanking-ID"));
        assertThat(bank.loginFields().customerId()).isEqualTo(Optional.of("Kunden-ID"));
        assertThat(bank.loginFields().pin()).isEqualTo(Optional.of("PIN"));
        assertThat(bank.isCustomerIdPassword()).isTrue();
        assertThat(bank.isSupported()).isTrue();
        assertThat(bank.supportedDataSources()).containsExactlyInAnyOrder(FINTS_SERVER, WEB_SCRAPER);
        assertThat(bank.pinsAreVolatile()).isTrue();
        assertThat(bank.location()).isEqualTo(Optional.of("DE"));
        assertThat(bank.city()).isEqualTo(Optional.of("München"));
        assertThat(bank.isTestBank()).isTrue();
        assertThat(bank.popularity()).isEqualTo(95);
        assertThat(bank.health()).isEqualTo(100);
        assertThat(bank.lastCommunicationAttempt())
            .isEqualTo(Optional.of(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get()));
        assertThat(bank.lastSuccessfulCommunication())
            .isEqualTo(Optional.of(new OffsetDateTimeOf("2018-01-02 00:00:00.000").get()));

        for (final BankInterface bankInterface : bank.interfaces()) {
            assertThat(bankInterface.bankingInterface()).isEqualTo(BankingInterface.FINTS_SERVER);

            assertThat(bankInterface.tppAuthenticationGroup().isPresent()).isTrue();
            final TppAuthenticationGroup authenticationGroup = bankInterface.tppAuthenticationGroup().get();
            assertThat(authenticationGroup.id()).isEqualTo(1L);
            assertThat(authenticationGroup.name()).isEqualTo("AirBank XS2A CZ");

            final LoginCredential loginCredential = bankInterface.loginCredentials().iterator().next();
            assertThat(loginCredential.label()).isEqualTo("Nutzerkennung");
            assertThat(loginCredential.isSecret()).isTrue();
            assertThat(loginCredential.isVolatile()).isTrue();

            assertThat(bankInterface.properties()).containsExactlyInAnyOrder(REDIRECT_APPROACH);

            assertThat(bankInterface.loginHint())
                .isEqualTo(Optional.of("Bitte geben Sie nur die ersten fünf Stellen Ihrer PIN ein."));
        }
    }
}
