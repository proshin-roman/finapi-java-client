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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

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
            new FakeAccessToken("fake-access-token"),
            "/api/v1/tppCertificates/"
        ).one(99L);
        assertThat(certificate.id(), is(99L));
    }
}
