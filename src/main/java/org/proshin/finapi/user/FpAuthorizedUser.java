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
import org.proshin.finapi.account.Accounts;
import org.proshin.finapi.account.FpAccounts;
import org.proshin.finapi.bankconnection.BankConnections;
import org.proshin.finapi.bankconnection.FpBankConnections;
import org.proshin.finapi.category.Categories;
import org.proshin.finapi.category.FpCategories;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.label.FpLabels;
import org.proshin.finapi.label.Labels;
import org.proshin.finapi.notificationrule.FpNotificationRules;
import org.proshin.finapi.notificationrule.NotificationRules;
import org.proshin.finapi.security.FpSecurities;
import org.proshin.finapi.security.Securities;
import org.proshin.finapi.transaction.FpTransactions;
import org.proshin.finapi.transaction.Transactions;
import org.proshin.finapi.webform.FpWebForms;
import org.proshin.finapi.webform.WebForms;

public final class FpAuthorizedUser implements AuthorizedUser {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final User origin;
    private final String url;

    public FpAuthorizedUser(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/users");
    }

    public FpAuthorizedUser(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = new FpUser(
            new JSONObject(
                endpoint.get(url, token)
            )
        );
        this.url = url;
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
        this.endpoint.delete(this.url, this.token);
    }

    @Override
    public BankConnections connections() {
        return new FpBankConnections(this.endpoint, this.token);
    }

    @Override
    public Accounts accounts() {
        return new FpAccounts(this.endpoint, this.token);
    }

    @Override
    public Transactions transactions() {
        return new FpTransactions(this.endpoint, this.token);
    }

    @Override
    public Securities securities() {
        return new FpSecurities(this.endpoint, this.token);
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
        return new FpNotificationRules(this.endpoint, this.token);
    }

    @Override
    public WebForms webForms() {
        return new FpWebForms(this.endpoint, this.token);
    }
}
