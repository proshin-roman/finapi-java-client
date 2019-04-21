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
package org.proshin.finapi.category;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.category.in.FpEditParameters;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

public class FpCategoryTest {

    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private ClientAndServer server;

    @Before
    public void startMockServer() {
        this.server = startClientAndServer(10000);
    }

    @Test
    public void testEdit() {
        this.server.when(
            HttpRequest.request("/api/v1/categories/378")
                .withMethod("PATCH")
                .withHeader("Authorization", "Bearer random-token")
                .withBody(new JsonBody("{\"name\": \"New category name\"}")))
            .respond(
                HttpResponse.response('{' +
                    "  \"id\": 378," +
                    "  \"name\": \"New category name\"," +
                    "  \"parentId\": 373," +
                    "  \"parentName\": \"Freizeit, Hobbys & Soziales\"," +
                    "  \"isCustom\": true," +
                    "  \"children\": []" +
                    '}')
            );
        final String newName = "New category name";
        final Category category = new FpCategory(
            new FpEndpoint("http://127.0.0.1:10000"),
            new FakeAccessToken("random-token"),
            new JSONObject('{' +
                "  \"id\": 378," +
                "  \"name\": \"Sport & Fitness\"," +
                "  \"parentId\": 373," +
                "  \"parentName\": \"Freizeit, Hobbys & Soziales\"," +
                "  \"isCustom\": true," +
                "  \"children\": []" +
                '}')
        ).edit(new FpEditParameters(newName));
        assertThat(category.name(), is(newName));
    }

    @After
    public void stopMockServer() {
        this.server.stop();
    }
}
