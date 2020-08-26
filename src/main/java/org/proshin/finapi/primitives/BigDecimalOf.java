/*
 * Copyright 2020 Roman Proshin
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
package org.proshin.finapi.primitives;

import java.math.BigDecimal;
import java.util.function.Supplier;
import org.cactoos.text.FormattedText;
import org.json.JSONObject;
import org.proshin.finapi.primitives.optional.OptionalBigDecimalOf;

public final class BigDecimalOf implements Supplier<BigDecimal> {

    private final OptionalBigDecimalOf origin;
    private final String field;

    public BigDecimalOf(final JSONObject origin, final String name) {
        this.origin = new OptionalBigDecimalOf(origin, name);
        this.field = name;
    }

    @Override
    public BigDecimal get() {
        return this.origin.get()
            .orElseThrow(() -> new IllegalStateException(
                new FormattedText("Field '%s' cannot be null", this.field).toString()
            ));
    }
}
