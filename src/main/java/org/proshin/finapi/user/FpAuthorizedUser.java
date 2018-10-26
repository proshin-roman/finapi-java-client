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

import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.bankconnection.BankConnections;
import org.proshin.finapi.bankconnection.FpBankConnections;
import org.proshin.finapi.category.Categories;
import org.proshin.finapi.category.FpCategories;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.label.FpLabels;
import org.proshin.finapi.label.Labels;
import org.proshin.finapi.notificationrule.NotificationRules;

/**
 * @todo #19 Finish categories(), labels() and notificationRules() when appropriate classes are implemented.
 */
public final class FpAuthorizedUser implements AuthorizedUser {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final User origin;

    public FpAuthorizedUser(final Endpoint endpoint, final AccessToken token) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = new FpUser(
            new JSONObject(
                endpoint.get("/api/v1/users", token)
            )
        );
    }

    @Override
    public String id() {
        return this.origin.id();
    }

    @Override
    public String password() {
        return this.origin.password();
    }

    @Override
    public Optional<String> email() {
        return this.origin.email();
    }

    @Override
    public Optional<String> phone() {
        return this.origin.phone();
    }

    @Override
    public boolean isAutoUpdateEnabled() {
        return this.origin.isAutoUpdateEnabled();
    }

    @Override
    public void delete() {
        this.endpoint.delete("/api/v1/users", this.token);
    }

    @Override
    public BankConnections connections() {
        return new FpBankConnections(this.endpoint, this.token);
    }

    @Override
    public Categories categories() {
        return new FpCategories(this.endpoint, this.token);
    }

    @Override
    public Labels labels() {
        return new FpLabels(this.endpoint, this.token);
    }

    @Override
    public NotificationRules notificationRules() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
