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
package org.proshin.finapi.category.out;

import java.math.BigDecimal;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.category.Category;
import org.proshin.finapi.category.FpCategory;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.optional.OptionalObjectOf;

public final class FpCashFlow implements CashFlow {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;

    public FpCashFlow(final Endpoint endpoint, final AccessToken token, final JSONObject origin) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
    }

    @Override
    public Optional<Category> category() {
        return new OptionalObjectOf(this.origin, "category").get()
                   .map(json -> new FpCategory(this.endpoint, this.token, json));
    }

    @Override
    public BigDecimal income() {
        return this.origin.getBigDecimal("income");
    }

    @Override
    public BigDecimal spending() {
        return this.origin.getBigDecimal("spending");
    }

    @Override
    public BigDecimal balance() {
        return this.origin.getBigDecimal("balance");
    }
}
