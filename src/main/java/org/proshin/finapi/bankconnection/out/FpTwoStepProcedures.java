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
package org.proshin.finapi.bankconnection.out;

import java.util.Objects;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalJsonField;

public final class FpTwoStepProcedures implements TwoStepProcedures {

    private final JSONObject origin;

    public FpTwoStepProcedures(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Optional<TwoStepProcedure> defaultOne() {
        return new OptionalJsonField<>(
            this.origin,
            "defaultTwoStepProcedureId",
            JSONObject::getString
        ).get().map(value -> {
            for (TwoStepProcedure procedure : this.all()) {
                if (Objects.equals(value, procedure.id())) {
                    return procedure;
                }
            }
            throw new IllegalStateException(
                "List of all two-step procedures doesn't contain an item matches ID of the default one");
        });
    }

    @Override
    public Iterable<TwoStepProcedure> all() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("twoStepProcedures"),
            (array, index) -> new FpTwoStepProcedure(array.getJSONObject(index))
        );
    }
}
