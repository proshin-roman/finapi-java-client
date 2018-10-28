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

import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public final class UpdateParameters implements Jsonable {

    private final JSONObject origin;

    public UpdateParameters() {
        this(new JSONObject());
    }

    public UpdateParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public UpdateParameters withBankConnection(final Long connectionId) {
        this.origin.put("bankConnectionId", connectionId);
        return this;
    }

    public UpdateParameters withPin(final String pin) {
        this.origin.put("bankingPin", pin);
        return this;
    }

    public UpdateParameters withStorePin() {
        this.origin.put("storePin", true);
        return this;
    }

    public UpdateParameters withImportNewAccounts() {
        this.origin.put("importNewAccounts", true);
        return this;
    }

    public UpdateParameters withSkipPositionsDownload() {
        this.origin.put("skipPositionsDownload", true);
        return this;
    }

    public UpdateParameters withLoadOwnerData() {
        this.origin.put("loadOwnerData", true);
        return this;
    }

    public UpdateParameters withChallengeResponse(final String challengeResponse) {
        this.origin.put("challengeResponse", challengeResponse);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
