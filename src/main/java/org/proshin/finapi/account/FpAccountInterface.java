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
package org.proshin.finapi.account;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.BankingInterface;
import org.proshin.finapi.account.out.Capability;
import org.proshin.finapi.account.out.Status;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalOffsetDateTimeOf;

public class FpAccountInterface implements AccountInterface {

    private final JSONObject origin;

    public FpAccountInterface(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public BankingInterface bankingInterface() {
        return BankingInterface.valueOf(this.origin.getString("interface"));
    }

    @Override
    public Status status() {
        return Status.valueOf(this.origin.getString("status"));
    }

    @Override
    public Iterable<Capability> capabilities() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("capabilities"),
            (json, index) -> Capability.valueOf(json.getString(index))
        );
    }

    @Override
    public Optional<OffsetDateTime> lastSuccessfulUpdate() {
        return new OptionalOffsetDateTimeOf(this.origin, "lastSuccessfulUpdate").get();
    }

    @Override
    public Optional<OffsetDateTime> lastUpdateAttempt() {
        return new OptionalOffsetDateTimeOf(this.origin, "lastUpdateAttempt").get();
    }
}
