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
package org.proshin.finapi.tppcredential.in;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class QueryTppAuthenticationGroupsCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public QueryTppAuthenticationGroupsCriteria() {
        this(new ArrayList<>());
    }

    public QueryTppAuthenticationGroupsCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public QueryTppAuthenticationGroupsCriteria withName(final String name) {
        this.pairs.add(new UrlEncodedPair("search", name));
        return this;
    }

    public QueryTppAuthenticationGroupsCriteria withBankBlz(final String bankBlz) {
        this.pairs.add(new UrlEncodedPair("search", bankBlz));
        return this;
    }

    public QueryTppAuthenticationGroupsCriteria withBankName(final String bankName) {
        this.pairs.add(new UrlEncodedPair("search", bankName));
        return this;
    }

    public QueryTppAuthenticationGroupsCriteria withPage(final int page, final int perPage) {
        this.pairs.add(new UrlEncodedPair("page", page));
        this.pairs.add(new UrlEncodedPair("perPage", perPage));
        return this;
    }

    @Nonnull
    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
