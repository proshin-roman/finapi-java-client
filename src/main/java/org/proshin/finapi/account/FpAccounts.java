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
package org.proshin.finapi.account;

import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.account.in.DailyBalancesCriteria;
import org.proshin.finapi.account.in.FpQueryCriteria;
import org.proshin.finapi.account.out.DailyBalances;
import org.proshin.finapi.account.out.DirectDebit;
import org.proshin.finapi.account.out.FpDailyBalances;
import org.proshin.finapi.account.out.FpDirectDebit;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.IterableJsonArray;

/**
 * @todo #21 Write unit tests for FpAccounts
 */
public final class FpAccounts implements Accounts {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpAccounts(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/accounts/");
    }

    public FpAccounts(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Account one(final Long id) {
        return new FpAccount(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.get(this.url + id, this.token)
            ),
            this.url
        );
    }

    @Override
    public Iterable<Account> query(final FpQueryCriteria criteria) {
        return new IterableJsonArray<>(
            new JSONObject(
                this.endpoint.get(
                    this.url,
                    this.token,
                    criteria
                )
            ).getJSONArray("accounts"),
            (array, index) -> new FpAccount(
                this.endpoint,
                this.token,
                array.getJSONObject(index),
                this.url
            )
        );
    }

    @Override
    public DailyBalances dailyBalances(final DailyBalancesCriteria criteria) {
        return new FpDailyBalances(
            new JSONObject(
                this.endpoint.get(this.url + "dailyBalances", this.token, criteria)
            )
        );
    }

    @Override
    public MoneyTransfer moneyTransfer() {
        return new FpMoneyTransfer(this.endpoint, this.token, this.url);
    }

    @Override
    public DirectDebit directDebit() {
        return new FpDirectDebit(this.endpoint, this.token, this.url);
    }

    @Override
    public void deleteAll() {
        this.endpoint.delete(this.url, this.token);
    }
}
