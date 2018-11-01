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
package org.proshin.finapi.mandator;

import java.time.OffsetDateTime;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.category.Category;
import org.proshin.finapi.category.FpCategory;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.Direction;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public final class FpKeywordRule implements KeywordRule {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;

    public FpKeywordRule(final Endpoint endpoint, final AccessToken token, final JSONObject origin) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public Category category() {
        return new FpCategory(
            this.endpoint,
            this.token,
            this.origin.getJSONObject("category")
        );
    }

    @Override
    public Direction direction() {
        return Direction.valueOf(this.origin.getString("direction").toLowerCase(Locale.ENGLISH));
    }

    @Override
    public OffsetDateTime creationDate() {
        return new OffsetDateTimeOf(this.origin.getString("creationDate")).get();
    }

    @Override
    public Iterable<String> keywords() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("keywords"),
            JSONArray::getString
        );
    }
}
