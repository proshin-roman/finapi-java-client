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
package org.proshin.finapi.bankconnection;

import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.account.Accounts;
import org.proshin.finapi.bank.Bank;
import org.proshin.finapi.bank.FpBank;
import org.proshin.finapi.bankconnection.in.FpEditParameters;
import org.proshin.finapi.bankconnection.out.Credentials;
import org.proshin.finapi.bankconnection.out.FpCredentials;
import org.proshin.finapi.bankconnection.out.FpStatus;
import org.proshin.finapi.bankconnection.out.FpTwoStepProcedures;
import org.proshin.finapi.bankconnection.out.FpUpdateResult;
import org.proshin.finapi.bankconnection.out.Owner;
import org.proshin.finapi.bankconnection.out.Status;
import org.proshin.finapi.bankconnection.out.TwoStepProcedures;
import org.proshin.finapi.bankconnection.out.Type;
import org.proshin.finapi.bankconnection.out.UpdateResult;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.optional.OptionalJsonField;
import org.proshin.finapi.primitives.optional.OptionalObjectOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

/**
 * Bank Connection model
 * @todo #20 Cover FpBankConnection by unit tests with mocked JSON structures.
 */
public final class FpBankConnection implements BankConnection {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;

    public FpBankConnection(final Endpoint endpoint, final AccessToken token, final JSONObject origin) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public Bank bank() {
        return new FpBank(this.origin.getJSONObject("bank"));
    }

    @Override
    public Optional<String> name() {
        return new OptionalStringOf(this.origin, "name").get();
    }

    @Override
    public Credentials credentials() {
        return new FpCredentials(this.origin);
    }

    @Override
    public Type type() {
        return Type.valueOf(this.origin.getString("type"));
    }

    @Override
    public Status status() {
        return new FpStatus(this.origin);
    }

    @Override
    public Optional<UpdateResult> lastManualUpdate() {
        return new OptionalObjectOf(this.origin, "lastManualUpdate").get()
            .map(FpUpdateResult::new);
    }

    @Override
    public Optional<UpdateResult> lastAutoUpdate() {
        return new OptionalObjectOf(this.origin, "lastAutoUpdate").get()
            .map(FpUpdateResult::new);
    }

    @Override
    public Optional<TwoStepProcedures> twoStepProcedures() {
        return new OptionalJsonField<TwoStepProcedures>(
            this.origin,
            "twoStepProcedures",
            (jsonObject, key) -> new FpTwoStepProcedures(jsonObject)
        ).get();
    }

    @Override
    public Accounts accounts() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public Iterable<Owner> owners() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public BankConnection edit(final FpEditParameters parameters) {
        return new FpBankConnection(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.patch(
                    String.format("/api/v1/bankConnections/%d", this.id()),
                    this.token,
                    parameters
                )
            )
        );
    }

    @Override
    public void delete() {
        this.endpoint.delete(String.format("/api/v1/bankConnections/%d", this.id()), this.token);
    }
}
