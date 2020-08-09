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
package org.proshin.finapi.tppcertificate.in;

import java.time.LocalDate;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.StringOf;
import org.proshin.finapi.tppcertificate.CertificateType;

public final class CreateTppCertificateParameters implements Jsonable {

    private final JSONObject origin;

    public CreateTppCertificateParameters(
        final CertificateType type,
        final String publicKey,
        final String privateKey
    ) {
        this(
            new JSONObject()
                .put("type", type.name())
                .put("publicKey", publicKey)
                .put("privateKey", privateKey)
        );
    }

    public CreateTppCertificateParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public CreateTppCertificateParameters withPassphrase(final String passphrase) {
        this.origin.put("passphrase", passphrase);
        return this;
    }

    public CreateTppCertificateParameters withLabel(final String label) {
        this.origin.put("label", label);
        return this;
    }

    public CreateTppCertificateParameters withValidFromDate(final LocalDate validFromDate) {
        this.origin.put("validFromDate", new StringOf(validFromDate));
        return this;
    }

    public CreateTppCertificateParameters withValidUntilDate(final LocalDate validUntilDate) {
        this.origin.put("validUntilDate", new StringOf(validUntilDate));
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
