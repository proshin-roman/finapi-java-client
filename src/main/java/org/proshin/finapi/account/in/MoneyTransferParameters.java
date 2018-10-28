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
import java.util.Iterator;
import org.cactoos.iterator.IteratorOf;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.StringOf;

public final class MoneyTransferParameters implements Jsonable {

    private final JSONObject origin;

    public MoneyTransferParameters() {
        this(new JSONObject());
    }

    public MoneyTransferParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public MoneyTransferParameters withAccount(final Long account) {
        this.origin.put("accountId", account);
        return this;
    }

    public MoneyTransferParameters withBankingPin(final String bankingPin) {
        this.origin.put("bankingPin", bankingPin);
        return this;
    }

    public MoneyTransferParameters withStoringPin() {
        this.origin.put("storePin", true);
        return this;
    }

    public MoneyTransferParameters withTwoStepProcedure(final String id) {
        this.origin.put("twoStepProcedureId", id);
        return this;
    }

    public MoneyTransferParameters withChallengeResponse(final String response) {
        this.origin.put("challengeResponse", response);
        return this;
    }

    public MoneyTransferParameters withExecutionDate(final OffsetDateTime executionDate) {
        this.origin.put("executionDate", new StringOf(executionDate));
        return this;
    }

    public MoneyTransferParameters asSingleBooking() {
        this.origin.put("singleBooking", true);
        return this;
    }

    public MoneyTransferParameters withRecipients(final Recipient... recipients) {
        final Iterator<Recipient> iterator = new IteratorOf<>(recipients);
        if (iterator.hasNext()) {
            final JSONObject jsonObject = iterator.next().asJson();
            for (final String key : jsonObject.keySet()) {
                this.origin.put(key, jsonObject.get(key));
            }
        }
        while (iterator.hasNext()) {
            this.origin.append("additionalMoneyTransfers", iterator.next().asJson());
        }
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }

    public static final class Recipient implements Jsonable {

        private final JSONObject origin;

        public Recipient() {
            this(new JSONObject());
        }

        public Recipient(final JSONObject origin) {
            this.origin = origin;
        }

        public Recipient withName(final String name) {
            this.origin.put("recipientName", name);
            return this;
        }

        public Recipient withIban(final String iban) {
            this.origin.put("recipientIban", iban);
            return this;
        }

        public Recipient withBic(final String bic) {
            this.origin.put("recipientBic", bic);
            return this;
        }

        public Recipient withClearingAccount(final String id) {
            this.origin.put("clearingAccountId", id);
            return this;
        }

        public Recipient withAmount(final BigDecimal amount) {
            this.origin.put("amount", amount);
            return this;
        }

        public Recipient withPurpose(final String purpose) {
            this.origin.put("purpose", purpose);
            return this;
        }

        public Recipient withSepaPurposeCode(final String sepaPurposeCode) {
            this.origin.put("sepaPurposeCode", sepaPurposeCode);
            return this;
        }

        @Override
        public JSONObject asJson() {
            return this.origin;
        }
    }
}
