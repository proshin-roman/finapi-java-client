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
package org.proshin.finapi.label;

import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.label.in.LabelsCriteria;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.paging.FpPage;
import org.proshin.finapi.primitives.paging.Page;

public final class FpLabels implements Labels {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final String url;

    public FpLabels(final Endpoint endpoint, final AccessToken token) {
        this(endpoint, token, "/api/v1/labels/");
    }

    public FpLabels(final Endpoint endpoint, final AccessToken token, final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.url = url;
    }

    @Override
    public Label one(final Long id) {
        return new FpLabel(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.get(this.url + id, this.token)
            )
        );
    }

    @Override
    public Page<Label> query(final LabelsCriteria criteria) {
        return new FpPage<>(
            "labels",
            new JSONObject(
                this.endpoint.get(this.url, this.token, criteria)
            ),
            (array, index) -> new FpLabel(this.endpoint, this.token, array.getJSONObject(index))
        );
    }

    @Override
    public Label create(final String name) {
        return new FpLabel(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.post(
                    this.url,
                    this.token,
                    () -> new JSONObject().put("name", name).toString(),
                    201
                )
            )
        );
    }

    @Override
    public Iterable<Long> deleteAll() {
        return new IterableJsonArray<>(
            new JSONArray(
                new JSONObject(
                    this.endpoint.delete(this.url, this.token)
                ).getJSONArray("identifiers")
            ),
            JSONArray::getLong
        );
    }
}
