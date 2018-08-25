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
package org.proshin.finapi.bank.in;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.proshin.finapi.bank.Bank;

public final class FpQueryCriteria implements QueryCriteria {

    private final List<NameValuePair> pairs;

    public FpQueryCriteria() {
        this(new ArrayList<>());
    }

    public FpQueryCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    @Override
    public QueryCriteria withIds(final Iterable<Long> ids) {
        this.pairs.add(
            new BasicNameValuePair("ids",
                StreamSupport.stream(ids.spliterator(), false)
                    .map(Object::toString)
                    .collect(Collectors.joining(","))
            )
        );
        return this;
    }

    @Override
    public QueryCriteria withSearch(final String search) {
        this.pairs.add(new BasicNameValuePair("search", search));
        return this;
    }

    @Override
    public QueryCriteria withSupporting(final boolean supporting) {
        this.pairs.add(new BasicNameValuePair("isSupported", String.valueOf(supporting)));
        return this;
    }

    @Override
    public QueryCriteria withPinsAreVolatile(final boolean pinsAreVolatile) {
        this.pairs.add(new BasicNameValuePair("pinsAreVolatile", String.valueOf(pinsAreVolatile)));
        return this;
    }

    @Override
    public QueryCriteria withSupportedDataSources(final Iterable<Bank.DataSource> supportedDataSources) {
        this.pairs.add(
            new BasicNameValuePair("ids",
                StreamSupport.stream(supportedDataSources.spliterator(), false)
                    .map(Bank.DataSource::name)
                    .collect(Collectors.joining(","))
            )
        );
        return this;
    }

    @Override
    public QueryCriteria withLocation(final Iterable<String> location) {
        this.pairs.add(
            new BasicNameValuePair("ids",
                StreamSupport.stream(location.spliterator(), false)
                    .collect(Collectors.joining(","))
            )
        );
        return this;
    }

    @Override
    public QueryCriteria withTestBank(final boolean testBank) {
        this.pairs.add(new BasicNameValuePair("isTestBank", String.valueOf(testBank)));
        return this;
    }

    @Override
    public List<NameValuePair> asPairs() {
        return this.pairs;
    }
}
