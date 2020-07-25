/*
 * Copyright 2020 Roman Proshin
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
package org.proshin.finapi.account.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.StringOf;

public final class Debtor implements Jsonable {
    private final JSONObject origin;

    public Debtor() {
        this(new JSONObject());
    }

    public Debtor(final JSONObject origin) {
        this.origin = origin;
    }

    public Debtor withName(final String name) {
        this.origin.put("debitorName", name);
        return this;
    }

    public Debtor withIban(final String iban) {
        this.origin.put("debitorIban", iban);
        return this;
    }

    public Debtor withBic(final String bic) {
        this.origin.put("debitorBic", bic);
        return this;
    }

    public Debtor withAmount(final BigDecimal amount) {
        this.origin.put("amount", amount);
        return this;
    }

    public Debtor withPurpose(final String purpose) {
        this.origin.put("purpose", purpose);
        return this;
    }

    public Debtor withSepaPurposeCode(final String sepaPurposeCode) {
        this.origin.put("sepaPurposeCode", sepaPurposeCode);
        return this;
    }

    public Debtor withMandateId(final String mandateId) {
        this.origin.put("mandateId", mandateId);
        return this;
    }

    public Debtor withMandateDate(final LocalDate mandateDate) {
        this.origin.put("mandateDate", new StringOf(mandateDate));
        return this;
    }

    public Debtor withCreditorId(final String creditorId) {
        this.origin.put("creditorId", creditorId);
        return this;
    }

    public Debtor withEndToEndId(final String endToEndId) {
        this.origin.put("endToEndId", endToEndId);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
