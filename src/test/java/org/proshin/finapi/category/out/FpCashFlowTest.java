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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.json.JSONObject;
import org.junit.Test;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

public class FpCashFlowTest {
    @Test
    public void test() {
        final FpCashFlow cashFlow = new FpCashFlow(
            new FpEndpoint("http://127.0.0.1"),
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
                "    }")
        );
        assertThat(cashFlow.category().isPresent(), is(true));
        assertThat(cashFlow.category().get().id(), is(378L));
        assertThat(cashFlow.income(), is(new BigDecimal("199.99")));
        assertThat(cashFlow.spending(), is(new BigDecimal("-99.99")));
        assertThat(cashFlow.balance(), is(new BigDecimal("100")));
        assertThat(cashFlow.countIncomeTransactions(), is(5));
        assertThat(cashFlow.countSpendingTransactions(), is(3));
        assertThat(cashFlow.countAllTransactions(), is(8));
    }
}
