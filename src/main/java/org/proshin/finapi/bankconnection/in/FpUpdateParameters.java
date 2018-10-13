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

public final class FpUpdateParameters implements UpdateParameters {

    private final JSONObject origin;

    public FpUpdateParameters() {
        this(new JSONObject());
    }

    public FpUpdateParameters(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public UpdateParameters withBankConnection(final Long connectionId) {
        this.origin.put("bankConnectionId", connectionId);
        return this;
    }

    @Override
    public UpdateParameters withPin(final String pin) {
        this.origin.put("bankingPin", pin);
        return this;
    }

    @Override
    public UpdateParameters withStorePin() {
        this.origin.put("storePin", true);
        return this;
    }

    @Override
    public UpdateParameters withImportNewAccounts() {
        this.origin.put("importNewAccounts", true);
        return this;
    }

    @Override
    public UpdateParameters withSkipPositionsDownload() {
        this.origin.put("skipPositionsDownload", true);
        return this;
    }

    @Override
    public UpdateParameters withLoadOwnerData() {
        this.origin.put("loadOwnerData", true);
        return this;
    }

    @Override
    public UpdateParameters withChallengeResponse(final String challengeResponse) {
        this.origin.put("challengeResponse", challengeResponse);
        return this;
    }

    @Override
    public String asJson() {
        return this.origin.toString();
    }
}
