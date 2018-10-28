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
package org.proshin.finapi.account.out;

import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class FpSepaRequestingResponse implements SepaRequestingResponse {

    private final JSONObject origin;

    public FpSepaRequestingResponse() {
        this(new JSONObject());
    }

    public FpSepaRequestingResponse(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Optional<String> successMessage() {
        return new OptionalStringOf(this.origin, "successMessage").get();
    }

    @Override
    public Optional<String> warnMessage() {
        return new OptionalStringOf(this.origin, "warnMessage").get();
    }

    @Override
    public Optional<String> challengeMessage() {
        return new OptionalStringOf(this.origin, "challengeMessage").get();
    }

    @Override
    public Optional<String> answerFieldLabel() {
        return new OptionalStringOf(this.origin, "answerFieldLabel").get();
    }

    @Override
    public Optional<String> tanListNumber() {
        return new OptionalStringOf(this.origin, "tanListNumber").get();
    }

    @Override
    public Optional<String> opticalData() {
        return new OptionalStringOf(this.origin, "opticalData").get();
    }

    @Override
    public Optional<String> photoTanMimeType() {
        return new OptionalStringOf(this.origin, "photoTanMimeType").get();
    }

    @Override
    public Optional<String> photoTanData() {
        return new OptionalStringOf(this.origin, "photoTanData").get();
    }
}
