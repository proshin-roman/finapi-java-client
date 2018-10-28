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

import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.optional.OptionalOffsetDateTimeOf;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.primitives.paging.Paging;

public final class FpDailyBalances implements DailyBalances {

    private final JSONObject origin;
    private final Page<DailyBalance> page;

    public FpDailyBalances(final JSONObject origin) {
        this.origin = origin;
        this.page = new FpPage<>(
            "dailyBalances",
            this.origin,
            (array, index) -> new FpDailyBalance(array.getJSONObject(index))
        );
    }

    @Override
    public Optional<OffsetDateTime> latestCommonBalanceTimestamp() {
        return new OptionalOffsetDateTimeOf(this.origin, "latestCommonBalanceTimestamp").get();
    }

    @Override
    public Iterable<DailyBalance> items() {
        return this.page.items();
    }

    @Override
    public Paging paging() {
        return this.page.paging();
    }
}
