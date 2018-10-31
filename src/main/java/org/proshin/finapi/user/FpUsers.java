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
package org.proshin.finapi.user;

import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.user.in.CreateParameters;

public final class FpUsers implements Users {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpUsers(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/users/");
    }

    public FpUsers(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public boolean verified(final String userId) {
        return new JSONObject(
            this.endpoint.get(this.url + "verificationStatus", this.token)
        ).getBoolean("isUserVerified");
    }

    @Override
    public User create(final CreateParameters parameters) {
        return new FpUser(
            new JSONObject(
                this.endpoint.post(
                    this.url,
                    this.token,
                    parameters,
                    201
                )
            )
        );
    }

    @Override
    public String requestPasswordChange(final String userId) {
        return new JSONObject(
            this.endpoint.post(
                this.url + "requestPasswordChange",
                this.token,
                () -> new JSONObject().put("userId", userId)
            )
        ).getString("passwordChangeToken");
    }

    @Override
    public void executePasswordChange(final String userId, final String password, final String token) {
        this.endpoint.post(
            this.url + "executePasswordChange",
            this.token,
            () -> new JSONObject()
                .put("userId", userId)
                .put("password", password)
                .put("passwordChangeToken", token)
        );
    }

    @Override
    public void verify(final String userId) {
        this.endpoint.post(
            this.url + "verify/" + userId,
            this.token,
            200
        );
    }

    @Override
    public void deleteUnverified(final String userId) {
        this.endpoint.delete(
            this.url + userId,
            this.token
        );
    }
}
