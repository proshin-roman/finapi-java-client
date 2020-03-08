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

import java.util.Optional;
import org.apache.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.primitives.LocalDateOf;

public class FpTppCertificateTest extends TestWithMockedEndpoint {

    @Test
    public void test() {
        final TppCertificate certificate = new FpTppCertificate(
            this.endpoint(),
            new FakeAccessToken("fake-access-token"),
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"certificateType\": \"QWAC\"," +
                "  \"label\": \"Global QWAC till 2022\"," +
                "  \"validFrom\": \"2019-07-20\"," +
                "  \"validUntil\": \"2019-07-20\"" +
                '}'),
            ""
        );
        assertThat(certificate.id()).isEqualTo(1L);
        assertThat(certificate.type()).isEqualTo(CertificateType.QWAC);
        assertThat(certificate.label()).isEqualTo(Optional.of("Global QWAC till 2022"));
        assertThat(certificate.validFrom()).isEqualTo(Optional.of(new LocalDateOf("2019-07-20").get()));
        assertThat(certificate.validUntil()).isEqualTo(Optional.of(new LocalDateOf("2019-07-20").get()));
    }

    @Test
    public void testDelete() {
        this.server().when(
            request()
                .withMethod("DELETE")
                .withPath("/api/v1/tppCertificates/99")
                .withHeader("Authorization", "Bearer fake-access-token")
        ).respond(
            response()
                .withStatusCode(HttpStatus.SC_OK)
        );
        final TppCertificate certificate = new FpTppCertificate(
            this.endpoint(),
            new FakeAccessToken("fake-access-token"),
            new JSONObject("{\"id\": 99}"),
            "/api/v1/tppCertificates"
        );
        certificate.delete();
    }
}
