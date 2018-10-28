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
package org.proshin.finapi.primitives;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class StringOf implements Supplier<String> {

    private final String origin;

    public StringOf(final OffsetDateTime value) {
        this(DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.of("Europe/Berlin"))
            .format(value));
    }

    public StringOf(final BigDecimal value) {
        this(((Supplier<String>) () -> {
            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
            df.applyPattern("###.##");
            return df.format(value);
        }
        ).get());
    }

    public StringOf(final Iterable<Long> items) {
        this(items, ",");
    }

    public StringOf(final Iterable<Long> items, final CharSequence delimiter) {
        this(
            StreamSupport.stream(items.spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.joining(delimiter))
        );
    }

    public StringOf(final String origin) {
        this.origin = origin;
    }

    @Override
    public String get() {
        return this.origin;
    }

    @Override
    public String toString() {
        return this.get();
    }
}
