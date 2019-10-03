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
package org.proshin.finapi.webform;

import org.junit.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

public class WebFormsTest extends TestWithMockedEndpoint {

    @Test
    public void testOne() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/webForms/12")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer user access token")
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpWebForms(
            this.endpoint(),
            new FakeAccessToken("user access token")
        ).one(12L);
    }
}
