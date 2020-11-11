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
package org.proshin.finapi.mock.in;

import java.util.function.Supplier;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.BankingInterface;

public final class Connection implements Jsonable {

    private final Supplier<? extends JSONObject> origin;

    public Connection(final Long connection, final BankingInterface bankingInterface) {
        this(connection, bankingInterface, false);
    }

    public Connection(
        final Long connection,
        final BankingInterface bankingInterface,
        final boolean simulateBankLoginError,
        final Account... accounts
    ) {
        this(() -> new JSONObject()
            .put("bankConnectionId", connection)
            .put("interface", bankingInterface)
            .put("simulateBankLoginError", simulateBankLoginError)
            .put("mockAccountsData", new ListOf<>(new Mapped<>(Jsonable::asJson, accounts)))
        );
    }

    public Connection(final Supplier<? extends JSONObject> origin) {
        this.origin = origin;
    }

    @Override
    public JSONObject asJson() {
        return this.origin.get();
    }
}
