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
import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.json.JSONObject;
import org.junit.Test;
import org.proshin.finapi.primitives.LocalDateOf;

public class FpUserTest {
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
        assertThat(user.id(), is("1"));
        assertThat(user.registrationDate(), is(new LocalDateOf("2018-01-01").get()));
        assertThat(user.deletionDate(), is(Optional.of(new LocalDateOf("2018-01-31").get())));
        assertThat(user.lastActiveDate(), is(Optional.of(new LocalDateOf("2018-02-01").get())));
        assertThat(user.bankConnectionCount(), is(5));
        assertThat(
            user.latestBankConnectionImportDate(),
            is(Optional.of(new LocalDateOf("2018-01-02").get()))
        );
        assertThat(
            user.latestBankConnectionDeletionDate(),
            is(Optional.of(new LocalDateOf("2018-02-02").get()))
        );
        assertThat(
            user.monthlyUserStats(),
            CoreMatchers.hasItem(
                new MonthlyUserStatsMatcher(
                    YearMonth.of(2018, 1),
                    1,
                    5
                )
            )
        );
        assertThat(user.isLocked(), is(true));
    }

    private static final class MonthlyUserStatsMatcher extends BaseMatcher<MonthlyUserStats> {

        private final YearMonth month;
        private final int minBankConnectionCount;
        private final int maxBankConnectionCount;

        private MonthlyUserStatsMatcher(final YearMonth month, final int minBankConnectionCount,
            final int maxBankConnectionCount) {
            this.month = month;
            this.minBankConnectionCount = minBankConnectionCount;
            this.maxBankConnectionCount = maxBankConnectionCount;
        }

        @Override
        public boolean matches(final Object item) {
            final MonthlyUserStats stats = (MonthlyUserStats) item;
            return stats.month().compareTo(this.month) == 0
                && stats.minBankConnectionCount() == this.minBankConnectionCount
                && stats.maxBankConnectionCount() == this.maxBankConnectionCount;
        }

        @Override
        public void describeTo(final Description description) {

        }
    }
}
