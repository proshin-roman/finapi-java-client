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
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.tppcredential.in.QueryTppAuthenticationGroupsCriteria;

final class FpTppAuthenticationGroupTest extends TestWithMockedEndpoint {

    @Test
    void testQuery() {
        this.server()
            .when(
                HttpRequest.request()
                    .withPath("/api/v1/tppCredentials/tppAuthenticationGroups")
                    .withMethod("GET")
                    .withQueryStringParameter("page", "2")
                    .withQueryStringParameter("perPage", "99")
                    .withHeader("Authorization", "Bearer fake-access-token")
            )
            .respond(
                HttpResponse.response('{' +
                    "  \"tppAuthenticationGroups\": [" +
                    "    {" +
                    "      \"id\": 1," +
                    "      \"name\": \"Test name\"," +
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
        final Page<TppAuthenticationGroup> authenticationGroups = new FpTppAuthenticationGroups(
            this.endpoint(),
            new FakeAccessToken("fake-access-token")
        ).query(new QueryTppAuthenticationGroupsCriteria().withPage(2, 99));
        assertThat(authenticationGroups.paging().page()).isEqualTo(2);
        assertThat(authenticationGroups.paging().perPage()).isEqualTo(99);
        assertThat(authenticationGroups.paging().pageCount()).isEqualTo(10);
        assertThat(authenticationGroups.paging().totalCount()).isEqualTo(200);

        final Iterator<TppAuthenticationGroup> iterator = authenticationGroups.items().iterator();
        assertThat(iterator.next()).isNotNull();
        assertThat(iterator.hasNext()).isFalse();
    }
}
