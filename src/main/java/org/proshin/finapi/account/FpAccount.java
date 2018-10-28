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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.account.in.FpEditParameters;
import org.proshin.finapi.account.out.ClearingAccount;
import org.proshin.finapi.account.out.FpClearingAccount;
import org.proshin.finapi.account.out.FpHolder;
import org.proshin.finapi.account.out.Holder;
import org.proshin.finapi.account.out.Order;
import org.proshin.finapi.account.out.Status;
import org.proshin.finapi.bankconnection.BankConnection;
import org.proshin.finapi.bankconnection.FpBankConnections;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalBigDecimalOf;
import org.proshin.finapi.primitives.optional.OptionalOffsetDateTimeOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

/**
 * @todo #21 Write unit tests for FpAccount class
 */
public final class FpAccount implements Account {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;
    private final String url;

    public FpAccount(final Endpoint endpoint, final AccessToken token, final JSONObject origin, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
        this.url = url;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public BankConnection bankConnection() {
        return new FpBankConnections(this.endpoint, this.token)
            .one(this.origin.getLong("bankConnectionId"));
    }

    @Override
    public Optional<String> name() {
        return new OptionalStringOf(this.origin, "accountName").get();
    }

    @Override
    public String number() {
        return this.origin.getString("accountNumber");
    }

    @Override
    public Optional<String> subNumber() {
        return new OptionalStringOf(this.origin, "subAccountNumber").get();
    }

    @Override
    public Optional<String> iban() {
        return new OptionalStringOf(this.origin, "iban").get();
    }

    @Override
    public Holder holder() {
        return new FpHolder(this.origin);
    }

    @Override
    public Optional<String> currency() {
        return new OptionalStringOf(this.origin, "accountCurrency").get();
    }

    @Override
    public Type type() {
        return new Type.TypeOf(this.origin.getInt("accountTypeId")).get();
    }

    @Override
    public Optional<BigDecimal> balance() {
        return new OptionalBigDecimalOf(this.origin, "balance").get();
    }

    @Override
    public Optional<BigDecimal> overdraft() {
        return new OptionalBigDecimalOf(this.origin, "overdraft").get();
    }

    @Override
    public Optional<BigDecimal> overdraftLimit() {
        return new OptionalBigDecimalOf(this.origin, "overdraftLimit").get();
    }

    @Override
    public Optional<BigDecimal> availableFunds() {
        return new OptionalBigDecimalOf(this.origin, "availableFunds").get();
    }

    @Override
    public Optional<OffsetDateTime> lastSuccessfulUpdate() {
        return new OptionalOffsetDateTimeOf(this.origin, "lastSuccessfulUpdate").get();
    }

    @Override
    public Optional<OffsetDateTime> lastUpdateAttempt() {
        return new OptionalOffsetDateTimeOf(this.origin, "lastUpdateAttempt").get();
    }

    @Override
    public boolean isNew() {
        return this.origin.getBoolean("isNew");
    }

    @Override
    public Status status() {
        return Status.valueOf(this.origin.getString("status"));
    }

    @Override
    public Iterable<Order> supportedOrders() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("supportedOrders"),
            (array, index) -> Order.valueOf(array.getString(index))
        );
    }

    @Override
    public Iterable<ClearingAccount> clearingAccounts() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("clearingAccounts"),
            (array, index) -> new FpClearingAccount(array.getJSONObject(index))
        );
    }

    @Override
    public void edit(final FpEditParameters parameters) {
        this.endpoint.patch(this.url + this.id(), this.token, parameters);
    }

    @Override
    public void delete(final Long id) {
        this.endpoint.delete(
            this.url + this.id(),
            this.token
        );
    }
}
