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
package org.proshin.finapi.mandator;

import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.mandator.in.UsersCriteria;
import org.proshin.finapi.mandator.out.DeletionResult;
import org.proshin.finapi.mandator.out.FpDeletionResult;
import org.proshin.finapi.mandator.out.FpUser;
import org.proshin.finapi.mandator.out.User;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;

public final class FpMandator implements Mandator {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpMandator(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/mandatorAdmin");
    }

    public FpMandator(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Page<User> users(final UsersCriteria criteria) {
        return new FpPage<>(
            "users",
            new JSONObject(
                this.endpoint.get(
                    this.url + "/getUserList",
                    this.token,
                    criteria
                )
            ),
            (array, index) -> new FpUser(array.getJSONObject(index))
        );
    }

    @Override
    public DeletionResult deleteUsers(final Iterable<String> ids) {
        return new FpDeletionResult(
            new JSONObject(
                this.endpoint.post(
                    this.url + "/deleteUsers",
                    this.token,
                    () -> {
                        final JSONObject body = new JSONObject();
                        for (final String id : ids) {
                            body.append("userIds", id);
                        }
                        return body;
                    }
                )
            )
        );
    }

    @Override
    public void changeClientCredentials(
        final String clientId,
        final String oldClientSecret,
        final String newClientSecret
    ) {
        this.endpoint.post(
            this.url + "/changeClientCredentials",
            this.token,
            () -> new JSONObject()
                .put("clientId", clientId)
                .put("oldClientSecret", oldClientSecret)
                .put("newClientSecret", newClientSecret)
        );
    }

    @Override
    public KeywordRules keywordRules() {
        return new FpKeywordRules(this.endpoint, this.token, this.url + "/keywordRules");
    }

    @Override
    public IbanRules ibanRules() {
        return new FpIbanRules(this.endpoint, this.token, this.url + "/ibanRules");
    }
}
