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
package org.proshin.finapi.transaction;

import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.transaction.in.DeleteTransactionsCriteria;
import org.proshin.finapi.transaction.in.EditTransactionsParameters;
import org.proshin.finapi.transaction.in.TransactionsCriteria;
import org.proshin.finapi.transaction.out.FpTransactionsPage;
import org.proshin.finapi.transaction.out.TransactionsPage;

public final class FpTransactions implements Transactions {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpTransactions(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/transactions/");
    }

    public FpTransactions(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Transaction one(final Long id) {
        return new FpTransaction(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.get(this.url + id, this.token)
            ),
            this.url
        );
    }

    @Override
    public TransactionsPage query(final TransactionsCriteria criteria) {
        return new FpTransactionsPage(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.get(
                    this.url,
                    this.token,
                    criteria
                )
            ),
            this.url
        );
    }

    @Override
    public Iterable<Long> edit(final EditTransactionsParameters parameters) {
        return new IterableJsonArray<>(
            new JSONObject(
                this.endpoint.patch(this.url, this.token, parameters)
            ).getJSONArray("identifiers"),
            JSONArray::getLong
        );
    }

    @Override
    public void triggerCategorization(final Iterable<Long> bankConnections) {
        final JSONObject parameters = new JSONObject();
        for (final Long bankConnection : bankConnections) {
            parameters.append("bankConnectionIds", bankConnection);
        }
        this.endpoint.post(this.url + "triggerCategorization", this.token, () -> parameters);
    }

    @Override
    public Iterable<Long> deleteAll(final DeleteTransactionsCriteria criteria) {
        return new IterableJsonArray<>(
            new JSONObject(
                this.endpoint.delete(this.url, this.token, criteria)
            ).getJSONArray("identifiers"),
            JSONArray::getLong
        );
    }
}
