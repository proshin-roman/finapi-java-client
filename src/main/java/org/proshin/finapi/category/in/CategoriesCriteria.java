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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class CategoriesCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public CategoriesCriteria() {
        this(new ArrayList<>());
    }

    public CategoriesCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public CategoriesCriteria withIds(final Iterable<Long> ids) {
        this.pairs.add(new CommaSeparatedPair<Long>("ids", ids));
        return this;
    }

    public CategoriesCriteria withSearch(final String search) {
        this.pairs.add(new UrlEncodedPair("search", search));
        return this;
    }

    public CategoriesCriteria withCustomOnly() {
        this.pairs.add(new UrlEncodedPair("isCustom", true));
        return this;
    }

    public CategoriesCriteria withNonCustomOnly() {
        this.pairs.add(new UrlEncodedPair("isCustom", false));
        return this;
    }

    public CategoriesCriteria withPage(final int page, final int perPage) {
        this.pairs.add(new UrlEncodedPair("page", page));
        this.pairs.add(new UrlEncodedPair("perPage", perPage));
        return this;
    }

    public CategoriesCriteria orderBy(final String... orders) {
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
