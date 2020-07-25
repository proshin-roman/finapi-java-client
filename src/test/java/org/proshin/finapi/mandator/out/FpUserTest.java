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
package org.proshin.finapi.mandator.out;

import java.time.YearMonth;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.proshin.finapi.primitives.LocalDateOf;

public final class FpUserTest {

    @Test
    public void test() {
        final User user = new FpUser(new JSONObject('{' +
            "      \"userId\": \"1\"," +
            "      \"registrationDate\": \"2018-01-01\"," +
            "      \"deletionDate\": \"2018-01-31\"," +
            "      \"lastActiveDate\": \"2018-02-01\"," +
            "      \"bankConnectionCount\": 5," +
            "      \"latestBankConnectionImportDate\": \"2018-01-02\"," +
            "      \"latestBankConnectionDeletionDate\": \"2018-02-02\"," +
            "      \"monthlyStats\": [" +
            "        {" +
            "          \"month\": \"2018-01\"," +
            "          \"minBankConnectionCount\": 1," +
            "          \"maxBankConnectionCount\": 5" +
            "        }" +
            "      ]," +
            "      \"isLocked\": true" +
            "    }"));
        assertThat(user.id()).isEqualTo("1");
        assertThat(user.registrationDate()).isEqualTo(new LocalDateOf("2018-01-01").get());
        assertThat(user.deletionDate()).isEqualTo(Optional.of(new LocalDateOf("2018-01-31").get()));
        assertThat(user.lastActiveDate()).isEqualTo(Optional.of(new LocalDateOf("2018-02-01").get()));
        assertThat(user.bankConnectionCount()).isEqualTo(5);
        assertThat(user.latestBankConnectionImportDate())
            .isEqualTo(Optional.of(new LocalDateOf("2018-01-02").get()));
        assertThat(user.latestBankConnectionDeletionDate())
            .isEqualTo(Optional.of(new LocalDateOf("2018-02-02").get()));
        user.monthlyUserStats().forEach(mus -> {
            assertThat(mus.month()).isEqualByComparingTo(YearMonth.of(2018, 1));
            assertThat(mus.minBankConnectionCount()).isEqualTo(1);
            assertThat(mus.maxBankConnectionCount()).isEqualTo(5);
        });
        assertThat(user.isLocked()).isTrue();
    }
}
