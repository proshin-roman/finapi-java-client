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
package org.proshin.finapi.security;

import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.security.out.QuantityNominalType;
import org.proshin.finapi.security.out.QuoteType;

public final class FpSecurityTest {

    @Test
    public void test() {
        final Security security = new FpSecurity(
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"accountId\": 2," +
                "  \"name\": \"Wertapapierbezeichnung\"," +
                "  \"isin\": \"DE0008404005\"," +
                "  \"wkn\": \"840400\"," +
                "  \"quote\": 99.999999," +
                "  \"quoteCurrency\": \"EUR\"," +
                "  \"quoteType\": \"ACTU\"," +
                "  \"quoteDate\": \"2018-01-01 00:00:00.000\"," +
                "  \"quantityNominal\": 99.999998," +
                "  \"quantityNominalType\": \"UNIT\"," +
                "  \"marketValue\": 99.999997," +
                "  \"marketValueCurrency\": \"EUR\"," +
                "  \"entryQuote\": 99.999996," +
                "  \"entryQuoteCurrency\": \"EUR\"," +
                "  \"profitOrLoss\": 99.999995" +
                '}')
        );
        assertThat(security.id()).isEqualTo(1L);
        assertThat(security.account()).isEqualTo(2L);
        assertThat(security.name()).isEqualTo(Optional.of("Wertapapierbezeichnung"));
        assertThat(security.isin()).isEqualTo(Optional.of("DE0008404005"));
        assertThat(security.wkn()).isEqualTo(Optional.of("840400"));
        assertThat(security.quote()).isEqualTo(Optional.of(new BigDecimal("99.999999")));
        assertThat(security.quoteCurrency()).isEqualTo(Optional.of("EUR"));
        assertThat(security.quoteType()).isEqualTo(Optional.of(QuoteType.ACTU));
        assertThat(security.quoteDate()).isEqualTo(Optional.of(new OffsetDateTimeOf("2018-01-01 00:00:00.000").get()));
        assertThat(security.quantityNominal()).isEqualTo(Optional.of(new BigDecimal("99.999998")));
        assertThat(security.quantityNominalType()).isEqualTo(Optional.of(QuantityNominalType.UNIT));
        assertThat(security.marketValue()).isEqualTo(Optional.of(new BigDecimal("99.999997")));
        assertThat(security.marketValueCurrency()).isEqualTo(Optional.of("EUR"));
        assertThat(security.entryQuote()).isEqualTo(Optional.of(new BigDecimal("99.999996")));
        assertThat(security.entryQuoteCurrency()).isEqualTo(Optional.of("EUR"));
        assertThat(security.profitOrLoss()).isEqualTo(Optional.of(new BigDecimal("99.999995")));
    }
}
