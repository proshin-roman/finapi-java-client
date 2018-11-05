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
package org.proshin.finapi.primitives.paging;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Supplier;
import org.apache.http.NameValuePair;
import org.cactoos.list.ListOf;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class PagingCriteria implements Iterable<NameValuePair> {

    private final Supplier<Iterator<NameValuePair>> origin;

    public PagingCriteria(final int page, final int perPage, final String... orders) {
        this(() -> {
                final Collection<NameValuePair> list = new ListOf<>();
                list.add(new UrlEncodedPair("page", page));
                list.add(new UrlEncodedPair("perPage", perPage));
                for (final String order : orders) {
                    list.add(new UrlEncodedPair("order", order));
                }
                return list.iterator();
            }
        );
    }

    public PagingCriteria(final Supplier<Iterator<NameValuePair>> origin) {
        this.origin = origin;
    }

    @Override
    public Iterator<NameValuePair> iterator() {
        return this.origin.get();
    }
}
