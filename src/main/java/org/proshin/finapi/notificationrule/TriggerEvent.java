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

public enum TriggerEvent {
    NEW_ACCOUNT_BALANCE,
    NEW_TRANSACTIONS,
    BANK_LOGIN_ERROR,
    FOREIGN_MONEY_TRANSFER,
    LOW_ACCOUNT_BALANCE,
    HIGH_TRANSACTION_AMOUNT,
    CATEGORY_CASH_FLOW,
    NEW_TERMS_AND_CONDITIONS
}
