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
package org.proshin.finapi.category.in;

import java.math.BigDecimal;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.account.Type;

public final class TrainCategorizationTransaction implements Jsonable {

    private final JSONObject origin;

    public TrainCategorizationTransaction() {
        this(new JSONObject());
    }

    public TrainCategorizationTransaction(final JSONObject origin) {
        this.origin = origin;
    }

    public TrainCategorizationTransaction withAccountType(final Type type) {
        this.origin.put("accountTypeId", type.asCode());
        return this;
    }

    public TrainCategorizationTransaction withAmount(final BigDecimal amount) {
        this.origin.put("amount", amount);
        return this;
    }

    public TrainCategorizationTransaction withPurpose(final String purpose) {
        this.origin.put("purpose", purpose);
        return this;
    }

    public TrainCategorizationTransaction withCounterpart(final String counterpart) {
        this.origin.put("counterpart", counterpart);
        return this;
    }

    public TrainCategorizationTransaction withCounterpartIban(final String counterpartIban) {
        this.origin.put("counterpartIban", counterpartIban);
        return this;
    }

    public TrainCategorizationTransaction withCounterpartAccountNumber(final String counterpartAccountNumber) {
        this.origin.put("counterpartAccountNumber", counterpartAccountNumber);
        return this;
    }

    public TrainCategorizationTransaction withCounterpartBlz(final String counterpartBlz) {
        this.origin.put("counterpartBlz", counterpartBlz);
        return this;
    }

    public TrainCategorizationTransaction withCounterpartBic(final String counterpartBic) {
        this.origin.put("counterpartBic", counterpartBic);
        return this;
    }

    public TrainCategorizationTransaction withMcCode(final String mcCode) {
        this.origin.put("mcCode", mcCode);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
