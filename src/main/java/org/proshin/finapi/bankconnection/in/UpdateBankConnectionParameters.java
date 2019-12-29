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
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.primitives.BankingInterface;
import org.proshin.finapi.primitives.MultiStepAuthentication;

public final class UpdateBankConnectionParameters implements Jsonable {

    private final JSONObject origin;

    public UpdateBankConnectionParameters() {
        this(new JSONObject());
    }

    public UpdateBankConnectionParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public UpdateBankConnectionParameters withBankConnection(final Long connectionId) {
        this.origin.put("bankConnectionId", connectionId);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public UpdateBankConnectionParameters withPin(final String pin) {
        this.origin.put("bankingPin", pin);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public UpdateBankConnectionParameters withStorePin() {
        this.origin.put("storePin", true);
        return this;
    }

    public UpdateBankConnectionParameters withBankingInterface(final BankingInterface bankingInterface) {
        this.origin.put("interface", bankingInterface);
        return this;
    }

    public UpdateBankConnectionParameters withLoginCredentials(final Map<String, String> credentials) {
        credentials.forEach((label, value) -> this.origin.append(
            "loginCredentials",
            new JSONObject()
                .put("label", label)
                .put("value", value)
        ));
        return this;
    }

    public UpdateBankConnectionParameters withStoreSecrets() {
        this.origin.put("storeSecrets", true);
        return this;
    }

    public UpdateBankConnectionParameters withImportNewAccounts() {
        this.origin.put("importNewAccounts", true);
        return this;
    }

    public UpdateBankConnectionParameters withSkipPositionsDownload() {
        this.origin.put("skipPositionsDownload", true);
        return this;
    }

    public UpdateBankConnectionParameters withLoadOwnerData() {
        this.origin.put("loadOwnerData", true);
        return this;
    }

    public UpdateBankConnectionParameters withAccountReferences(final Iterable<String> ibans) {
        ibans.forEach(iban -> this.origin.append(
            "accountReferences",
            new JSONObject().put("iban", iban)
        ));
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public UpdateBankConnectionParameters withChallengeResponse(final String challengeResponse) {
        this.origin.put("challengeResponse", challengeResponse);
        return this;
    }

    public UpdateBankConnectionParameters withMultiStepAuthentication(
        final MultiStepAuthentication multiStepAuthentication
    ) {
        this.origin.put("multiStepAuthentication", multiStepAuthentication.asJson());
        return this;
    }

    public UpdateBankConnectionParameters withRedirectUrl(final String redirectUrl) {
        this.origin.put("redirectUrl", redirectUrl);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
