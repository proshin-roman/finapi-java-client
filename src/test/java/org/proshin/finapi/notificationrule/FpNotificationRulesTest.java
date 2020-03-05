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
package org.proshin.finapi.notificationrule;

import java.math.BigDecimal;
import org.cactoos.iterable.IterableOfLongs;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.notificationrule.in.CreatingParameters;
import org.proshin.finapi.notificationrule.in.NotificationRulesCriteria;
import org.proshin.finapi.notificationrule.in.params.BankLoginErrorParams;
import org.proshin.finapi.notificationrule.in.params.CategoryCashFlowParams;
import org.proshin.finapi.notificationrule.in.params.ForeignMoneyTransferParams;
import org.proshin.finapi.notificationrule.in.params.HighTransactionAmountParams;
import org.proshin.finapi.notificationrule.in.params.LowAccountBalanceParams;
import org.proshin.finapi.notificationrule.in.params.NewAccountBalanceParams;
import org.proshin.finapi.notificationrule.in.params.NewTransactionsParams;

public class FpNotificationRulesTest extends TestWithMockedEndpoint {

    @Test
    public void testOne() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules/123")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).one(123L);
    }

    @Test
    public void testQuery() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("ids", "1%2C2%2C3")
                    .withQueryStringParameter("triggerEvent", "NEW_ACCOUNT_BALANCE")
                    .withQueryStringParameter("includeDetails", "true")
            )
            .respond(
                HttpResponse.response("{\"notificationRules\":[{}]}")
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).query(
            new NotificationRulesCriteria()
                .withIds(new IterableOfLongs(1L, 2L, 3L))
                .withTriggerEvent(TriggerEvent.NEW_ACCOUNT_BALANCE)
                .withDetails(true)
        ).iterator().next();
    }

    @Test
    public void testCreateNewAccountBalance() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"triggerEvent\": \"NEW_ACCOUNT_BALANCE\"," +
                        "  \"params\": {" +
                        "    \"accountIds\": \"1,2,3\"" +
                        "  }," +
                        "  \"callbackHandle\": \"callbackhandle\"," +
                        "  \"includeDetails\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreatingParameters(TriggerEvent.NEW_ACCOUNT_BALANCE)
                .withCallbackHandle("callbackhandle")
                .withDetails()
                .withParams(
                    new NewAccountBalanceParams(new IterableOfLongs(1L, 2L, 3L))
                )
        );
    }

    @Test
    public void testCreateNewTransactions() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"triggerEvent\": \"NEW_TRANSACTIONS\"," +
                        "  \"params\": {" +
                        "    \"accountIds\": \"1,2,3\"," +
                        "    \"maxTransactionsCount\": 100" +
                        "  }," +
                        "  \"callbackHandle\": \"callbackhandle\"," +
                        "  \"includeDetails\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreatingParameters(TriggerEvent.NEW_TRANSACTIONS)
                .withCallbackHandle("callbackhandle")
                .withDetails()
                .withParams(
                    new NewTransactionsParams(new IterableOfLongs(1L, 2L, 3L))
                        .withMaxTransactionsCount(100)
                )
        );
    }

    @Test
    public void testCreateBankLoginError() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"triggerEvent\": \"BANK_LOGIN_ERROR\"," +
                        "  \"params\": {" +
                        "    \"bankConnectionIds\": \"1,2,3\"" +
                        "  }," +
                        "  \"callbackHandle\": \"callbackhandle\"," +
                        "  \"includeDetails\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreatingParameters(TriggerEvent.BANK_LOGIN_ERROR)
                .withCallbackHandle("callbackhandle")
                .withDetails()
                .withParams(
                    new BankLoginErrorParams(new IterableOfLongs(1L, 2L, 3L))
                )
        );
    }

    @Test
    public void testCreateForeignMoneyTransfer() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"triggerEvent\": \"FOREIGN_MONEY_TRANSFER\"," +
                        "  \"params\": {" +
                        "    \"accountIds\": \"1,2,3\"" +
                        "  }," +
                        "  \"callbackHandle\": \"callbackhandle\"," +
                        "  \"includeDetails\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreatingParameters(TriggerEvent.FOREIGN_MONEY_TRANSFER)
                .withCallbackHandle("callbackhandle")
                .withDetails()
                .withParams(
                    new ForeignMoneyTransferParams(new IterableOfLongs(1L, 2L, 3L))
                )
        );
    }

    @Test
    public void testCreateLowAccountBalance() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"triggerEvent\": \"LOW_ACCOUNT_BALANCE\"," +
                        "  \"params\": {" +
                        "    \"accountIds\": \"1,2,3\"," +
                        "    \"balanceThreshold\": 99.99" +
                        "  }," +
                        "  \"callbackHandle\": \"callbackhandle\"," +
                        "  \"includeDetails\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreatingParameters(TriggerEvent.LOW_ACCOUNT_BALANCE)
                .withCallbackHandle("callbackhandle")
                .withDetails()
                .withParams(
                    new LowAccountBalanceParams(new BigDecimal("99.99"))
                        .withAccounts(new IterableOfLongs(1L, 2L, 3L))
                )
        );
    }

    @Test
    public void testCreateHighTransactionAmount() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"triggerEvent\": \"HIGH_TRANSACTION_AMOUNT\"," +
                        "  \"params\": {" +
                        "    \"accountIds\": \"1,2,3\"," +
                        "    \"maxTransactionsCount\": 45," +
                        "    \"absoluteAmountThreshold\": 98.99" +
                        "  }," +
                        "  \"callbackHandle\": \"callbackhandle\"," +
                        "  \"includeDetails\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreatingParameters(TriggerEvent.HIGH_TRANSACTION_AMOUNT)
                .withCallbackHandle("callbackhandle")
                .withDetails()
                .withParams(
                    new HighTransactionAmountParams(new BigDecimal("98.99"))
                        .withAccounts(new IterableOfLongs(1L, 2L, 3L))
                        .withMaxTransactionsCount(45)
                )
        );
    }

    @Test
    public void testCreateCategoryCashFlow() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"triggerEvent\": \"CATEGORY_CASH_FLOW\"," +
                        "  \"params\": {" +
                        "    \"accountIds\": \"1,2,3\"," +
                        "    \"categoryId\": 378," +
                        "    \"includeChildCategories\": false" +
                        "  }," +
                        "  \"callbackHandle\": \"callbackhandle\"," +
                        "  \"includeDetails\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreatingParameters(TriggerEvent.CATEGORY_CASH_FLOW)
                .withCallbackHandle("callbackhandle")
                .withDetails()
                .withParams(
                    new CategoryCashFlowParams(378L)
                        .withAccounts(new IterableOfLongs(1L, 2L, 3L))
                        .withoutIncludingChildCategories()
                )
        );
    }

    @Test
    public void testCreateNewTermsAndConditions() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer user-token")
                    .withBody(new JsonBody('{' +
                        "  \"triggerEvent\": \"NEW_TERMS_AND_CONDITIONS\"," +
                        "  \"callbackHandle\": \"callbackhandle\"," +
                        "  \"includeDetails\": true" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}").withStatusCode(201)
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).create(
            new CreatingParameters(TriggerEvent.NEW_TERMS_AND_CONDITIONS)
                .withCallbackHandle("callbackhandle")
                .withDetails()
        );
    }

    @Test
    public void testDeleteAll() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/notificationRules")
                    .withMethod("DELETE")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{\"identifiers\":[]}")
            );
        new FpNotificationRules(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).deleteAll();
    }
}
