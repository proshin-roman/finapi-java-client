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
import java.time.LocalDate;
import java.util.function.Supplier;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.StringOf;

public final class Transaction implements Jsonable {

    private final Supplier<? extends JSONObject> origin;

    public Transaction(final BigDecimal amount) {
        this(() -> new JSONObject()
            .put("amount", amount));
    }

    public Transaction(final Supplier<? extends JSONObject> origin) {
        this.origin = origin;
    }

    public Transaction withPurpose(final String purpose) {
        return new Transaction(() -> this.origin.get().put("purpose", purpose));
    }

    public Transaction withCounterpart(final String counterpart) {
        return new Transaction(() -> this.origin.get().put("counterpart", counterpart));
    }

    public Transaction withCounterpartIban(final String counterpartIban) {
        return new Transaction(() -> this.origin.get().put("counterpartIban", counterpartIban));
    }

    public Transaction withCounterpartBlz(final String counterpartBlz) {
        return new Transaction(() -> this.origin.get().put("counterpartBlz", counterpartBlz));
    }

    public Transaction withCounterpartBic(final String counterpartBic) {
        return new Transaction(() -> this.origin.get().put("counterpartBic", counterpartBic));
    }

    public Transaction withCounterpartAccountNumber(final String counterpartAccountNumber) {
        return new Transaction(() -> this.origin.get().put("counterpartAccountNumber", counterpartAccountNumber));
    }

    public Transaction withBookingDate(final LocalDate bookingDate) {
        return new Transaction(() -> this.origin.get().put("bookingDate", new StringOf(bookingDate)));
    }

    public Transaction withValueDate(final LocalDate valueDate) {
        return new Transaction(() -> this.origin.get().put("valueDate", new StringOf(valueDate)));
    }

    @Override
    public JSONObject asJson() {
        return this.origin.get();
    }
}
