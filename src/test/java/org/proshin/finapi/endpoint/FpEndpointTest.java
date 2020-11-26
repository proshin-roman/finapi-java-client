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
package org.proshin.finapi.endpoint;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import static org.assertj.core.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.exception.FinapiAuthenticationException;

final class FpEndpointTest extends TestWithMockedEndpoint {

    @Test
    void testAuthenticationException() {
        final String body = '{' +
            "  \"bankId\": 277672," +
            '}';
        this.server()
            .when(
                HttpRequest.request("/api/v1/bankConnections")
                    .withMethod("POST")
                    .withBody(new JsonBody(body))
            ).respond(
            HttpResponse.response('{' +
                "  \"error\": \"unauthorized\"," +
                "  \"error_description\": \"An Authentication object was not found in the SecurityContext\"" +
                '}')
                .withStatusCode(HttpStatus.SC_UNAUTHORIZED)
                .withHeader("x-request-id", "request-id")
        );

        try {
            this.endpoint()
                .post(
                    "/api/v1/bankConnections",
                    new StringEntity(body, ContentType.APPLICATION_JSON),
                    HttpStatus.SC_CREATED
                );
        } catch (final FinapiAuthenticationException e) {
            // do nothing
            return;
        }
        fail("Should never be reached");
    }
}
