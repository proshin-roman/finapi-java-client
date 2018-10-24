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
package org.proshin.finapi.account.in;

import org.json.JSONObject;
import org.proshin.finapi.account.Type;

public final class FpEditParameters implements EditParameters {

    private final JSONObject origin;

    public FpEditParameters() {
        this(new JSONObject());
    }

    public FpEditParameters(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public EditParameters withName(final String name) {
        this.origin.put("accountName", name);
        return this;
    }

    @Override
    public EditParameters withType(final Type type) {
        this.origin.put("accountTypeId", type.asCode());
        return this;
    }

    @Override
    public EditParameters withNew(final boolean isNew) {
        this.origin.put("isNew", isNew);
        return this;
    }

    @Override
    public String asJson() {
        return this.origin.toString();
    }
}
