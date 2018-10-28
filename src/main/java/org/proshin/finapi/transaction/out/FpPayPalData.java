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

import java.math.BigDecimal;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.optional.OptionalBigDecimalOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class FpPayPalData implements PayPalData {

    private final JSONObject origin;

    public FpPayPalData(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Optional<String> invoiceNumber() {
        return new OptionalStringOf(this.origin, "invoiceNumber").get();
    }

    @Override
    public Optional<BigDecimal> fee() {
        return new OptionalBigDecimalOf(this.origin, "fee").get();
    }

    @Override
    public Optional<BigDecimal> net() {
        return new OptionalBigDecimalOf(this.origin, "net").get();
    }

    @Override
    public Optional<String> auctionSite() {
        return new OptionalStringOf(this.origin, "auctionSite").get();
    }
}
