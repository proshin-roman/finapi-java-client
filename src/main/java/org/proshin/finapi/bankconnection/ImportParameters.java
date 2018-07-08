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

import org.cactoos.iterable.IterableOf;
import org.proshin.finapi.account.Type;

public interface ImportParameters {

    ImportParameters withBank(Long bankId);

    ImportParameters withUserId(String userId);

    ImportParameters withCustomerId(String customerId);

    ImportParameters withPin(String pin);

    ImportParameters withName(String name);

    ImportParameters withSkipPositionsDownload();

    ImportParameters withLoadOwnerData();

    ImportParameters withMaxDaysForDownload(int days);

    default ImportParameters withAccountTypes(Type... types) {
        return this.withAccountTypes(new IterableOf<>(types));
    }

    ImportParameters withAccountTypes(Iterable<Type> types);

    ImportParameters withChallengeResponse(String challengeResponse);

    String asJson();
}
