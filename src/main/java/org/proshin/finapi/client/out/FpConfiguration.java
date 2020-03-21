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
package org.proshin.finapi.client.out;

import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.optional.OptionalOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class FpConfiguration implements Configuration {

    private final JSONObject origin;

    public FpConfiguration(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public boolean isAutomaticBatchUpdateEnabled() {
        return this.origin.getBoolean("isAutomaticBatchUpdateEnabled");
    }

    @Override
    public Optional<String> userNotificationCallbackUrl() {
        return new OptionalOf<>(this.origin, "userNotificationCallbackUrl", JSONObject::getString).get();
    }

    @Override
    public Optional<String> userSynchronizationCallbackUrl() {
        return new OptionalOf<>(this.origin, "userSynchronizationCallbackUrl", JSONObject::getString).get();
    }

    @Override
    public Optional<Integer> refreshTokensValidityPeriod() {
        return new OptionalOf<>(this.origin, "refreshTokensValidityPeriod", JSONObject::getInt).get();
    }

    @Override
    public Optional<Integer> userAccessTokensValidityPeriod() {
        return new OptionalOf<>(this.origin, "userAccessTokensValidityPeriod", JSONObject::getInt).get();
    }

    @Override
    public Optional<Integer> clientAccessTokensValidityPeriod() {
        return new OptionalOf<>(this.origin, "clientAccessTokensValidityPeriod", JSONObject::getInt).get();
    }

    @Override
    public int maxUserLoginAttempts() {
        return this.origin.getInt("maxUserLoginAttempts");
    }

    @Override
    public boolean isUserAutoVerificationEnabled() {
        return this.origin.getBoolean("isUserAutoVerificationEnabled");
    }

    @Override
    public boolean isMandatorAdmin() {
        return this.origin.getBoolean("isMandatorAdmin");
    }

    @Override
    public boolean isWebScrapingEnabled() {
        return this.origin.getBoolean("isWebScrapingEnabled");
    }

    @Override
    @Deprecated
    public boolean isXs2aEnabled() {
        return this.origin.getBoolean("isXs2aEnabled");
    }

    @Override
    public Iterable<String> availableBankGroups() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("availableBankGroups"),
            JSONArray::getString
        );
    }

    @Override
    public Optional<String> applicationName() {
        return new OptionalOf<>(this.origin, "applicationName", JSONObject::getString).get();
    }

    @Override
    public Optional<String> finTSProductRegistrationNumber() {
        return new OptionalStringOf(this.origin, "finTSProductRegistrationNumber").get();
    }

    @Override
    public boolean storeSecretsAvailableInWebForm() {
        return this.origin.getBoolean("storeSecretsAvailableInWebForm");
    }

    @Override
    public Optional<String> supportSubjectDefault() {
        return new OptionalStringOf(this.origin, "supportSubjectDefault").get();
    }

    @Override
    public Optional<String> supportEmail() {
        return new OptionalStringOf(this.origin, "supportEmail").get();
    }

    @Override
    public boolean paymentsEnabled() {
        return this.origin.getBoolean("paymentsEnabled");
    }

    @Override
    @Deprecated
    public boolean pinStorageAvailableInWebForm() {
        return this.origin.getBoolean("pinStorageAvailableInWebForm");
    }
}
