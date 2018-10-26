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
package org.proshin.finapi.security;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.optional.OptionalBigDecimalOf;
import org.proshin.finapi.primitives.optional.OptionalOffsetDateTimeOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;
import org.proshin.finapi.security.out.QuantityNominalType;
import org.proshin.finapi.security.out.QuoteType;

public final class FpSecurity implements Security {

    private final JSONObject origin;

    public FpSecurity(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public Long account() {
        return this.origin.getLong("accountId");
    }

    @Override
    public Optional<String> name() {
        return new OptionalStringOf(this.origin, "name").get();
    }

    @Override
    public Optional<String> isin() {
        return new OptionalStringOf(this.origin, "isin").get();
    }

    @Override
    public Optional<String> wkn() {
        return new OptionalStringOf(this.origin, "wkn").get();
    }

    @Override
    public Optional<BigDecimal> quote() {
        return new OptionalBigDecimalOf(this.origin, "quote").get();
    }

    @Override
    public Optional<String> quoteCurrency() {
        return new OptionalStringOf(this.origin, "quoteCurrency").get();
    }

    @Override
    public Optional<QuoteType> quoteType() {
        return new OptionalStringOf(this.origin, "quoteType").get().map(QuoteType::valueOf);
    }

    @Override
    public Optional<OffsetDateTime> quoteDate() {
        return new OptionalOffsetDateTimeOf(this.origin, "quoteDate").get();
    }

    @Override
    public Optional<BigDecimal> quantityNominal() {
        return new OptionalBigDecimalOf(this.origin, "quantityNominal").get();
    }

    @Override
    public Optional<QuantityNominalType> quantityNominalType() {
        return new OptionalStringOf(this.origin, "quantityNominalType").get().map(QuantityNominalType::valueOf);
    }

    @Override
    public Optional<BigDecimal> marketValue() {
        return new OptionalBigDecimalOf(this.origin, "marketValue").get();
    }

    @Override
    public Optional<String> marketValueCurrency() {
        return new OptionalStringOf(this.origin, "marketValueCurrency").get();
    }

    @Override
    public Optional<BigDecimal> entryQuote() {
        return new OptionalBigDecimalOf(this.origin, "entryQuote").get();
    }

    @Override
    public Optional<String> entryQuoteCurrency() {
        return new OptionalStringOf(this.origin, "entryQuoteCurrency").get();
    }

    @Override
    public Optional<BigDecimal> profitOrLoss() {
        return new OptionalBigDecimalOf(this.origin, "profitOrLoss").get();
    }
}
