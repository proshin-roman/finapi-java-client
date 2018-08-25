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

import org.cactoos.collection.StickyCollection;
import org.json.JSONObject;
import org.proshin.finapi.account.Type;

public final class FpImportParameters implements ImportParameters {

    private final JSONObject origin;

    public FpImportParameters() {
        this(new JSONObject());
    }

    public FpImportParameters(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public ImportParameters withBank(final Long bankId) {
        this.origin.put("bankId", bankId);
        return this;
    }

    @Override
    public ImportParameters withUserId(final String userId) {
        this.origin.put("bankingUserId", userId);
        return this;
    }

    @Override
    public ImportParameters withCustomerId(final String customerId) {
        this.origin.put("bankingCustomerId", customerId);
        return this;
    }

    @Override
    public ImportParameters withPin(final String pin) {
        this.origin.put("bankingPin", pin);
        return this;
    }

    @Override
    public ImportParameters withStorePin() {
        this.origin.put("storePin", true);
        return this;
    }

    @Override
    public ImportParameters withName(final String name) {
        this.origin.put("name", name);
        return this;
    }

    @Override
    public ImportParameters withSkipPositionsDownload() {
        this.origin.put("skipPositionsDownload", true);
        return this;
    }

    @Override
    public ImportParameters withLoadOwnerData() {
        this.origin.put("loadOwnerData", true);
        return this;
    }

    @Override
    public ImportParameters withMaxDaysForDownload(final int days) {
        this.origin.put("maxDaysForDownload", days);
        return this;
    }

    @Override
    public ImportParameters withAccountTypes(final Iterable<Type> types) {
        this.origin.put("accountTypeIds", new StickyCollection<>(types));
        return this;
    }

    @Override
    public ImportParameters withChallengeResponse(final String challengeResponse) {
        this.origin.put("challengeResponse", challengeResponse);
        return this;
    }

    @Override
    public String asJson() {
        return this.origin.toString();
    }
}
