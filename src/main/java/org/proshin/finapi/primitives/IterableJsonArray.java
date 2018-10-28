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
package org.proshin.finapi.primitives;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import org.json.JSONArray;

public final class IterableJsonArray<T> implements Iterable<T> {

    private final JSONArray array;
    private final BiFunction<JSONArray, Integer, T> func;

    public IterableJsonArray(final JSONArray array, final BiFunction<JSONArray, Integer, T> func) {
        this.array = array;
        this.func = func;
    }

    @Override
    public Iterator<T> iterator() {
        return new JsonArrayIterator<>(this.array, this.func);
    }

    private static final class JsonArrayIterator<T> implements Iterator<T> {

        private final AtomicInteger index;
        private final JSONArray array;
        private final BiFunction<? super JSONArray, ? super Integer, ? extends T> func;

        JsonArrayIterator(
            final JSONArray array,
            final BiFunction<? super JSONArray, ? super Integer, ? extends T> func
        ) {
            this(
                new AtomicInteger(0),
                array,
                func
            );
        }

        JsonArrayIterator(
            final AtomicInteger index,
            final JSONArray array,
            final BiFunction<? super JSONArray, ? super Integer, ? extends T> func
        ) {
            this.index = index;
            this.array = array;
            this.func = func;
        }

        @Override
        public boolean hasNext() {
            return this.index.get() < this.array.length();
        }

        @Override
        public T next() {
            return this.func.apply(this.array, this.index.getAndIncrement());
        }
    }
}
