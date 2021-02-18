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
package org.proshin.finapi.account.in;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.StringOf;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.PlainNameValuePair;

public final class DailyBalancesCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public DailyBalancesCriteria() {
        this(new ArrayList<>());
    }

    public DailyBalancesCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public DailyBalancesCriteria withAccounts(final Iterable<Long> accounts) {
        this.pairs.add(new CommaSeparatedPair<>("accountIds", accounts));
        return this;
    }

    public DailyBalancesCriteria withStartDate(final OffsetDateTime startDate) {
        this.pairs.add(new PlainNameValuePair("startDate", new StringOf(startDate)));
        return this;
    }

    public DailyBalancesCriteria withEndDate(final OffsetDateTime endDate) {
        this.pairs.add(new PlainNameValuePair("endDate", new StringOf(endDate)));
        return this;
    }

    public DailyBalancesCriteria withoutProjection() {
        this.pairs.add(new PlainNameValuePair("withProjection", false));
        return this;
    }

    public DailyBalancesCriteria withPage(final int page, final int perPage) {
        this.pairs.add(new PlainNameValuePair("page", page));
        this.pairs.add(new PlainNameValuePair("perPage", perPage));
        return this;
    }

    public DailyBalancesCriteria orderBy(final String... orders) {
        for (final String order : orders) {
            this.pairs.add(new PlainNameValuePair("order", order));
        }
        return this;
    }

    @Nonnull
    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
