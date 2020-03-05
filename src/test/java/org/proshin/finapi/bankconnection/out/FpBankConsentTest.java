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
package org.proshin.finapi.bankconnection.out;

import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.proshin.finapi.bankconnection.out.BankConsent.Status.PRESENT;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public class FpBankConsentTest {

    @Test
    public void test() {
        final BankConsent consent = new FpBankConsent(
            new JSONObject(
                '{' +
                    "  \"status\": \"PRESENT\"," +
                    "  \"expiresAt\": \"2019-07-20 09:05:10.546\"" +
                    '}'
            )
        );
        assertThat(consent.status()).isEqualTo(PRESENT);
        assertThat(consent.expiresAt()).isEqualTo(new OffsetDateTimeOf("2019-07-20 09:05:10.546").get());
    }
}
