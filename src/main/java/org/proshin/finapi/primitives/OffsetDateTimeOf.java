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

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public final class OffsetDateTimeOf implements Supplier<OffsetDateTime> {

    private final String origin;
    private final DateTimeFormatter formatter;

    public OffsetDateTimeOf(final String origin) {
        this(
            origin,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                .withZone(ZoneId.of("Europe/Berlin"))
        );
    }

    public OffsetDateTimeOf(final String origin, final DateTimeFormatter formatter) {
        this.origin = origin;
        this.formatter = formatter;
    }

    @Override
    public OffsetDateTime get() {
        return ZonedDateTime.parse(this.origin, formatter).toOffsetDateTime();
    }
}
