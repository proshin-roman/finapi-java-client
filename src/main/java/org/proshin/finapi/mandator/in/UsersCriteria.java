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
package org.proshin.finapi.mandator.in;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.StringOf;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class UsersCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public UsersCriteria() {
        this(new ArrayList<>());
    }

    public UsersCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public UsersCriteria withMinRegistrationDate(final OffsetDateTime minRegistrationDate) {
        this.pairs.add(new UrlEncodedPair("minRegistrationDate", new StringOf(minRegistrationDate)));
        return this;
    }

    public UsersCriteria withMaxRegistrationDate(final OffsetDateTime maxRegistrationDate) {
        this.pairs.add(new UrlEncodedPair("maxRegistrationDate", new StringOf(maxRegistrationDate)));
        return this;
    }

    public UsersCriteria withMinDeletionDate(final OffsetDateTime minDeletionDate) {
        this.pairs.add(new UrlEncodedPair("minDeletionDate", new StringOf(minDeletionDate)));
        return this;
    }

    public UsersCriteria withMaxDeletionDate(final OffsetDateTime maxDeletionDate) {
        this.pairs.add(new UrlEncodedPair("maxDeletionDate", new StringOf(maxDeletionDate)));
        return this;
    }

    public UsersCriteria withMinLastActiveDate(final OffsetDateTime minLastActiveDate) {
        this.pairs.add(new UrlEncodedPair("minLastActiveDate", new StringOf(minLastActiveDate)));
        return this;
    }

    public UsersCriteria withMaxLastActiveDate(final OffsetDateTime maxLastActiveDate) {
        this.pairs.add(new UrlEncodedPair("maxLastActiveDate", new StringOf(maxLastActiveDate)));
        return this;
    }

    public UsersCriteria withMonthlyStats() {
        this.pairs.add(new UrlEncodedPair("includeMonthlyStats", true));
        return this;
    }

    public UsersCriteria withMonthlyStatsStartDate(final OffsetDateTime monthlyStatsStartDate) {
        this.pairs.add(new UrlEncodedPair("monthlyStatsStartDate", new StringOf(monthlyStatsStartDate)));
        return this;
    }

    public UsersCriteria withMonthlyStatsEndDate(final OffsetDateTime monthlyStatsEndDate) {
        this.pairs.add(new UrlEncodedPair("monthlyStatsEndDate", new StringOf(monthlyStatsEndDate)));
        return this;
    }

    public UsersCriteria withMinBankConnectionCountInMonthlyStats(
        final int minBankConnectionCountInMonthlyStats) {
        this.pairs.add(
            new UrlEncodedPair(
                "minBankConnectionCountInMonthlyStats",
                minBankConnectionCountInMonthlyStats
            )
        );
        return this;
    }

    public UsersCriteria withUserId(final String userId) {
        this.pairs.add(new UrlEncodedPair("userId", userId));
        return this;
    }

    public UsersCriteria withIsDeleted(final boolean isDeleted) {
        this.pairs.add(new UrlEncodedPair("isDeleted", isDeleted));
        return this;
    }

    public UsersCriteria withIsLocked(final boolean isLocked) {
        this.pairs.add(new UrlEncodedPair("isLocked", isLocked));
        return this;
    }

    public UsersCriteria withPage(final int page, final int perPage) {
        this.pairs.add(new UrlEncodedPair("page", page));
        this.pairs.add(new UrlEncodedPair("perPage", perPage));
        return this;
    }

    public UsersCriteria orderBy(final String... orders) {
        for (final String order : orders) {
            this.pairs.add(new UrlEncodedPair("order", order));
        }
        return this;
    }

    @Nonnull
    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
