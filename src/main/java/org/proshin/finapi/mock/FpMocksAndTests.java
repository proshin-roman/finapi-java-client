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
package org.proshin.finapi.mock;

import org.cactoos.collection.CollectionOf;
import org.cactoos.iterable.Mapped;
import org.json.JSONObject;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.mock.in.BatchUpdateParameters;
import org.proshin.finapi.mock.in.CategorizationParameter;
import org.proshin.finapi.mock.out.CategorizationResults;
import org.proshin.finapi.mock.out.FpCategorizationResults;

public final class FpMocksAndTests implements MocksAndTests {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpMocksAndTests(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/tests");
    }

    public FpMocksAndTests(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public void mockBatchUpdate(final BatchUpdateParameters parameters) {
        this.endpoint.post(this.url + "/mockBatchUpdate", this.token, parameters);
    }

    @Override
    public CategorizationResults checkCategorization(final CategorizationParameter... parameters) {
        return new FpCategorizationResults(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.post(
                    this.url + "/checkCategorization",
                    this.token,
                    () -> new JSONObject()
                        .put("transactionData", new CollectionOf<>(new Mapped<>(Jsonable::asJson, parameters)))
                )
            ),
            this.url
        );
    }
}
