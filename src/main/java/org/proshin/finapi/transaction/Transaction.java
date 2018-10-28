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
package org.proshin.finapi.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.proshin.finapi.category.Category;
import org.proshin.finapi.label.Label;
import org.proshin.finapi.transaction.in.EditTransactionParameters;
import org.proshin.finapi.transaction.in.SplitTransactionParameters;
import org.proshin.finapi.transaction.out.Counterpart;
import org.proshin.finapi.transaction.out.PayPalData;
import org.proshin.finapi.transaction.out.Type;

public interface Transaction {

    Long id();

    Optional<Long> parent();

    Long account();

    OffsetDateTime valueDate();

    OffsetDateTime bankBookingDate();

    OffsetDateTime finapiBookingDate();

    BigDecimal amount();

    Optional<String> purpose();

    Counterpart counterpart();

    Type type();

    Optional<String> sepaPurposeCode();

    Optional<String> primanota();

    Optional<Category> category();

    Iterable<Label> labels();

    boolean isPotentialDuplicate();

    boolean isAdjustingEntry();

    boolean isNew();

    OffsetDateTime importDate();

    Iterable<Long> children();

    Optional<PayPalData> payPalData();

    Optional<String> endToEndReference();

    Transaction split(SplitTransactionParameters parameters);

    Transaction restore();

    Transaction edit(EditTransactionParameters parameters);

    void delete();
}
