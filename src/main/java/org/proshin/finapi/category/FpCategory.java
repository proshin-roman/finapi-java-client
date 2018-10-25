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
package org.proshin.finapi.category;

import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalLongOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class FpCategory implements Category {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;

    public FpCategory(final Endpoint endpoint, final AccessToken token, final JSONObject origin) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public String name() {
        return this.origin.getString("name");
    }

    @Override
    public Optional<Long> parentId() {
        return new OptionalLongOf(this.origin, "parentId").get();
    }

    @Override
    public Optional<String> parentName() {
        return new OptionalStringOf(this.origin, "parentName").get();
    }

    @Override
    public boolean isCustom() {
        return this.origin.getBoolean("isCustom");
    }

    @Override
    public Iterable<Long> children() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("children"),
            JSONArray::getLong
        );
    }

    @Override
    public void delete() {
        this.endpoint.delete(String.format("/api/v1/categories/%d", this.id()), this.token);
    }
}
