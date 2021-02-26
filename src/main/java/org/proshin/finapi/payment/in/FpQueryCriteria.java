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
package org.proshin.finapi.payment.in;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.StringOf;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.PlainNameValuePair;

public final class FpQueryCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public FpQueryCriteria() {
        this(new ArrayList<>());
    }

    public FpQueryCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public FpQueryCriteria withIds(final Iterable<Long> ids) {
        this.pairs.add(new PlainNameValuePair(new CommaSeparatedPair<>("ids", ids)));
        return this;
    }

    public FpQueryCriteria withAccountIds(final Iterable<Long> accountIds) {
        this.pairs.add(new PlainNameValuePair(new CommaSeparatedPair<>("accountIds", accountIds)));
        return this;
    }

    public FpQueryCriteria withMinAmount(final BigDecimal minAmount) {
        this.pairs.add(new PlainNameValuePair("minAmount", new StringOf(minAmount)));
        return this;
    }

    public FpQueryCriteria withMaxAmount(final BigDecimal maxAmount) {
        this.pairs.add(new PlainNameValuePair("maxAmount", new StringOf(maxAmount)));
        return this;
    }

    public FpQueryCriteria withPage(final int page, final int perPage) {
        this.pairs.add(new PlainNameValuePair("page", page));
        this.pairs.add(new PlainNameValuePair("perPage", perPage));
        return this;
    }

    public FpQueryCriteria orderBy(final String... orders) {
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
