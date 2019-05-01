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
import org.cactoos.collection.CollectionOf;
import org.cactoos.iterable.Mapped;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public final class BatchUpdateParameters implements Jsonable {

    private final Supplier<JSONObject> origin;

    public BatchUpdateParameters(final boolean withNotifications, final Connection... connections) {
        this(() -> new JSONObject()
            .put("triggerNotifications", withNotifications)
            .put("mockBankConnectionUpdates", new CollectionOf<>(new Mapped<>(Jsonable::asJson, connections)))
        );
    }

    public BatchUpdateParameters(final Supplier<JSONObject> origin) {
        this.origin = origin;
    }

    @Override
    public JSONObject asJson() {
        return this.origin.get();
    }
}
