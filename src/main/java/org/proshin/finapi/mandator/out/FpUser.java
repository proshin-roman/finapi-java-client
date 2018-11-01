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

import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.primitives.optional.OptionalOffsetDateTimeOf;

public final class FpUser implements User {

    private final JSONObject origin;
    private final String pattern;

    public FpUser(final JSONObject origin) {
        this.origin = origin;
        this.pattern = "YYYY-MM-DD";
    }

    @Override
    public String id() {
        return this.origin.getString("id");
    }

    @Override
    public OffsetDateTime registrationDate() {
        return new OffsetDateTimeOf(this.origin.getString("registrationDate"), this.pattern).get();
    }

    @Override
    public Optional<OffsetDateTime> deletionDate() {
        return new OptionalOffsetDateTimeOf(this.origin, "deletionDate", this.pattern).get();
    }

    @Override
    public Optional<OffsetDateTime> lastActiveDate() {
        return new OptionalOffsetDateTimeOf(this.origin, "lastActiveDate", this.pattern).get();
    }

    @Override
    public int bankConnectionCount() {
        return this.origin.getInt("bankConnectionCount");
    }

    @Override
    public Optional<OffsetDateTime> latestBankConnectionImportDate() {
        return new OptionalOffsetDateTimeOf(this.origin, "latestBankConnectionImportDate", this.pattern).get();
    }

    @Override
    public Optional<OffsetDateTime> latestBankConnectionDeletionDate() {
        return new OptionalOffsetDateTimeOf(this.origin, "latestBankConnectionDeletionDate", this.pattern).get();
    }

    @Override
    public Iterable<MonthlyUserStats> monthlyUserStats() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("monthlyStats"),
            (array, index) -> new FpMonthlyUserStats(array.getJSONObject(index))
        );
    }
}
