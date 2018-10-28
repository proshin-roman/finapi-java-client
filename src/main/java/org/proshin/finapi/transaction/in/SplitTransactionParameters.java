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
package org.proshin.finapi.transaction.in;

import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public final class SplitTransactionParameters implements Jsonable {

    private final JSONObject origin;

    public SplitTransactionParameters() {
        this(new JSONObject());
    }

    public SplitTransactionParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public SplitTransactionParameters withSubtransactions(final Subtransaction... subtransactions) {
        for (final Subtransaction subtransaction : subtransactions) {
            this.origin.append("subTransactions", subtransaction.asJson());
        }
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
