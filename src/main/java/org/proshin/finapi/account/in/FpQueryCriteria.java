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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.http.NameValuePair;
import org.proshin.finapi.account.Type;
import org.proshin.finapi.primitives.StringOf;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class FpQueryCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public FpQueryCriteria() {
        this(new ArrayList<>());
    }

    public FpQueryCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public FpQueryCriteria withIds(final Iterable<Long> ids) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("ids", ids)));
        return this;
    }

    public FpQueryCriteria withSearch(final String search) {
        this.pairs.add(new UrlEncodedPair("search", search));
        return this;
    }

    public FpQueryCriteria withTypes(final Type... types) {
        this.pairs.add(
            new UrlEncodedPair(
                new CommaSeparatedPair<>(
                    "accountTypeIds",
                    Arrays.stream(types)
                        .map(Type::asCode)
                        .collect(Collectors.toList())
                )
            )
        );
        return this;
    }

    public FpQueryCriteria withBankConnections(final Iterable<Long> ids) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("bankConnectionIds", ids)));
        return this;
    }

    public FpQueryCriteria withMinLastSuccessfulUpdate(final OffsetDateTime minLastSuccessfulUpdate) {
        this.pairs.add(
            new UrlEncodedPair(
                "minLastSuccessfulUpdate",
                new StringOf(minLastSuccessfulUpdate)
            )
        );
        return this;
    }

    public FpQueryCriteria withMaxLastSuccessfulUpdate(final OffsetDateTime maxLastSuccessfulUpdate) {
        this.pairs.add(
            new UrlEncodedPair(
                "maxLastSuccessfulUpdate",
                new StringOf(maxLastSuccessfulUpdate)
            )
        );
        return this;
    }

    public FpQueryCriteria withMinBalance(final BigDecimal minBalance) {
        this.pairs.add(new UrlEncodedPair("minBalance", new StringOf(minBalance)));
        return this;
    }

    public FpQueryCriteria withMaxBalance(final BigDecimal maxBalance) {
        this.pairs.add(new UrlEncodedPair("maxBalance", new StringOf(maxBalance)));
        return this;
    }

    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
