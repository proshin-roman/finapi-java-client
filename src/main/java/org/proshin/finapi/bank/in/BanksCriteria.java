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
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import org.proshin.finapi.bank.Bank;
import org.proshin.finapi.primitives.BankingInterface;
import org.proshin.finapi.primitives.paging.PagingCriteria;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class BanksCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public BanksCriteria() {
        this(new ArrayList<>());
    }

    public BanksCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public BanksCriteria withIds(final Iterable<Long> ids) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("ids", ids)));
        return this;
    }

    public BanksCriteria withSearch(final String search) {
        this.pairs.add(new UrlEncodedPair("search", search));
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public BanksCriteria withSupporting(final boolean supporting) {
        this.pairs.add(new UrlEncodedPair("isSupported", supporting));
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public BanksCriteria withPinsAreVolatile(final boolean pinsAreVolatile) {
        this.pairs.add(new UrlEncodedPair("pinsAreVolatile", pinsAreVolatile));
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public BanksCriteria withSupportedDataSources(final Iterable<Bank.DataSource> supportedDataSources) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("supportedDataSources", supportedDataSources)));
        return this;
    }

    public BanksCriteria withSupportedInterfaces(final Iterable<BankingInterface> supportedInterfaces) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("supportedInterfaces", supportedInterfaces)));
        return this;
    }

    public BanksCriteria withLocation(final Iterable<String> location) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("location", location)));
        return this;
    }

    public BanksCriteria withTppAuthenticationGroups(final Iterable<Long> tppAuthenticationGroups) {
        this.pairs.add(
            new UrlEncodedPair(
                new CommaSeparatedPair<>(
                    "tppAuthenticationGroupIds",
                    tppAuthenticationGroups
                )));
        return this;
    }

    public BanksCriteria withTestBank(final boolean testBank) {
        this.pairs.add(new UrlEncodedPair("isTestBank", testBank));
        return this;
    }

    public BanksCriteria withPaging(final PagingCriteria paging) {
        for (final NameValuePair parameter : paging) {
            this.pairs.add(parameter);
        }
        return this;
    }

    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
