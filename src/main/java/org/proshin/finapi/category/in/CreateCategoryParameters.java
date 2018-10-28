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

import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public final class CreateCategoryParameters implements Jsonable {

    private final JSONObject origin;

    public CreateCategoryParameters() {
        this(new JSONObject());
    }

    public CreateCategoryParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public CreateCategoryParameters withName(final String name) {
        this.origin.put("name", name);
        return this;
    }

    public CreateCategoryParameters withParent(final Long parent) {
        this.origin.put("parentId", parent);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
