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
package org.proshin.finapi.notificationrule.in.params;

import org.json.JSONObject;
import org.proshin.finapi.notificationrule.TriggerEvent;
import org.proshin.finapi.primitives.StringOf;

public final class BankLoginErrorParams implements NotificationRuleParams {

    private final JSONObject origin;

    public BankLoginErrorParams(final Iterable<Long> bankConnections) {
        this.origin = new JSONObject()
            .put("bankConnectionIds", new StringOf(bankConnections));
    }

    @Override
    public boolean support(final TriggerEvent triggerEvent) {
        return triggerEvent == TriggerEvent.BANK_LOGIN_ERROR;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
