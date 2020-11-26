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
import java.util.function.Supplier;
import org.apache.http.HttpResponse;
import org.cactoos.io.InputOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.json.JSONObject;

public final class FinapiAuthenticationException extends RuntimeException {

    @SuppressWarnings("staticfree")
    private static final long serialVersionUID = -2536176269994901299L;

    @SuppressWarnings("TransientFieldNotInitialized")
    private final transient String requestId;

    @SuppressWarnings("nullfree")
    public FinapiAuthenticationException(final HttpResponse response) {
        this(
            ((Supplier<String>) () -> {
                try {
                    final JSONObject jsonObject = new JSONObject(
                        new UncheckedText(
                            new TextOf(
                                new InputOf(response.getEntity().getContent()),
                                StandardCharsets.UTF_8
                            )
                        ).asString()
                    );
                    return String.format("%s: %s",
                        jsonObject.getString("error"), jsonObject.getString("error_description")
                    );
                } catch (final IOException e) {
                    throw new RuntimeException("Couldn't read the response body", e);
                }
            }
            ).get(),
            response.getFirstHeader("X-Request-Id").getValue()
        );
    }

    public FinapiAuthenticationException(final String message, final String requestId) {
        super(message);
        this.requestId = requestId;
    }

    public String requestId() {
        return this.requestId;
    }
}
