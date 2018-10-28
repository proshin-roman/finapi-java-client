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
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.primitives.paging.Paging;
import org.proshin.finapi.transaction.FpTransaction;
import org.proshin.finapi.transaction.Transaction;

public final class FpTransactionsPage implements TransactionsPage {

    private final Page<Transaction> page;
    private final JSONObject origin;

    public FpTransactionsPage(
        final Endpoint endpoint,
        final AccessToken token,
        final JSONObject origin,
        final String url
    ) {
        this(
            origin,
            new FpPage<>(
                "transactions",
                origin,
                (array, index) -> new FpTransaction(
                    endpoint,
                    token,
                    array.getJSONObject(index),
                    url
                )
            )
        );
    }

    public FpTransactionsPage(final JSONObject origin, final Page<Transaction> page) {
        this.page = page;
        this.origin = origin;
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

    @Override
    public Iterable<Transaction> items() {
        return this.page.items();
    }

    @Override
    public Paging paging() {
        return this.page.paging();
    }
}
