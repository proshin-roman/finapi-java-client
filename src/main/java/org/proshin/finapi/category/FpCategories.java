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
package org.proshin.finapi.category;

import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.category.in.CashFlowsCriteria;
import org.proshin.finapi.category.in.CategoriesCriteria;
import org.proshin.finapi.category.in.CreateCategoryParameters;
import org.proshin.finapi.category.in.TrainCategorizationParameters;
import org.proshin.finapi.category.out.CashFlows;
import org.proshin.finapi.category.out.FpCashFlows;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;

public final class FpCategories implements Categories {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpCategories(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/categories/");
    }

    public FpCategories(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Category one(final Long id) {
        return new FpCategory(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.get(
                    this.url + id,
                    this.token
                )
            )
        );
    }

    @Override
    public Page<Category> query(final CategoriesCriteria criteria) {
        return new FpPage<>(
            "categories",
            new JSONObject(
                this.endpoint.get(this.url, this.token, criteria)
            ),
            (array, index) -> new FpCategory(this.endpoint, this.token, array.getJSONObject(index))
        );
    }

    @Override
    public CashFlows cashFlows(final CashFlowsCriteria criteria) {
        return new FpCashFlows(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.get(this.url + "cashFlows", this.token, criteria)
            )
        );
    }

    @Override
    public Category create(final CreateCategoryParameters parameters) {
        return new FpCategory(
            this.endpoint,
            this.token,
            new JSONObject(this.endpoint.post(this.url, this.token, parameters, 201))
        );
    }

    @Override
    public void trainCategorization(final TrainCategorizationParameters parameters) {
        this.endpoint.post(this.url + "trainCategorization", this.token, parameters);
    }

    @Override
    public Iterable<Long> deleteAll() {
        return new IterableJsonArray<>(
            new JSONArray(this.endpoint.delete(this.url, this.token)),
            JSONArray::getLong
        );
    }
}
