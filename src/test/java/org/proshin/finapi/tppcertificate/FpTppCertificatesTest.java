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
package org.proshin.finapi.tppcertificate;

import java.util.Iterator;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.tppcertificate.in.QueryTppCertificatesCriteria;

public class FpTppCertificatesTest extends TestWithMockedEndpoint {

    @Test
    public void testOne() {
        this.server()
            .when(
                HttpRequest.request()
                    .withPath("/api/v1/tppCertificates/99")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer fake-access-token")
            )
            .respond(
                HttpResponse.response("{\"id\": 99}")
            );
        final TppCertificate certificate = new FpTppCertificates(
            this.endpoint(),
            new FakeAccessToken("fake-access-token")
        ).one(99L);
        assertThat(certificate.id(), is(99L));
    }

    @Test
    public void testQuery() {
        this.server()
            .when(
                HttpRequest.request()
                    .withPath("/api/v1/tppCertificates")
                    .withMethod("GET")
                    .withQueryStringParameter("page", "2")
                    .withQueryStringParameter("perPage", "99")
                    .withHeader("Authorization", "Bearer fake-access-token")
            )
            .respond(
                HttpResponse.response('{' +
                    "  \"tppCertificates\": [" +
                    "    {" +
                    "      \"id\": 1," +
                    "      \"certificateType\": \"QWAC\"," +
                    "      \"label\": \"Global QWAC till 2022\"," +
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
        final Page<TppCertificate> certificates = new FpTppCertificates(
            this.endpoint(),
            new FakeAccessToken("fake-access-token")
        ).query(new QueryTppCertificatesCriteria().withPage(2, 99));
        assertThat(certificates.paging().page(), is(2));
        assertThat(certificates.paging().perPage(), is(99));
        assertThat(certificates.paging().pageCount(), is(10));
        assertThat(certificates.paging().totalCount(), is(200));

        final Iterator<TppCertificate> iterator = certificates.items().iterator();
        assertThat(iterator.next(), is(notNullValue()));
        assertThat(iterator.hasNext(), is(false));
    }
}
