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
package org.proshin.finapi.bank;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.bank.out.BankGroup;
import org.proshin.finapi.bank.out.BankInterface;
import org.proshin.finapi.bank.out.FpBankGroup;
import org.proshin.finapi.bank.out.FpBankInterface;
import org.proshin.finapi.bank.out.FpLoginFields;
import org.proshin.finapi.bank.out.LoginFields;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalOffsetDateTimeOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class FpBank implements Bank {

    private final JSONObject origin;

    public FpBank(final JSONObject origin) {
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
    public Optional<String> loginHint() {
        return new OptionalStringOf(this.origin, "loginHint").get();
    }

    @Override
    public Optional<String> bic() {
        return new OptionalStringOf(this.origin, "bic").get();
    }

    @Override
    public String blz() {
        return this.origin.getString("blz");
    }

    @Override
    public Optional<String> location() {
        return new OptionalStringOf(this.origin, "location").get();
    }

    @Override
    public Optional<String> city() {
        return new OptionalStringOf(this.origin, "city").get();
    }

    @Override
    public boolean isSupported() {
        return this.origin.getBoolean("isSupported");
    }

    @Override
    public boolean isTestBank() {
        return this.origin.getBoolean("isTestBank");
    }

    @Override
    public int popularity() {
        return this.origin.getInt("popularity");
    }

    @Override
    public int health() {
        return this.origin.getInt("health");
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Override
    @Deprecated
    public LoginFields loginFields() {
        return new FpLoginFields(this.origin);
    }

    @Override
    public boolean pinsAreVolatile() {
        return this.origin.getBoolean("pinsAreVolatile");
    }

    @Override
    public boolean isCustomerIdPassword() {
        return this.origin.getBoolean("isCustomerIdPassword");
    }

    @Override
    public Iterable<DataSource> supportedDataSources() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("supportedDataSources"),
            (array, index) -> DataSource.valueOf(array.getString(index))
        );
    }

    @Override
    public Iterable<BankInterface> interfaces() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("interfaces"),
            (array, index) -> new FpBankInterface(array.getJSONObject(index))
        );
    }

    @Override
    public Optional<BankGroup> bankGroup() {
        return Optional.ofNullable(this.origin.getJSONObject("bankGroup"))
            .map(FpBankGroup::new);
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
