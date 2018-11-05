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
package org.proshin.finapi.user.in;

import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public final class FpCreateParameters implements Jsonable {

    private final JSONObject origin;

    public FpCreateParameters() {
        this(new JSONObject());
    }

    public FpCreateParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public FpCreateParameters withId(final String id) {
        this.origin.put("id", id);
        return this;
    }

    public FpCreateParameters withPassword(final String password) {
        this.origin.put("password", password);
        return this;
    }

    public FpCreateParameters withEmail(final String email) {
        this.origin.put("email", email);
        return this;
    }

    public FpCreateParameters withPhone(final String phone) {
        this.origin.put("phone", phone);
        return this;
    }

    public FpCreateParameters withAutoUpdateEnabled() {
        this.origin.put("isAutoUpdateEnabled", true);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
