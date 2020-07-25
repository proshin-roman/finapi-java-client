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

import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.tppcredential.in.QueryTppAuthenticationGroupsCriteria;

public final class FpTppAuthenticationGroups implements TppAuthenticationGroups {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpTppAuthenticationGroups(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/tppCredentials/tppAuthenticationGroups");
    }

    public FpTppAuthenticationGroups(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Page<TppAuthenticationGroup> query(final QueryTppAuthenticationGroupsCriteria criteria) {
        return new FpPage<>(
            "tppAuthenticationGroups",
            new JSONObject(
                this.endpoint.get(this.url, this.token, criteria)
            ),
            (array, index) -> new FpTppAuthenticationGroup(array.getJSONObject(index))
        );
    }
}
