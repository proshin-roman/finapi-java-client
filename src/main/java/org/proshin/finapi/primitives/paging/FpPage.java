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

import java.util.function.BiFunction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.primitives.IterableJsonArray;

/**
 * Page of resources with paging info.
 * @param <T> Type of items.
 * @todo #38 Write a test for FpPage class.
 */
public final class FpPage<T> implements Page<T> {

    private final String itemsField;
    private final JSONObject origin;
    private final BiFunction<JSONArray, Integer, T> func;

    public FpPage(final String itemsField, final JSONObject origin, final BiFunction<JSONArray, Integer, T> func) {
        this.itemsField = itemsField;
        this.origin = origin;
        this.func = func;
    }

    @Override
    public Iterable<T> items() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray(this.itemsField),
            func
        );
    }

    @Override
    public Paging paging() {
        return new FpPaging(this.origin.getJSONObject("paging"));
    }
}
