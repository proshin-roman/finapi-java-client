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
package org.proshin.finapi.account;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.proshin.finapi.account.in.FpEditParameters;
import org.proshin.finapi.account.out.ClearingAccount;
import org.proshin.finapi.account.out.Holder;
import org.proshin.finapi.account.out.Order;
import org.proshin.finapi.account.out.Status;
import org.proshin.finapi.bankconnection.BankConnection;

public interface Account {

    Long id();

    BankConnection bankConnection();

    Optional<String> name();

    String number();

    Optional<String> subNumber();

    Optional<String> iban();

    Holder holder();

    Optional<String> currency();

    Type type();

    Optional<BigDecimal> balance();

    Optional<BigDecimal> overdraft();

    Optional<BigDecimal> overdraftLimit();

    Optional<BigDecimal> availableFunds();

    Optional<OffsetDateTime> lastSuccessfulUpdate();

    Optional<OffsetDateTime> lastUpdateAttempt();

    boolean isNew();

    Status status();

    Iterable<Order> supportedOrders();

    Iterable<ClearingAccount> clearingAccounts();

    void edit(FpEditParameters parameters);

    void delete(Long id);
}
