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
package org.proshin.finapi.category.out;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

final class FpCashFlowTest extends TestWithMockedEndpoint {

    @Test
    void test() {
        final CashFlow cashFlow = new FpCashFlow(
            this.endpoint(),
            new FakeAccessToken("user-token"),
            new JSONObject('{' +
                "      \"category\": {" +
                "        \"id\": 378," +
                "        \"name\": \"Sport & Fitness\"," +
                "        \"parentId\": 373," +
                "        \"parentName\": \"Freizeit, Hobbys & Soziales\"," +
                "        \"isCustom\": true," +
                "        \"children\": [" +
                "          1," +
                "          2," +
                "          3" +
                "        ]" +
                "      }," +
                "      \"income\": 199.99," +
                "      \"spending\": -99.99," +
                "      \"balance\": 100," +
                "      \"countIncomeTransactions\": 5," +
                "      \"countSpendingTransactions\": 3," +
                "      \"countAllTransactions\": 8" +
                "    }"),
            "/api/v1/accounts"
        );
        assertThat(cashFlow.category().isPresent()).isTrue();
        assertThat(cashFlow.category().get().id()).isEqualTo(378L);
        assertThat(cashFlow.income()).isEqualTo(new BigDecimal("199.99"));
        assertThat(cashFlow.spending()).isEqualTo(new BigDecimal("-99.99"));
        assertThat(cashFlow.balance()).isEqualTo(new BigDecimal("100"));
        assertThat(cashFlow.countIncomeTransactions()).isEqualTo(5);
        assertThat(cashFlow.countSpendingTransactions()).isEqualTo(3);
        assertThat(cashFlow.countAllTransactions()).isEqualTo(8);
    }
}
