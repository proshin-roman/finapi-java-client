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
package org.proshin.finapi.bank.out;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.BankingInterface;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalOffsetDateTimeOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;
import org.proshin.finapi.tppcredential.FpTppAuthenticationGroup;
import org.proshin.finapi.tppcredential.TppAuthenticationGroup;

public final class FpBankInterface implements BankInterface {

    private final JSONObject origin;

    public FpBankInterface(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public BankingInterface bankingInterface() {
        return BankingInterface.valueOf(this.origin.getString("interface"));
    }

    @Override
    public Optional<TppAuthenticationGroup> tppAuthenticationGroup() {
        return Optional.ofNullable(this.origin.getJSONObject("tppAuthenticationGroup"))
            .map(FpTppAuthenticationGroup::new);
    }

    @Override
    public Iterable<LoginCredential> loginCredentials() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("loginCredentials"),
            (array, index) -> new FpLoginCredential(array.getJSONObject(index))
        );
    }

    @Override
    public Iterable<BankInterfaceProperty> properties() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("properties"),
            (array, index) -> BankInterfaceProperty.valueOf(array.getString(index))
        );
    }

    @Override
    public Optional<String> loginHint() {
        return new OptionalStringOf(this.origin, "loginHint").get();
    }

    @Override
    public int health() {
        return this.origin.getInt("health");
    }

    @Override
    public Optional<OffsetDateTime> lastCommunicationAttempt() {
        return new OptionalOffsetDateTimeOf(this.origin, "lastCommunicationAttempt").get();
    }

    @Override
    public Optional<OffsetDateTime> lastSuccessfulCommunication() {
        return new OptionalOffsetDateTimeOf(this.origin, "lastSuccessfulCommunication").get();
    }
}
