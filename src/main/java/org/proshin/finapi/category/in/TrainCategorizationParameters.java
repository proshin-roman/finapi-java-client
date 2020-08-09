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
package org.proshin.finapi.category.in;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public final class TrainCategorizationParameters implements Jsonable {

    private final JSONObject origin;

    public TrainCategorizationParameters() {
        this(new JSONObject());
    }

    public TrainCategorizationParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public TrainCategorizationParameters withTransactions(final TrainCategorizationTransaction... transactions) {
        this.origin.put(
            "transactionData",
            new JSONArray(
                Arrays.stream(transactions)
                    .map(tx -> new JSONObject(tx.asString()))
                    .collect(Collectors.toList())
            )
        );
        return this;
    }

    public TrainCategorizationParameters withCategory(final Long category) {
        this.origin.put("categoryId", category);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
