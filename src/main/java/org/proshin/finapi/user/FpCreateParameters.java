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
package org.proshin.finapi.user;

import org.json.JSONObject;

public final class FpCreateParameters implements CreateParameters {

    private final JSONObject settings;

    public FpCreateParameters() {
        this(new JSONObject());
    }

    public FpCreateParameters(final JSONObject settings) {
        this.settings = settings;
    }

    @Override
    public CreateParameters withId(final String id) {
        this.settings.put("id", id);
        return this;
    }

    @Override
    public CreateParameters withPassword(final String password) {
        this.settings.put("password", password);
        return this;
    }

    @Override
    public CreateParameters withEmail(final String email) {
        this.settings.put("email", email);
        return this;
    }

    @Override
    public CreateParameters withPhone(final String phone) {
        this.settings.put("phone", phone);
        return this;
    }

    @Override
    public CreateParameters withAutoUpdateEnabled() {
        this.settings.put("isAutoUpdateEnabled", true);
        return this;
    }

    @Override
    public String asJson() {
        return this.settings.toString();
    }
}
