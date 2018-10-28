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
package org.proshin.finapi.notificationrule;

import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.notificationrule.in.CreatingParameters;
import org.proshin.finapi.notificationrule.in.NotificationRulesCriteria;
import org.proshin.finapi.primitives.IterableJsonArray;

public final class FpNotificationRules implements NotificationRules {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpNotificationRules(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/notificationRules/");
    }

    public FpNotificationRules(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public NotificationRule one(final Long id) {
        return new FpNotificationRule(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.get(this.url + id, this.token)
            ),
            this.url
        );
    }

    @Override
    public Iterable<NotificationRule> query(final NotificationRulesCriteria criteria) {
        return new IterableJsonArray<>(
            new JSONObject(
                this.endpoint.get(this.url, this.token, criteria)
            ).getJSONArray("notificationRules"),
            (array, index) -> new FpNotificationRule(
                this.endpoint,
                this.token,
                array.getJSONObject(index),
                this.url
            )
        );
    }

    @Override
    public NotificationRule create(final CreatingParameters parameters) {
        return new FpNotificationRule(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.post(this.url, this.token, parameters)
            ),
            this.url
        );
    }

    @Override
    public Iterable<Long> deleteAll() {
        return new IterableJsonArray<>(
            new JSONObject(
                this.endpoint.delete(this.url, this.token)
            ).getJSONArray("identifiers"),
            JSONArray::getLong
        );
    }
}
