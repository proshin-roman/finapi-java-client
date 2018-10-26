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
import org.proshin.finapi.security.out.QuantityNominalType;
import org.proshin.finapi.security.out.QuoteType;

public interface Security {

    Long id();

    Long account();

    Optional<String> name();

    Optional<String> isin();

    Optional<String> wkn();

    Optional<BigDecimal> quote();

    Optional<String> quoteCurrency();

    Optional<QuoteType> quoteType();

    Optional<OffsetDateTime> quoteDate();

    Optional<BigDecimal> quantityNominal();

    Optional<QuantityNominalType> quantityNominalType();

    Optional<BigDecimal> marketValue();

    Optional<String> marketValueCurrency();

    Optional<BigDecimal> entryQuote();

    Optional<String> entryQuoteCurrency();

    Optional<BigDecimal> profitOrLoss();
}
