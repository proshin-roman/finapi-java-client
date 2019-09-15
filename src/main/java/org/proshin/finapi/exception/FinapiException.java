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
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;
import org.json.JSONObject;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public final class FinapiException extends RuntimeException {

    private static final long serialVersionUID = -5608855544688297953L;

    @SuppressWarnings("TransientFieldNotInitialized")
    private final transient JSONObject origin;
    @SuppressWarnings({"TransientFieldNotInitialized", "OptionalUsedAsFieldOrParameterType"})
    private final transient Optional<String> location;

    public FinapiException(final int expected, final HttpResponse response) {
        super(
            String.format(
                "Unexpected response code: expected=%d, actual=%s",
                expected, response.getStatusLine().getStatusCode()
            )
        );
        final String content;
        try {
            content = new TextOf(
                new InputOf(response.getEntity().getContent()),
                StandardCharsets.UTF_8
            ).asString();
        } catch (final IOException e) {
            throw new RuntimeException("Couldn't read the response body", e);
        }
        this.origin = new JSONObject(content);
        this.location = Optional.ofNullable(response.getFirstHeader("Location"))
            .map(NameValuePair::getValue);
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

    public String bank() {
        return this.origin.getString("bank");
    }

    public Optional<String> location() {
        return this.location;
    }
}
