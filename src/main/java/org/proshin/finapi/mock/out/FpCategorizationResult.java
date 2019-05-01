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
package org.proshin.finapi.mock.out;

import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.category.Category;
import org.proshin.finapi.category.FpCategory;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.optional.OptionalOf;

public final class FpCategorizationResult implements CategorizationResult {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;

    public FpCategorizationResult(final Endpoint endpoint, final AccessToken token, final JSONObject origin) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
    }

    @Override
    public String transaction() {
        return this.origin.getString("transactionId");
    }

    @Override
    public Optional<Category> category() {
        return new OptionalOf<Category>(
            this.origin,
            "category",
            (json, name) -> new FpCategory(this.endpoint, this.token, json.getJSONObject(name))
        ).get();
    }
}
