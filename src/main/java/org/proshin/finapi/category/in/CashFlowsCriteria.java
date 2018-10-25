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
package org.proshin.finapi.category.in;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.Direction;
import org.proshin.finapi.primitives.StringOf;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class CashFlowsCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public CashFlowsCriteria() {
        this(new ArrayList<>());
    }

    public CashFlowsCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public CashFlowsCriteria withSearch(final String search) {
        this.pairs.add(new UrlEncodedPair("search", search));
        return this;
    }

    public CashFlowsCriteria withCounterpart(final String counterpart) {
        this.pairs.add(new UrlEncodedPair("counterpart", counterpart));
        return this;
    }

    public CashFlowsCriteria withPurpose(final String purpose) {
        this.pairs.add(new UrlEncodedPair("purpose", purpose));
        return this;
    }

    public CashFlowsCriteria withAccounts(final Iterable<Long> accounts) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("accountIds", accounts)));
        return this;
    }

    public CashFlowsCriteria withMinBankBookingDate(final OffsetDateTime minBankBookingDate) {
        this.pairs.add(new UrlEncodedPair("minBankBookingDate", new StringOf(minBankBookingDate)));
        return this;
    }

    public CashFlowsCriteria withMaxBankBookingDate(final OffsetDateTime maxBankBookingDate) {
        this.pairs.add(new UrlEncodedPair("maxBankBookingDate", new StringOf(maxBankBookingDate)));
        return this;
    }

    public CashFlowsCriteria withMinFinapiBookingDate(final OffsetDateTime minFinapiBookingDate) {
        this.pairs.add(new UrlEncodedPair("minFinapiBookingDate", new StringOf(minFinapiBookingDate)));
        return this;
    }

    public CashFlowsCriteria withMaxFinapiBookingDate(final OffsetDateTime maxFinapiBookingDate) {
        this.pairs.add(new UrlEncodedPair("maxFinapiBookingDate", new StringOf(maxFinapiBookingDate)));
        return this;
    }

    public CashFlowsCriteria withMinAmount(final BigDecimal minAmount) {
        this.pairs.add(new UrlEncodedPair("minAmount", new StringOf(minAmount)));
        return this;
    }

    public CashFlowsCriteria withMaxAmount(final BigDecimal maxAmount) {
        this.pairs.add(new UrlEncodedPair("maxAmount", new StringOf(maxAmount)));
        return this;
    }

    public CashFlowsCriteria withDirection(final Direction direction) {
        this.pairs.add(new UrlEncodedPair("direction", direction.label()));
        return this;
    }

    public CashFlowsCriteria withLabels(final Iterable<Long> labels) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("labelIds", labels)));
        return this;
    }

    public CashFlowsCriteria withCategories(final Iterable<Long> categories) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("categoryIds", categories)));
        return this;
    }

    public CashFlowsCriteria withIsNew(final boolean isNew) {
        this.pairs.add(new UrlEncodedPair("isNew", isNew));
        return this;
    }

    public CashFlowsCriteria withMinImportDate(final OffsetDateTime minImportDate) {
        this.pairs.add(new UrlEncodedPair("minImportDate", new StringOf(minImportDate)));
        return this;
    }

    public CashFlowsCriteria withMaxImportDate(final OffsetDateTime maxImportDate) {
        this.pairs.add(new UrlEncodedPair("maxImportDate", new StringOf(maxImportDate)));
        return this;
    }

    public CashFlowsCriteria withoutSubCashFlows() {
        this.pairs.add(new UrlEncodedPair("includeSubCashFlows", false));
        return this;
    }

    public CashFlowsCriteria withOrdering(final String... orders) {
        for (String order : orders) {
            this.pairs.add(new UrlEncodedPair("order", order));
        }
        return this;
    }

    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
