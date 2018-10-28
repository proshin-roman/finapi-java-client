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
package org.proshin.finapi.notificationrule;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class FpNotificationRule implements NotificationRule {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;
    private final String url;

    public FpNotificationRule(
        final Endpoint endpoint,
        final AccessToken token,
        final JSONObject origin,
        final String url
    ) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
        this.url = url;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public TriggerEvent triggerEvent() {
        return TriggerEvent.valueOf(this.origin.getString("triggerEvent"));
    }

    @Override
    public Map<String, Object> params() {
        final String field = "params";
        return this.origin.isNull(field)
            ? Collections.emptyMap()
            : this.origin.getJSONObject(field).toMap();
    }

    @Override
    public Optional<String> callbackHandle() {
        return new OptionalStringOf(this.origin, "callbackHandle").get();
    }

    @Override
    public boolean includeDetails() {
        return this.origin.getBoolean("includeDetails");
    }

    @Override
    public void delete() {
        this.endpoint.delete(this.url + this.id(), this.token);
    }
}
