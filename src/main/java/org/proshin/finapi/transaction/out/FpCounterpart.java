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
package org.proshin.finapi.transaction.out;

import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class FpCounterpart implements Counterpart {

    private final JSONObject origin;

    public FpCounterpart(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Optional<String> name() {
        return new OptionalStringOf(this.origin, "counterpartName").get();
    }

    @Override
    public Optional<String> accountNumber() {
        return new OptionalStringOf(this.origin, "counterpartAccountNumber").get();
    }

    @Override
    public Optional<String> iban() {
        return new OptionalStringOf(this.origin, "counterpartIban").get();
    }

    @Override
    public Optional<String> blz() {
        return new OptionalStringOf(this.origin, "counterpartBlz").get();
    }

    @Override
    public Optional<String> bic() {
        return new OptionalStringOf(this.origin, "counterpartBic").get();
    }

    @Override
    public Optional<String> bankName() {
        return new OptionalStringOf(this.origin, "counterpartBankName").get();
    }

    @Override
    public Optional<String> mandateReference() {
        return new OptionalStringOf(this.origin, "counterpartMandateReference").get();
    }

    @Override
    public Optional<String> customerReference() {
        return new OptionalStringOf(this.origin, "counterpartCustomerReference").get();
    }

    @Override
    public Optional<String> creditorId() {
        return new OptionalStringOf(this.origin, "counterpartCreditorId").get();
    }
}
