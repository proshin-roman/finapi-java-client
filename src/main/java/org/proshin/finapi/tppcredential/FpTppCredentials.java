/*
 * Copyright 2020 Roman Proshin
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

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.tppcredential.in.CreateTppCredentialParameters;
import org.proshin.finapi.tppcredential.in.QueryTppCredentialsCriteria;

public final class FpTppCredentials implements TppCredentials {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpTppCredentials(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/tppCredentials");
    }

    public FpTppCredentials(
        final Endpoint endpoint,
        final AccessToken token,
        final String url
    ) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public TppCredential one(final Long id) {
        return new FpTppCredential(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.get(
                    String.format("%s/%d", this.url, id),
                    this.token
                )
            ),
            this.url
        );
    }

    @Override
    public Page<TppCredential> query(final QueryTppCredentialsCriteria criteria) {
        return new FpPage<>(
            "tppCredentials",
            new JSONObject(
                this.endpoint.get(this.url, this.token, criteria)
            ),
            (array, index) -> new FpTppCredential(this.endpoint, this.token, array.getJSONObject(index), this.url)
        );
    }

    @Override
    public TppCredential create(final CreateTppCredentialParameters parameters) {
        return new FpTppCredential(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.post(
                    this.url,
                    this.token,
                    parameters,
                    HttpStatus.SC_CREATED
                )
            ),
            this.url
        );
    }
}
