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
package org.proshin.finapi.bank;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.proshin.finapi.bank.out.BankInterface;
import org.proshin.finapi.bank.out.LoginFields;

public interface Bank {

    Long id();

    String name();

    Optional<String> loginHint();

    Optional<String> bic();

    String blz();

    Optional<String> location();

    Optional<String> city();

    boolean isSupported();

    boolean isTestBank();

    int popularity();

    int health();

    LoginFields loginFields();

    boolean pinsAreVolatile();

    boolean isCustomerIdPassword();

    Iterable<DataSource> supportedDataSources();

    Iterable<BankInterface> interfaces();

    Optional<OffsetDateTime> lastCommunicationAttempt();

    Optional<OffsetDateTime> lastSuccessfulCommunication();

    enum DataSource {
        FINTS_SERVER, WEB_SCRAPER
    }
}
