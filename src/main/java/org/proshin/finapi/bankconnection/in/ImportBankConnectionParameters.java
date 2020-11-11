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
package org.proshin.finapi.bankconnection.in;

import java.util.Map;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.account.Type;
import org.proshin.finapi.primitives.BankingInterface;
import org.proshin.finapi.primitives.MultiStepAuthentication;

public final class ImportBankConnectionParameters implements Jsonable {

    private final JSONObject origin;

    public ImportBankConnectionParameters() {
        this(new JSONObject());
    }

    public ImportBankConnectionParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public ImportBankConnectionParameters withBank(final Long bankId) {
        this.origin.put("bankId", bankId);
        return this;
    }

    public ImportBankConnectionParameters withName(final String name) {
        this.origin.put("name", name);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public ImportBankConnectionParameters withUserId(final String userId) {
        this.origin.put("bankingUserId", userId);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public ImportBankConnectionParameters withCustomerId(final String customerId) {
        this.origin.put("bankingCustomerId", customerId);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public ImportBankConnectionParameters withPin(final String pin) {
        this.origin.put("bankingPin", pin);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public ImportBankConnectionParameters withStorePin() {
        this.origin.put("storePin", true);
        return this;
    }

    public ImportBankConnectionParameters withBankingInterface(final BankingInterface bankingInterface) {
        this.origin.put("interface", bankingInterface.name());
        return this;
    }

    public ImportBankConnectionParameters withLoginCredentials(final Map<String, String> credentials) {
        credentials.forEach((label, value) -> this.origin.append(
            "loginCredentials",
            new JSONObject()
                .put("label", label)
                .put("value", value)
        ));
        return this;
    }

    public ImportBankConnectionParameters withStoreSecrets() {
        this.origin.put("storeSecrets", true);
        return this;
    }

    public ImportBankConnectionParameters withSkipPositionsDownload() {
        this.origin.put("skipPositionsDownload", true);
        return this;
    }

    public ImportBankConnectionParameters withLoadOwnerData() {
        this.origin.put("loadOwnerData", true);
        return this;
    }

    public ImportBankConnectionParameters withMaxDaysForDownload(final int days) {
        this.origin.put("maxDaysForDownload", days);
        return this;
    }

    public ImportBankConnectionParameters withAccountTypes(final Iterable<Type> types) {
        this.origin.put("accountTypes", new ListOf<>(new Mapped<>(Type::name, types)));
        return this;
    }

    public ImportBankConnectionParameters withAccountReferences(final Iterable<String> ibans) {
        ibans.forEach(iban -> this.origin.append("accountReferences", new JSONObject().put("iban", iban)));
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public ImportBankConnectionParameters withChallengeResponse(final String challengeResponse) {
        this.origin.put("challengeResponse", challengeResponse);
        return this;
    }

    public ImportBankConnectionParameters withMultiStepAuthentication(
        final MultiStepAuthentication multiStepAuthentication
    ) {
        this.origin.put("multiStepAuthentication", multiStepAuthentication.asJson());
        return this;
    }

    public ImportBankConnectionParameters withRedirectUrl(final String redirectUrl) {
        this.origin.put("redirectUrl", redirectUrl);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
