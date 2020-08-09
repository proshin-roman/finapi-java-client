/*
 * Copyright 2019 Roman Proshin
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

public final class ConnectInterfaceParameters implements Jsonable {

    private final JSONObject origin;

    public ConnectInterfaceParameters() {
        this(new JSONObject());
    }

    public ConnectInterfaceParameters(final JSONObject origin) {
        this.origin = origin;
    }

    public ConnectInterfaceParameters withInterface(final BankingInterface bankingInterface) {
        this.origin.put("interface", bankingInterface.name());
        return this;
    }

    public ConnectInterfaceParameters withLoginCredentials(final Map<String, String> credentials) {
        credentials.forEach((label, value) -> this.origin.append(
            "loginCredentials",
            new JSONObject()
                .put("label", label)
                .put("value", value)
        ));
        return this;
    }

    public ConnectInterfaceParameters withStoreSecrets() {
        this.origin.put("storeSecrets", true);
        return this;
    }

    public ConnectInterfaceParameters withSkipPositionsDownload() {
        this.origin.put("skipPositionsDownload", true);
        return this;
    }

    public ConnectInterfaceParameters withLoadOwnerData() {
        this.origin.put("loadOwnerData", true);
        return this;
    }

    public ConnectInterfaceParameters withAccountReferences(final Iterable<String> ibans) {
        ibans.forEach(iban -> this.origin.append("accountReferences", new JSONObject().put("iban", iban)));
        return this;
    }

    public ConnectInterfaceParameters withMultiStepAuthentication(
        final MultiStepAuthentication multiStepAuthentication
    ) {
        this.origin.put("multiStepAuthentication", multiStepAuthentication.asJson());
        return this;
    }

    public ConnectInterfaceParameters withRedirectUrl(final String redirectUrl) {
        this.origin.put("redirectUrl", redirectUrl);
        return this;
    }

    @Override
    public JSONObject asJson() {
        return this.origin;
    }
}
