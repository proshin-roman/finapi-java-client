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
package org.proshin.finapi.transaction.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.Direction;
import org.proshin.finapi.primitives.StringOf;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.QueryParamEncodedPair;

@SuppressWarnings("ClassWithTooManyMethods")
public final class TransactionsCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public TransactionsCriteria() {
        this(new ArrayList<>());
    }

    public TransactionsCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public TransactionsCriteria withIds(final Iterable<Long> ids) {
        this.pairs.add(new QueryParamEncodedPair(new CommaSeparatedPair<>("ids", ids)));
        return this;
    }

    public TransactionsCriteria withView(final View view) {
        this.pairs.add(new QueryParamEncodedPair("view", view.label()));
        return this;
    }

    public TransactionsCriteria withSearch(final String search) {
        this.pairs.add(new QueryParamEncodedPair("search", search));
        return this;
    }

    public TransactionsCriteria withCounterpart(final String counterpart) {
        this.pairs.add(new QueryParamEncodedPair("counterpart", counterpart));
        return this;
    }

    public TransactionsCriteria withPurpose(final String purpose) {
        this.pairs.add(new QueryParamEncodedPair("purpose", purpose));
        return this;
    }

    public TransactionsCriteria withAccounts(final Iterable<Long> accounts) {
        this.pairs.add(new QueryParamEncodedPair(new CommaSeparatedPair<>("accountIds", accounts)));
        return this;
    }

    public TransactionsCriteria withMinBankBookingDate(final LocalDate minBankBookingDate) {
        this.pairs.add(new QueryParamEncodedPair("minBankBookingDate", new StringOf(minBankBookingDate)));
        return this;
    }

    public TransactionsCriteria withMaxBankBookingDate(final LocalDate maxBankBookingDate) {
        this.pairs.add(new QueryParamEncodedPair("maxBankBookingDate", new StringOf(maxBankBookingDate)));
        return this;
    }

    public TransactionsCriteria withMinFinapiBookingDate(final LocalDate minFinapiBookingDate) {
        this.pairs.add(new QueryParamEncodedPair("minFinapiBookingDate", new StringOf(minFinapiBookingDate)));
        return this;
    }

    public TransactionsCriteria withMaxFinapiBookingDate(final LocalDate maxFinapiBookingDate) {
        this.pairs.add(new QueryParamEncodedPair("maxFinapiBookingDate", new StringOf(maxFinapiBookingDate)));
        return this;
    }

    public TransactionsCriteria withMinAmount(final BigDecimal minAmount) {
        this.pairs.add(new QueryParamEncodedPair("minAmount", new StringOf(minAmount)));
        return this;
    }

    public TransactionsCriteria withMaxAmount(final BigDecimal maxAmount) {
        this.pairs.add(new QueryParamEncodedPair("maxAmount", new StringOf(maxAmount)));
        return this;
    }

    public TransactionsCriteria withDirection(final Direction direction) {
        this.pairs.add(new QueryParamEncodedPair("direction", direction.lowerCase()));
        return this;
    }

    public TransactionsCriteria withLabels(final Iterable<Long> labels) {
        this.pairs.add(new QueryParamEncodedPair(new CommaSeparatedPair<>("labelIds", labels)));
        return this;
    }

    public TransactionsCriteria withCategories(final Iterable<Long> categories) {
        this.pairs.add(new QueryParamEncodedPair(new CommaSeparatedPair<>("categoryIds", categories)));
        return this;
    }

    public TransactionsCriteria withoutIncludingChildCategories() {
        this.pairs.add(new QueryParamEncodedPair("includeChildCategories", false));
        return this;
    }

    public TransactionsCriteria withIsNew(final boolean isNew) {
        this.pairs.add(new QueryParamEncodedPair("isNew", isNew));
        return this;
    }

    public TransactionsCriteria withIsPotentialDuplicate(final boolean isPotentialDuplicate) {
        this.pairs.add(new QueryParamEncodedPair("isPotentialDuplicate", isPotentialDuplicate));
        return this;
    }

    public TransactionsCriteria withIsAdjustingEntry(final boolean isAdjustingEntry) {
        this.pairs.add(new QueryParamEncodedPair("isAdjustingEntry", isAdjustingEntry));
        return this;
    }

    public TransactionsCriteria withMinImportDate(final LocalDate minImportDate) {
        this.pairs.add(new QueryParamEncodedPair("minImportDate", new StringOf(minImportDate)));
        return this;
    }

    public TransactionsCriteria withMaxImportDate(final LocalDate maxImportDate) {
        this.pairs.add(new QueryParamEncodedPair("maxImportDate", new StringOf(maxImportDate)));
        return this;
    }

    public TransactionsCriteria withPage(final int page, final int perPage) {
        this.pairs.add(new QueryParamEncodedPair("page", page));
        this.pairs.add(new QueryParamEncodedPair("perPage", perPage));
        return this;
    }

    public TransactionsCriteria orderBy(final String... orders) {
        for (final String order : orders) {
            this.pairs.add(new QueryParamEncodedPair("order", order));
        }
        return this;
    }

    @Nonnull
    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }

    public enum View {

        BANK("bankView"), USER("userView");

        private final String label;

        View(final String label) {
            this.label = label;
        }

        public final String label() {
            return this.label;
        }
    }
}
