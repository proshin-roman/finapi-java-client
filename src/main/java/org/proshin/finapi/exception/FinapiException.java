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
package org.proshin.finapi.exception;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import org.apache.http.HttpResponse;
import org.cactoos.io.InputOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.json.JSONObject;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public final class FinapiException extends RuntimeException {

    private final JSONObject origin;

    public FinapiException(final int expected, final HttpResponse response) throws IOException {
        this(
            expected,
            response.getStatusLine().getStatusCode(),
            new JSONObject(
                new TextOf(
                    new InputOf(response.getEntity().getContent()),
                    StandardCharsets.UTF_8
                ).asString()
            )
        );
    }

    public FinapiException(final int expected, final int actual, final JSONObject origin) {
        this(
            new UncheckedText(
                new FormattedText(
                    "Server returns code %d instead of %d. Response was:\n%s",
                    actual, expected, origin.toString()
                )
            ).asString(),
            origin
        );
    }

    public FinapiException(final String message, final JSONObject origin) {
        super(message);
        this.origin = origin;
    }

    public Iterable<FinapiError> errors() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("errors"),
            (array, index) -> new FinapiError(array.getJSONObject(index))
        );
    }

    public OffsetDateTime date() {
        return new OffsetDateTimeOf(this.origin.getString("date")).get();
    }

    public String requestId() {
        return this.origin.getString("requestId");
    }

    public String endpoint() {
        return this.origin.getString("endpoint");
    }

    public String authContext() {
        return this.origin.getString("authContext");
    }
}
