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

import org.cactoos.collection.CollectionOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.mandator.in.NewKeywordRule;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class FpKeywordRules implements KeywordRules {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpKeywordRules(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Page<KeywordRule> query(final int page, final int perPage) {
        return new FpPage<>(
            "keywordRules",
            new JSONObject(
                this.endpoint.get(
                    this.url,
                    this.token,
                    new IterableOf<>(
                        new UrlEncodedPair("page", page),
                        new UrlEncodedPair("perPage", perPage)
                    )
                )
            ),
            (array, index) -> new FpKeywordRule(
                this.endpoint,
                this.token,
                array.getJSONObject(index)
            )
        );
    }

    @Override
    public Iterable<KeywordRule> create(final NewKeywordRule... rules) {
        return new IterableJsonArray<>(
            new JSONObject(
                this.endpoint.post(
                    this.url,
                    this.token,
                    () -> new JSONObject()
                        .put("keywordRules", new Mapped<>(NewKeywordRule::asJson, rules))
                )
            ).getJSONArray("keywordRules"),
            (array, index) -> new FpKeywordRule(
                this.endpoint,
                this.token,
                array.getJSONObject(index)
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
                    () -> new JSONObject().put("ids", new CollectionOf<>(ids))
                )
            ).getJSONArray("identifiers"),
            JSONArray::getLong
        );
    }
}
