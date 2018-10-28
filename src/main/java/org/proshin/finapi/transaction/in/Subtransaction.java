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
package org.proshin.finapi.transaction.in;

import java.math.BigDecimal;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public final class Subtransaction implements Jsonable {

    private final JSONObject origin;

    public Subtransaction() {
        this(new JSONObject());
    }

    public Subtransaction(final JSONObject origin) {
        this.origin = origin;
    }

    public Subtransaction withAmount(final BigDecimal amount) {
        this.origin.put("amount", amount);
        return this;
    }

    public Subtransaction withCategory(final Long category) {
        this.origin.put("categoryId", category);
        return this;
    }

    public Subtransaction withPurpose(final String purpose) {
        this.origin.put("purpose", purpose);
        return this;
    }

    public Subtransaction withCounterpart(final String counterpart) {
        this.origin.put("counterpart", counterpart);
        return this;
    }

    public Subtransaction withCounterpartAccountNumber(final String accountNumber) {
        this.origin.put("counterpartAccountNumber", accountNumber);
        return this;
    }

    public Subtransaction withCounterpartIban(final String iban) {
        this.origin.put("counterpartIban", iban);
        return this;
    }

    public Subtransaction withCounterpartBic(final String bic) {
        this.origin.put("counterpartBic", bic);
        return this;
    }

    public Subtransaction withCounterpartBlz(final String blz) {
        this.origin.put("counterpartBlz", blz);
        return this;
    }

    public Subtransaction withLabels(final Iterable<Long> labels) {
        for (final Long label : labels) {
            this.origin.append("labelIds", label);
        }
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
