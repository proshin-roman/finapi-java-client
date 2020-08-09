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
package org.proshin.finapi.tppcredential.in;

import java.time.LocalDate;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.StringOf;

public final class EditTppCredentialParameters implements Jsonable {

    private final JSONObject origin;

    public EditTppCredentialParameters() {
        this(new JSONObject());
    }

    public EditTppCredentialParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public EditTppCredentialParameters withTppAuthenticationGroupId(final String tppAuthenticationGroupId) {
        this.origin.put("tppAuthenticationGroupId", tppAuthenticationGroupId);
        return this;
    }

    public EditTppCredentialParameters withLabel(final String label) {
        this.origin.put("label", label);
        return this;
    }

    public EditTppCredentialParameters withTppClientId(final String tppClientId) {
        this.origin.put("tppClientId", tppClientId);
        return this;
    }

    public EditTppCredentialParameters withTppClientSecret(final String tppClientSecret) {
        this.origin.put("tppClientSecret", tppClientSecret);
        return this;
    }

    public EditTppCredentialParameters withTppApiKey(final String tppApiKey) {
        this.origin.put("tppApiKey", tppApiKey);
        return this;
    }

    public EditTppCredentialParameters withTppName(final String tppName) {
        this.origin.put("tppName", tppName);
        return this;
    }

    public EditTppCredentialParameters withValidFromDate(final LocalDate validFromDate) {
        this.origin.put("validFromDate", new StringOf(validFromDate));
        return this;
    }

    public EditTppCredentialParameters withValidUntilDate(final LocalDate validUntilDate) {
        this.origin.put("validUntilDate", new StringOf(validUntilDate));
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
