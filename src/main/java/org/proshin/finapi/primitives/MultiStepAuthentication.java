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
package org.proshin.finapi.primitives;

import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public class MultiStepAuthentication implements Jsonable {

    private final JSONObject origin;

    public MultiStepAuthentication() {
        this(new JSONObject());
    }

    public MultiStepAuthentication(final String hash) {
        this(new JSONObject().put("hash", hash));
    }

    public MultiStepAuthentication(final String hash, final String twoStepProcedureId) {
        this(
            new JSONObject()
                .put("hash", hash)
                .put("twoStepProcedureId", twoStepProcedureId)
        );
    }

    public MultiStepAuthentication(final String hash, final String twoStepProcedureId, final String challengeResponse) {
        this(
            new JSONObject()
                .put("hash", hash)
                .put("twoStepProcedureId", twoStepProcedureId)
                .put("challengeResponse", challengeResponse)
        );
    }

    public MultiStepAuthentication(final String hash, final boolean decoupledCallback) {
        this(
            new JSONObject()
                .put("hash", hash)
                .put("decoupledCallback", decoupledCallback)
        );
    }

    public MultiStepAuthentication(final JSONObject origin) {
        this.origin = origin;
    }

    public MultiStepAuthentication withTwoStepProcedure(final String twoStepProcedureId) {
        this.origin.put("twoStepProcedureId", twoStepProcedureId);
        return this;
    }

    public MultiStepAuthentication withChallengeResponse(final String challengeResponse) {
        this.origin.put("challengeResponse", challengeResponse);
        return this;
    }

    public MultiStepAuthentication withRedirectCallback(final String redirectCallback) {
        this.origin.put("redirectCallback", redirectCallback);
        return this;
    }

    public MultiStepAuthentication withDecoupledCallback(final String decoupledCallback) {
        this.origin.put("decoupledCallback", decoupledCallback);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
