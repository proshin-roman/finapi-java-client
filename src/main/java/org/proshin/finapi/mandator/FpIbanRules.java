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
package org.proshin.finapi.mandator;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.mandator.in.NewIbanRule;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.primitives.pair.QueryParamEncodedPair;

public final class FpIbanRules implements IbanRules {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpIbanRules(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Page<IbanRule> query(final int page, final int perPage) {
        return new FpPage<>(
            "ibanRules",
            new JSONObject(
                this.endpoint.get(
                    this.url,
                    this.token,
                    new IterableOf<>(
                        new QueryParamEncodedPair("page", page),
                        new QueryParamEncodedPair("perPage", perPage)
                    )
                )
            ),
            (array, index) -> new FpIbanRule(
                this.endpoint,
                this.token,
                array.getJSONObject(index),
                this.url
            )
        );
    }

    @Override
    public Iterable<IbanRule> create(final NewIbanRule... rules) {
        return new IterableJsonArray<>(
            new JSONObject(
                this.endpoint.post(
                    this.url,
                    this.token,
                    () -> new JSONObject()
                        .put("ibanRules", new Mapped<>(NewIbanRule::asJson, rules))
                )
            ).getJSONArray("ibanRules"),
            (array, index) -> new FpIbanRule(
                this.endpoint,
                this.token,
                array.getJSONObject(index),
                this.url
            )
        );
    }

    @Override
    public Iterable<Long> delete(final Iterable<Long> ids) {
        return new IterableJsonArray<>(
            new JSONObject(
                this.endpoint.post(
                    this.url,
                    this.token,
                    () -> new JSONObject().put("ids", new ListOf<>(ids))
                )
            ).getJSONArray("identifiers"),
            JSONArray::getLong
        );
    }
}
