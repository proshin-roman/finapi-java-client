/*
 * Copyright 2020 Roman Proshin
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
package org.proshin.finapi.tppcredential;

import java.util.Iterator;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.json.JSONObject;
import org.junit.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.tppcredential.in.EditTppCredentialParameters;
import org.proshin.finapi.tppcredential.in.QueryTppCredentialsCriteria;

public class FpTppCredentialsTest extends TestWithMockedEndpoint {

    @Test
    public void testOne() {
        this.server()
            .when(
                HttpRequest.request()
                    .withPath("/api/v1/tppCredentials/99")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer fake-access-token")
            )
            .respond(
                HttpResponse.response("{\"id\": 99}")
            );
        final TppCredential credential = new FpTppCredentials(
            this.endpoint(),
            new FakeAccessToken("fake-access-token")
        ).one(99L);
        assertThat(credential.id(), is(99L));
    }

    @Test
    public void testQuery() {
        this.server()
            .when(
                HttpRequest.request()
                    .withPath("/api/v1/tppCredentials")
                    .withMethod("GET")
                    .withQueryStringParameter("page", "2")
                    .withQueryStringParameter("perPage", "99")
                    .withHeader("Authorization", "Bearer fake-access-token")
            )
            .respond(
                HttpResponse.response('{' +
                    "  \"tppCredentials\": [" +
                    "    {" +
                    "      \"id\": 1," +
                    "      \"label\": \"Test credentials\"," +
                    "      \"tppAuthenticationGroupId\": 1," +
                    "      \"validFrom\": \"2019-07-20\"," +
                    "      \"validUntil\": \"2019-07-20\"" +
                    "    }" +
                    "  ]," +
                    "  \"paging\": {" +
                    "    \"page\": 2," +
                    "    \"perPage\": 99," +
                    "    \"pageCount\": 10," +
                    "    \"totalCount\": 200" +
                    "  }" +
                    '}')
            );
        final Page<TppCredential> credentials = new FpTppCredentials(
            this.endpoint(),
            new FakeAccessToken("fake-access-token")
        ).query(new QueryTppCredentialsCriteria().withPage(2, 99));
        assertThat(credentials.paging().page(), is(2));
        assertThat(credentials.paging().perPage(), is(99));
        assertThat(credentials.paging().pageCount(), is(10));
        assertThat(credentials.paging().totalCount(), is(200));

        final Iterator<TppCredential> iterator = credentials.items().iterator();
        assertThat(iterator.next(), is(notNullValue()));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void testEdit() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/tppCredentials/378")
                    .withMethod("PATCH")
                    .withHeader("Authorization", "Bearer random-token")
                    .withBody(new JsonBody("{\"label\": \"Changed label\"}")))
            .respond(
                HttpResponse.response("{}")
            );
        new FpTppCredential(
            this.endpoint(),
            new FakeAccessToken("random-token"),
            new JSONObject("{\"id\":378}"),
            "/api/v1/tppCredentials"
        ).edit(new EditTppCredentialParameters()
            .withLabel("Changed label"));
    }
}
