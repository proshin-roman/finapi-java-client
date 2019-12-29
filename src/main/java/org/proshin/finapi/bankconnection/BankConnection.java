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
package org.proshin.finapi.bankconnection;

import java.util.Optional;
import org.proshin.finapi.bank.Bank;
import org.proshin.finapi.bankconnection.in.ConnectInterfaceParameters;
import org.proshin.finapi.bankconnection.in.EditBankConnectionParameters;
import org.proshin.finapi.bankconnection.out.BankConnectionInterface;
import org.proshin.finapi.bankconnection.out.Credentials;
import org.proshin.finapi.bankconnection.out.Owner;
import org.proshin.finapi.bankconnection.out.Status;
import org.proshin.finapi.bankconnection.out.TwoStepProcedures;
import org.proshin.finapi.bankconnection.out.Type;
import org.proshin.finapi.bankconnection.out.UpdateResult;
import org.proshin.finapi.primitives.BankingInterface;

public interface BankConnection {

    Long id();

    Bank bank();

    Optional<String> name();

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    Credentials credentials();

    Type type();

    Status status();

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    Optional<UpdateResult> lastManualUpdate();

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    Optional<UpdateResult> lastAutoUpdate();

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    TwoStepProcedures twoStepProcedures();

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    boolean ibanOnlyMoneyTransferSupported();

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    boolean ibanOnlyDirectDebitSupported();

    @Deprecated
    boolean collectiveMoneyTransferSupported();

    Iterable<Long> accounts();

    Iterable<Owner> owners();

    Iterable<BankConnectionInterface> interfaces();

    BankConnection edit(EditBankConnectionParameters parameters);

    BankConnection connectInterface(ConnectInterfaceParameters parameters);

    void removeInterface(BankingInterface bankingInterface);

    void delete();
}
