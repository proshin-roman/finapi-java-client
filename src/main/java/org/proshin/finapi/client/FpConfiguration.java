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
package org.proshin.finapi.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FpConfiguration implements Configuration {

    private final JSONObject origin;

    public FpConfiguration(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public boolean isAutomaticBatchUpdateEnabled() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public Optional<String> userNotificationCallbackUrl() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public Optional<String> userSynchronizationCallbackUrl() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public Optional<Integer> refreshTokensValidityPeriod() {
        return this.optional("refreshTokensValidityPeriod");
    }

    @Override
    public Optional<Integer> userAccessTokensValidityPeriod() {
        return this.optional("userAccessTokensValidityPeriod");
    }

    @Override
    public Optional<Integer> clientAccessTokensValidityPeriod() {
        return this.optional("clientAccessTokensValidityPeriod");
    }

    @Override
    public Optional<Integer> maxUserLoginAttempts() {
        return this.optional("maxUserLoginAttempts");
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
    public Iterable<String> availableBankGroups() {
        final JSONArray array = this.origin.getJSONArray("availableBankGroups");
        final List<String> availableBankGroups = new ArrayList<>(array.length());
        for (int index = 0; index < array.length(); index++) {
            availableBankGroups.add(array.getString(index));
        }
        return availableBankGroups;
    }

    private Optional<Integer> optional(final String name) {
        if (this.origin.isNull(name)) {
            return Optional.empty();
        } else {
            return Optional.of(this.origin.getInt(name));
        }
    }
}
