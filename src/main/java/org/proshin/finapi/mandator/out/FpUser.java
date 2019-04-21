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

import java.time.LocalDate;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.LocalDateOf;
import org.proshin.finapi.primitives.optional.OptionalLocalDateOf;

public final class FpUser implements User {

    private final JSONObject origin;

    public FpUser(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public String id() {
        return this.origin.getString("userId");
    }

    @Override
    public LocalDate registrationDate() {
        return new LocalDateOf(this.origin.getString("registrationDate")).get();
    }

    @Override
    public Optional<LocalDate> deletionDate() {
        return new OptionalLocalDateOf(this.origin, "deletionDate").get();
    }

    @Override
    public Optional<LocalDate> lastActiveDate() {
        return new OptionalLocalDateOf(this.origin, "lastActiveDate").get();
    }

    @Override
    public int bankConnectionCount() {
        return this.origin.getInt("bankConnectionCount");
    }

    @Override
    public Optional<LocalDate> latestBankConnectionImportDate() {
        return new OptionalLocalDateOf(this.origin, "latestBankConnectionImportDate").get();
    }

    @Override
    public Optional<LocalDate> latestBankConnectionDeletionDate() {
        return new OptionalLocalDateOf(this.origin, "latestBankConnectionDeletionDate").get();
    }

    @Override
    public Iterable<MonthlyUserStats> monthlyUserStats() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("monthlyStats"),
            (array, index) -> new FpMonthlyUserStats(array.getJSONObject(index))
        );
    }

    @Override
    public boolean isLocked() {
        return this.origin.getBoolean("isLocked");
    }
}
