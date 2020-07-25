/*
 * Copyright 2019 Roman Proshin
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
package org.proshin.finapi.bankconnection.out;

import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.BankingInterface;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalObjectOf;

public final class FpBankConnectionInterface implements BankConnectionInterface {

    private final JSONObject origin;

    public FpBankConnectionInterface(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public BankingInterface bankingInterface() {
        return BankingInterface.valueOf(this.origin.getString("interface"));
    }

    @Override
    public Iterable<LoginCredential> credentials() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("loginCredentials"),
            (array, index) -> new FpLoginCredential(array.getJSONObject(index))
        );
    }

    @Override
    public TwoStepProcedures twoStepProcedures() {
        return new FpTwoStepProcedures(this.origin);
    }

    @Override
    public BankConsent aisConsent() {
        return new FpBankConsent(this.origin.getJSONObject("aisConsent"));
    }

    @Override
    public Optional<UpdateResult> lastManualUpdate() {
        return new OptionalObjectOf(this.origin, "lastManualUpdate").get()
            .map(FpUpdateResult::new);
    }

    @Override
    public Optional<UpdateResult> lastAutoUpdate() {
        return new OptionalObjectOf(this.origin, "lastAutoUpdate").get()
            .map(FpUpdateResult::new);
    }
}
