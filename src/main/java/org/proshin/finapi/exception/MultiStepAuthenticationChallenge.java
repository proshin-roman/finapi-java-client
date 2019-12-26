/*
 * Copyright 2019 Roman Proshin
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
import org.proshin.finapi.bankconnection.out.FpTwoStepProcedure;
import org.proshin.finapi.bankconnection.out.TwoStepProcedure;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class MultiStepAuthenticationChallenge {

    private final JSONObject origin;

    public MultiStepAuthenticationChallenge(final JSONObject origin) {
        this.origin = origin;
    }

    public String hash() {
        return this.origin.getString("hash");
    }

    public Status status() {
        return Status.valueOf(this.origin.getString("status"));
    }

    public Optional<String> challengeMessage() {
        return new OptionalStringOf(this.origin, "challengeMessage").get();
    }

    public Optional<String> answerFieldLabel() {
        return new OptionalStringOf(this.origin, "answerFieldLabel").get();
    }

    public Optional<String> redirectUrl() {
        return new OptionalStringOf(this.origin, "redirectUrl").get();
    }

    public Optional<String> redirectContext() {
        return new OptionalStringOf(this.origin, "redirectContext").get();
    }

    public Optional<String> redirectContextField() {
        return new OptionalStringOf(this.origin, "redirectContextField").get();
    }

    public Iterable<TwoStepProcedure> twoStepProcedures() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("twoStepProcedures"),
            (array, index) -> new FpTwoStepProcedure(array.getJSONObject(index))
        );
    }

    public Optional<String> opticalData() {
        return new OptionalStringOf(this.origin, "opticalData").get();
    }

    public Optional<String> photoTanMimeType() {
        return new OptionalStringOf(this.origin, "photoTanMimeType").get();
    }

    public Optional<String> photoTanData() {
        return new OptionalStringOf(this.origin, "photoTanData").get();
    }

    public enum Status {
        CHALLENGE_RESPONSE_REQUIRED,
        TWO_STEP_PROCEDURE_REQUIRED,
        REDIRECT_REQUIRED,
        DECOUPLED_AUTH_REQUIRED,
        DECOUPLED_AUTH_IN_PROGRESS
    }
}
