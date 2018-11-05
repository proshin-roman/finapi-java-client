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
package org.proshin.finapi.mock.in;

import java.math.BigDecimal;
import java.util.function.Supplier;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.account.Type;

public final class CategorizationParameter implements Jsonable {

    private final Supplier<JSONObject> origin;

    public CategorizationParameter(final String transaction, final Type type, final BigDecimal amount) {
        this(() -> new JSONObject()
            .put("transactionId", transaction)
            .put("accountTypeId", type.asCode())
            .put("amount", amount)
        );
    }

    public CategorizationParameter withPurpose(final String purpose) {
        return new CategorizationParameter(() -> this.origin.get().put("purpose", purpose));
    }

    public CategorizationParameter withCounterpart(final String counterpart) {
        return new CategorizationParameter(() -> this.origin.get().put("counterpart", counterpart));
    }

    public CategorizationParameter withCounterpartIban(final String counterpartIban) {
        return new CategorizationParameter(() -> this.origin.get().put("counterpartIban", counterpartIban));
    }

    public CategorizationParameter withCounterpartAccountNumber(final String counterpartAccountNumber) {
        return new CategorizationParameter(() -> this.origin.get()
            .put("counterpartAccountNumber", counterpartAccountNumber));
    }

    public CategorizationParameter withCounterpartBlz(final String counterpartBlz) {
        return new CategorizationParameter(() -> this.origin.get().put("counterpartBlz", counterpartBlz));
    }

    public CategorizationParameter withCounterpartBic(final String counterpartBic) {
        return new CategorizationParameter(() -> this.origin.get().put("counterpartBic", counterpartBic));
    }

    public CategorizationParameter withMcCode(final String mcCode) {
        return new CategorizationParameter(() -> this.origin.get().put("mcCode", mcCode));
    }

    public CategorizationParameter withTypeCodeZka(final String typeCodeZka) {
        return new CategorizationParameter(() -> this.origin.get().put("typeCodeZka", typeCodeZka));
    }

    public CategorizationParameter(final Supplier<JSONObject> origin) {
        this.origin = origin;
    }

    @Override
    public JSONObject asJson() {
        return this.origin.get();
    }
}
