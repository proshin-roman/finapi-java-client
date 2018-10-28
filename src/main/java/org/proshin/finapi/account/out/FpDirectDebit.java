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
package org.proshin.finapi.account.out;

import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.account.in.DirectDebitParameters;
import org.proshin.finapi.endpoint.Endpoint;

public final class FpDirectDebit implements DirectDebit {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpDirectDebit(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public SepaRequestingResponse request(final DirectDebitParameters parameters) {
        return new FpSepaRequestingResponse(
            new JSONObject(
                this.endpoint.post(
                    this.url + "requestSepaDirectDebit",
                    this.token,
                    parameters
                )
            )
        );
    }

    @Override
    public SepaExecutingResponse execute(final Long account, final String bankingTan) {
        return new FpSepaExecutingResponse(
            new JSONObject(
                this.endpoint.post(
                    this.url + "executeSepaDirectDebit",
                    this.token,
                    () -> new JSONObject()
                        .put("accountId", account)
                        .put("bankingTan", bankingTan)
                )
            )
        );
    }
}
