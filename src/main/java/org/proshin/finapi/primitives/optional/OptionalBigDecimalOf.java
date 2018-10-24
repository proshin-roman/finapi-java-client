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
package org.proshin.finapi.primitives.optional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Supplier;
import org.json.JSONObject;

public final class OptionalBigDecimalOf implements Supplier<Optional<BigDecimal>> {

    private final JSONObject origin;
    private final String name;

    public OptionalBigDecimalOf(final JSONObject origin, final String name) {
        this.origin = origin;
        this.name = name;
    }

    @Override
    public Optional<BigDecimal> get() {
        if (this.origin.isNull(name)) {
            return Optional.empty();
        } else {
            return Optional.of(this.origin.getBigDecimal(name));
        }
    }
}
