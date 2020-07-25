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
package org.proshin.finapi.payment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.payment.out.Status;
import org.proshin.finapi.payment.out.Type;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.primitives.optional.OptionalOf;
import org.proshin.finapi.primitives.optional.OptionalOffsetDateTimeOf;

public final class FpPayment implements Payment {

    private final JSONObject origin;

    public FpPayment(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public Long accountId() {
        return this.origin.getLong("accountId");
    }

    @Override
    public Type type() {
        return Type.valueOf(this.origin.getString("type"));
    }

    @Override
    public BigDecimal amount() {
        return this.origin.getBigDecimal("amount");
    }

    @Override
    public int orderCount() {
        return this.origin.getInt("orderCount");
    }

    @Override
    public Status status() {
        return Status.valueOf(this.origin.getString("status"));
    }

    @Override
    public Optional<String> bankMessage() {
        return new OptionalOf<>(this.origin, "bankMessage", JSONObject::getString).get();
    }

    @Override
    public OffsetDateTime requestDate() {
        return new OffsetDateTimeOf(this.origin.getString("requestDate")).get();
    }

    @Override
    public Optional<OffsetDateTime> executionDate() {
        return new OptionalOffsetDateTimeOf(this.origin, "executionDate").get();
    }
}
