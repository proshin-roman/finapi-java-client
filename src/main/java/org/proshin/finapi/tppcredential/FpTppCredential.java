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
package org.proshin.finapi.tppcredential;

import java.time.LocalDate;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.optional.OptionalLocalDateOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;
import org.proshin.finapi.tppcredential.in.EditTppCredentialParameters;

public class FpTppCredential implements TppCredential {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;
    private final String url;

    public FpTppCredential(
        final Endpoint endpoint,
        final AccessToken token,
        final JSONObject origin,
        final String url
    ) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
        this.url = url;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public Optional<String> label() {
        return new OptionalStringOf(this.origin, "label").get();
    }

    @Override
    public Long tppAuthenticationGroupId() {
        return this.origin.getLong("tppAuthenticationGroupId");
    }

    @Override
    public Optional<LocalDate> validFrom() {
        return new OptionalLocalDateOf(this.origin, "validFrom").get();
    }

    @Override
    public Optional<LocalDate> validUntil() {
        return new OptionalLocalDateOf(this.origin, "validUntil").get();
    }

    @Override
    public TppCredential edit(final EditTppCredentialParameters parameters) {
        return new FpTppCredential(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.patch(
                    String.format("%s/%d", this.url, this.id()),
                    this.token,
                    parameters
                )
            ),
            this.url
        );
    }

    @Override
    public void delete() {
        this.endpoint.delete(this.url + '/' + this.id(), this.token);
    }
}
