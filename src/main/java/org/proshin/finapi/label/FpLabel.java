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
package org.proshin.finapi.label;

import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;

public final class FpLabel implements Label {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;

    public FpLabel(final Endpoint endpoint, final AccessToken token, final JSONObject origin) {
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
    public Label edit(final String name) {
        return new FpLabel(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.patch(
                    "/api/v1/labels/" + this.id(),
                    this.token,
                    () -> new JSONObject().put("name", name)
                )
            )
        );
    }

    @Override
    public void delete() {
        this.endpoint.delete("/api/v1/labels/" + this.id(), this.token);
    }
}
