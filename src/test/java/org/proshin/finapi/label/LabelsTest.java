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

import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.label.in.LabelsCriteria;
import org.proshin.finapi.primitives.paging.Page;

final class LabelsTest extends TestWithMockedEndpoint {

    @Test
    void testOne() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/labels/12")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        final Label label = new FpLabels(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).one(12L);
    }

    @Test
    void testQuery() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/labels")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user-token")
                    .withQueryStringParameter("ids", "1,2")
                    .withQueryStringParameter("search", "just%20a%20word")
                    .withQueryStringParameter("order", "id,asc", "name,desc")
            )
            .respond(
                HttpResponse.response("{}")
            );
        final Page<Label> labels = new FpLabels(
            this.endpoint(),
            new FakeAccessToken("user-token")
        ).query(
            new LabelsCriteria()
                .withIds(new IterableOf<>(1L, 2L))
                .withSearch("just a word")
                .withPage(2, 23)
                .orderBy("id,asc", "name,desc")
        );
    }
}
