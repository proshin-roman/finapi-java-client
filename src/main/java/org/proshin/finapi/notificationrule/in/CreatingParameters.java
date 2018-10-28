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
package org.proshin.finapi.notificationrule.in;

import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.notificationrule.TriggerEvent;
import org.proshin.finapi.notificationrule.in.params.NotificationRuleParams;

public final class CreatingParameters implements Jsonable {

    private final JSONObject origin;
    private final TriggerEvent triggerEvent;

    public CreatingParameters(final TriggerEvent triggerEvent) {
        this(
            new JSONObject()
                .put("triggerEvent", triggerEvent.name()),
            triggerEvent
        );
    }

    public CreatingParameters(final JSONObject origin, final TriggerEvent triggerEvent) {
        this.origin = origin;
        this.triggerEvent = triggerEvent;
    }

    public CreatingParameters withParams(final NotificationRuleParams params) {
        if (params.support(this.triggerEvent)) {
            this.origin.put("params", params.asJson());
        } else {
            throw new RuntimeException("Incompatible params and triggerEvent arguments");
        }
        return this;
    }

    public CreatingParameters withCallbackHandle(final String callbackHandle) {
        this.origin.put("callbackHandle", callbackHandle);
        return this;
    }

    public CreatingParameters withIncludingDetails() {
        this.origin.put("includeDetails", true);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
