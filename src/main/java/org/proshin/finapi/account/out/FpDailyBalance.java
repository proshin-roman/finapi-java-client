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
package org.proshin.finapi.account.out;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public final class FpDailyBalance implements DailyBalance {

    private final JSONObject origin;

    public FpDailyBalance(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public OffsetDateTime date() {
        return new OffsetDateTimeOf(this.origin.getString("date")).get();
    }

    @Override
    public BigDecimal balance() {
        return this.origin.getBigDecimal("balance");
    }

    @Override
    public BigDecimal income() {
        return this.origin.getBigDecimal("income");
    }

    @Override
    public BigDecimal spending() {
        return this.origin.getBigDecimal("spending");
    }

    @Override
    public Iterable<Long> transactions() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("transactions"),
            JSONArray::getLong
        );
    }
}
