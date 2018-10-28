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

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
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

    public DirectDebitParameters withStoringPin() {
        this.origin.put("storePin", true);
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

    public DirectDebitParameters withExecutionDate(final OffsetDateTime executionDate) {
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

    public static final class Debtor implements Jsonable {
        private final JSONObject origin;

        public Debtor() {
            this(new JSONObject());
        }

        public Debtor(final JSONObject origin) {
            this.origin = origin;
        }

        public Debtor withName(final String name) {
            this.origin.put("debitorName", name);
            return this;
        }

        public Debtor withIban(final String iban) {
            this.origin.put("debitorIban", iban);
            return this;
        }

        public Debtor withBic(final String bic) {
            this.origin.put("debitorBic", bic);
            return this;
        }

        public Debtor withAmount(final BigDecimal amount) {
            this.origin.put("amount", amount);
            return this;
        }

        public Debtor withPurpose(final String purpose) {
            this.origin.put("purpose", purpose);
            return this;
        }

        public Debtor withSepaPurposeCode(final String sepaPurposeCode) {
            this.origin.put("sepaPurposeCode", sepaPurposeCode);
            return this;
        }

        public Debtor withMandateId(final String mandateId) {
            this.origin.put("mandateId", mandateId);
            return this;
        }

        public Debtor withMandateDate(final OffsetDateTime mandateDate) {
            this.origin.put("mandateDate", new StringOf(mandateDate));
            return this;
        }

        public Debtor withCreditorId(final String creditorId) {
            this.origin.put("creditorId", creditorId);
            return this;
        }

        public Debtor withEndToEndId(final String endToEndId) {
            this.origin.put("endToEndIdId", endToEndId);
            return this;
        }

        @Override
        public JSONObject asJson() {
            return this.origin;
        }
    }
}
