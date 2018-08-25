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

import java.util.Optional;
import org.json.JSONObject;

public class FinapiError {

    private final JSONObject origin;

    public FinapiError(final JSONObject origin) {
        this.origin = origin;
    }

    public Optional<String> message() {
        return this.origin.has("message")
                   ? Optional.of(this.origin.getString("message"))
                   : Optional.empty();
    }

    public Optional<ErrorCode> errorCode() {
        return this.origin.has("code")
                   ? Optional.of(ErrorCode.valueOf(this.origin.getString("code")))
                   : Optional.empty();
    }

    public Optional<ErrorType> errorType() {
        return this.origin.has("type")
                   ? Optional.of(ErrorType.valueOf(this.origin.getString("type")))
                   : Optional.empty();
    }
}
