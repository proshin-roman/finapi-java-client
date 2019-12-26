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
package org.proshin.finapi.bankconnection.in;

import java.util.Map;
import org.json.JSONObject;
import org.proshin.finapi.BankingInterface;
import org.proshin.finapi.Jsonable;

public final class EditBankConnectionParameters implements Jsonable {

    private final JSONObject origin;

    public EditBankConnectionParameters() {
        this(new JSONObject());
    }

    public EditBankConnectionParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public EditBankConnectionParameters withName(final String name) {
        this.origin.put("name", name);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public EditBankConnectionParameters withUserId(final String userId) {
        this.origin.put("bankingUserId", userId);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public EditBankConnectionParameters withCustomerId(final String customerId) {
        this.origin.put("bankingCustomerId", customerId);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public EditBankConnectionParameters withPin(final String pin) {
        this.origin.put("bankingPin", pin);
        return this;
    }

    public EditBankConnectionParameters withBankingInterface(final BankingInterface bankingInterface) {
        this.origin.put("interface", bankingInterface);
        return this;
    }

    public EditBankConnectionParameters withLoginCredentials(final Map<String, String> credentials) {
        credentials.forEach((label, value) -> {
            this.origin.append("loginCredentials", new JSONObject().put("label", label).put("value", value));
        });
        return this;
    }

    public EditBankConnectionParameters withDefaultTwoStepProcedure(final String procedureId) {
        this.origin.put("defaultTwoStepProcedureId", procedureId);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
