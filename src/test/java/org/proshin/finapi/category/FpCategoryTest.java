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

import java.util.Optional;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

    @SuppressWarnings("StaticVariableMayNotBeInitialized")
    private static ClientAndServer server;

    @BeforeClass
    public static void startMockServer() {
        server = startClientAndServer(10000);
    }

    @Before
    public void reset() {
        server.reset();
    }

    @AfterClass
    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    public static void stopMockServer() {
        server.stop();
    }

    @Test
    public void test() {
        final FpCategory category = new FpCategory(
            new FpEndpoint("http://127.0.0.1:10000"),
            new FakeAccessToken("test-user"),
            new JSONObject('{' +
                "  \"id\": 378," +
                "  \"name\": \"Sport & Fitness\"," +
                "  \"parentId\": 373," +
                "  \"parentName\": \"Freizeit, Hobbys & Soziales\"," +
                "  \"isCustom\": true," +
                "  \"children\": [1, 2, 3]" +
                '}'),
            "/api/v1/categories"
        );
        assertThat(category.id(), is(378L));
        assertThat(category.name(), is("Sport & Fitness"));
        assertThat(category.parentId(), is(Optional.of(373L)));
        assertThat(category.parentName(), is(Optional.of("Freizeit, Hobbys & Soziales")));
        assertThat(category.isCustom(), is(true));
        assertThat(category.children(), hasItems(1L, 2L, 3L));
    }

    @Test
    public void testEdit() {
        server.when(
            HttpRequest.request("/api/v1/categories/378")
                .withMethod("PATCH")
                .withHeader("Authorization", "Bearer random-token")
                .withBody(new JsonBody("{\"name\": \"New category name\"}")))
            .respond(
                HttpResponse.response("{}")
            );
        new FpCategory(
            new FpEndpoint("http://127.0.0.1:10000"),
            new FakeAccessToken("random-token"),
            new JSONObject("{\"id\":378}"),
            "/api/v1/categories"
        ).edit(new FpEditParameters("New category name"));
    }

    @Test
    public void testDelete() {
        server.when(
            HttpRequest.request("/api/v1/categories/378")
                .withMethod("DELETE")
                .withHeader("Authorization", "Bearer random-token"))
            .respond(
                HttpResponse.response().withStatusCode(200)
            );
        new FpCategory(
            new FpEndpoint("http://127.0.0.1:10000"),
            new FakeAccessToken("random-token"),
            new JSONObject("{\"id\":378}"),
            "/api/v1/categories"
        ).delete();
    }
}
