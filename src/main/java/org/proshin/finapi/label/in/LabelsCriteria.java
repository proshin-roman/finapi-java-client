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
package org.proshin.finapi.label.in;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class LabelsCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public LabelsCriteria() {
        this(new ArrayList<>());
    }

    public LabelsCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public LabelsCriteria withIds(final Iterable<Long> ids) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("ids", ids)));
        return this;
    }

    public LabelsCriteria withSearch(final String search) {
        this.pairs.add(new UrlEncodedPair("search", search));
        return this;
    }

    public LabelsCriteria withPage(final int page, final int perPage) {
        this.pairs.add(new UrlEncodedPair("page", page));
        this.pairs.add(new UrlEncodedPair("perPage", perPage));
        return this;
    }

    public LabelsCriteria orderBy(final String... orders) {
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
