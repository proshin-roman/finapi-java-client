/*
 * Copyright 2018 Roman Proshin
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
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;

public final class FpMonthlyUserStats implements MonthlyUserStats {

    private final JSONObject origin;

    public FpMonthlyUserStats(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public YearMonth month() {
        return YearMonth.parse(
            this.origin.getString("month"),
            DateTimeFormatter.ofPattern("yyyy-MM")
        );
    }

    @Override
    public int minBankConnectionCount() {
        return this.origin.getInt("minBankConnectionCount");
    }

    @Override
    public int maxBankConnectionCount() {
        return this.origin.getInt("maxBankConnectionCount");
    }
}
