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
package org.proshin.finapi.client;

import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.client.in.EditClientParameters;
import org.proshin.finapi.client.out.Configuration;
import org.proshin.finapi.client.out.FpConfiguration;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.user.FpUsers;
import org.proshin.finapi.user.Users;

public final class FpClient implements Client {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpClient(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/clientConfiguration");
    }

    public FpClient(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Configuration configuration() {
        return new FpConfiguration(
            new JSONObject(
                this.endpoint.get(this.url, this.token)
            )
        );
    }

    @Override
    public Configuration edit(final EditClientParameters parameters) {
        return new FpConfiguration(
            new JSONObject(
                this.endpoint.patch(this.url, this.token, parameters)
            )
        );
    }

    @Override
    public Users users() {
        return new FpUsers(this.endpoint, this.token);
    }
}
