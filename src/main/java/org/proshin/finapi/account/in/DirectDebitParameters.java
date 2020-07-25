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
package org.proshin.finapi.account.in;

import java.time.LocalDate;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.MultiStepAuthentication;
import org.proshin.finapi.primitives.StringOf;

public final class DirectDebitParameters implements Jsonable {

    private final JSONObject origin;

    public DirectDebitParameters() {
        this(new JSONObject());
    }

    public DirectDebitParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public DirectDebitParameters withAccount(final Long account) {
        this.origin.put("accountId", account);
        return this;
    }

    public DirectDebitParameters withBankingPin(final String bankingPin) {
        this.origin.put("bankingPin", bankingPin);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public DirectDebitParameters withStoringPin() {
        this.origin.put("storePin", true);
        return this;
    }

    public DirectDebitParameters withStoringSecrets() {
        this.origin.put("storeSecrets", true);
        return this;
    }

    public DirectDebitParameters withTwoStepProcedure(final String id) {
        this.origin.put("twoStepProcedureId", id);
        return this;
    }

    public DirectDebitParameters withDirectDebitType(final DirectDebitType type) {
        this.origin.put("directDebitType", type);
        return this;
    }

    public DirectDebitParameters withSequenceType(final SequenceType type) {
        this.origin.put("sequenceType", type);
        return this;
    }

    public DirectDebitParameters withExecutionDate(final LocalDate executionDate) {
        this.origin.put("executionDate", new StringOf(executionDate));
        return this;
    }

    public DirectDebitParameters asSingleBooking() {
        this.origin.put("singleBooking", true);
        return this;
    }

    public DirectDebitParameters withDebtors(final Debtor... debtors) {
        for (final Debtor debtor : debtors) {
            this.origin.append("directDebits", debtor.asJson());
        }
        return this;
    }

    public DirectDebitParameters withHidingTransactionDetailsInWebForm() {
        this.origin.put("hideTransactionDetailsInWebForm", true);
        return this;
    }

    public DirectDebitParameters withMultiStepAuthentication(final MultiStepAuthentication multiStepAuthentication) {
        this.origin.put("multiStepAuthentication", multiStepAuthentication.asJson());
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }

    public enum DirectDebitType {
        BASIC, B2B
    }

    public enum SequenceType {
        OOFF, FRST, RCUR, FNAL
    }
}
