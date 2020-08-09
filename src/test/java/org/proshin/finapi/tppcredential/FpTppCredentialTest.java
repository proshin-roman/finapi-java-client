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

public final class FpTppCredentialTest extends TestWithMockedEndpoint {

    @Test
    public void test() {
        final TppCredential credential = new FpTppCredential(
            this.endpoint(),
            new FakeAccessToken("fake-access-token"),
            new JSONObject('{' +
                "  \"id\": 1," +
                "  \"label\": \"Test credentials\"," +
                "  \"tppAuthenticationGroupId\": 1," +
                "  \"validFrom\": \"2019-07-20\"," +
                "  \"validUntil\": \"2019-07-20\"" +
                '}'),
            ""
        );
        assertThat(credential.id()).isEqualTo(1L);
        assertThat(credential.label()).isEqualTo(Optional.of("Test credentials"));
        assertThat(credential.tppAuthenticationGroupId()).isEqualTo(1L);
        assertThat(credential.validFrom()).isEqualTo(Optional.of(new LocalDateOf("2019-07-20").get()));
        assertThat(credential.validUntil()).isEqualTo(Optional.of(new LocalDateOf("2019-07-20").get()));
    }

    @Test
    public void testDelete() {
        this.server().when(
            request()
                .withMethod("DELETE")
                .withPath("/api/v1/tppCredentials/99")
                .withHeader("Authorization", "Bearer fake-access-token")
        ).respond(
            response()
                .withStatusCode(HttpStatus.SC_OK)
        );
        final TppCredential credential = new FpTppCredential(
            this.endpoint(),
            new FakeAccessToken("fake-access-token"),
            new JSONObject("{\"id\": 99}"),
            "/api/v1/tppCredentials"
        );
        credential.delete();
    }
}
