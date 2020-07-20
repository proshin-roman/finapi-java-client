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
package org.proshin.finapi.client.in;

import org.json.JSONObject;
import org.proshin.finapi.Jsonable;

public final class EditClientParameters implements Jsonable {

    private final JSONObject origin;

    public EditClientParameters() {
        this(new JSONObject());
    }

    public EditClientParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public EditClientParameters withUserNotificationCallbackUrl(final String userNotificationCallbackUrl) {
        this.origin.put("userNotificationCallbackUrl", userNotificationCallbackUrl);
        return this;
    }

    public EditClientParameters withUserSynchronizationCallbackUrl(final String userSynchronizationCallbackUrl) {
        this.origin.put("userSynchronizationCallbackUrl", userSynchronizationCallbackUrl);
        return this;
    }

    public EditClientParameters withRefreshTokensValidityPeriod(final int refreshTokensValidityPeriod) {
        this.origin.put("refreshTokensValidityPeriod", refreshTokensValidityPeriod);
        return this;
    }

    public EditClientParameters withUserAccessTokensValidityPeriod(final int userAccessTokensValidityPeriod) {
        this.origin.put("userAccessTokensValidityPeriod", userAccessTokensValidityPeriod);
        return this;
    }

    public EditClientParameters withClientAccessTokensValidityPeriod(final int clientAccessTokensValidityPeriod) {
        this.origin.put("clientAccessTokensValidityPeriod", clientAccessTokensValidityPeriod);
        return this;
    }

    /**
     * @deprecated since v0.1.92 due to PSD2-related changes
     */
    @Deprecated
    public EditClientParameters withPinStorageAvailableInWebForm(final boolean isPinStorageAvailableInWebForm) {
        this.origin.put("isPinStorageAvailableInWebForm", isPinStorageAvailableInWebForm);
        return this;
    }

    public EditClientParameters withStoreSecretsAvailableInWebForm(final boolean storeSecretsAvailableInWebForm) {
        this.origin.put("storeSecretsAvailableInWebForm", storeSecretsAvailableInWebForm);
        return this;
    }

    public EditClientParameters withApplicationName(final String applicationName) {
        this.origin.put("applicationName", applicationName);
        return this;
    }

    public EditClientParameters withFinTSProductRegistrationNumber(final String finTSProductRegistrationNumber) {
        this.origin.put("finTSProductRegistrationNumber", finTSProductRegistrationNumber);
        return this;
    }

    public EditClientParameters withSupportSubjectDefault(final String supportSubjectDefault) {
        this.origin.put("supportSubjectDefault", supportSubjectDefault);
        return this;
    }

    public EditClientParameters withSupportEmail(final String supportEmail) {
        this.origin.put("supportEmail", supportEmail);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
