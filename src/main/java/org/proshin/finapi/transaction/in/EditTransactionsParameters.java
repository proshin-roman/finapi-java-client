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

public final class EditTransactionsParameters implements Jsonable {
    private final JSONObject origin;

    public EditTransactionsParameters() {
        this(new JSONObject());
    }

    public EditTransactionsParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public EditTransactionsParameters withIsNew(final boolean isNew) {
        this.origin.put("isNew", isNew);
        return this;
    }

    public EditTransactionsParameters withIsPotentialDuplicate(final boolean isPotentialDuplicate) {
        this.origin.put("isPotentialDuplicate", isPotentialDuplicate);
        return this;
    }

    public EditTransactionsParameters withCategory(final Long category) {
        this.origin.put("categoryId", category);
        return this;
    }

    public EditTransactionsParameters withTrainCategorization(final boolean trainCategorization) {
        this.origin.put("trainCategorization", trainCategorization);
        return this;
    }

    public EditTransactionsParameters withLabels(final Iterable<Long> labels) {
        for (final Long label : labels) {
            this.origin.append("labelIds", label);
        }
        return this;
    }

    public EditTransactionsParameters withIds(final Iterable<Long> ids) {
        for (final Long id : ids) {
            this.origin.append("ids", id);
        }
        return this;
    }

    public EditTransactionsParameters withAccounts(final Iterable<Long> accounts) {
        for (final Long account : accounts) {
            this.origin.append("accountIds", account);
        }
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
