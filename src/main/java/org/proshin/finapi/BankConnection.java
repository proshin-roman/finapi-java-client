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
package org.proshin.finapi;

import java.util.Optional;
import org.proshin.finapi.bankconnection.Credentials;
import org.proshin.finapi.bankconnection.EditParameters;
import org.proshin.finapi.bankconnection.Owner;
import org.proshin.finapi.bankconnection.Status;
import org.proshin.finapi.bankconnection.TwoStepProcedures;
import org.proshin.finapi.bankconnection.Type;
import org.proshin.finapi.bankconnection.UpdateResult;

public interface BankConnection {

    Long id();

    Bank bank();

    Optional<String> name();

    Credentials credentials();

    Type type();

    Status status();

    Optional<UpdateResult> lastManualUpdate();

    Optional<UpdateResult> lastAutoUpdate();

    TwoStepProcedures twoStepProcedures();

    Accounts accounts();

    Iterable<Owner> owners();

    BankConnection edit(EditParameters parameters);

    void delete(Long id);
}
