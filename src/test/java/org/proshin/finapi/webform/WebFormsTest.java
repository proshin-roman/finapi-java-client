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

import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.fake.FakeEndpoint;
import org.proshin.finapi.fake.FakeRoute;
import org.proshin.finapi.fake.matcher.path.ExactPathMatcher;

public class WebFormsTest {

    @Test
    public void testOne() {
        final WebForms webForms = new FpWebForms(
            new FakeEndpoint(
                new FakeRoute(
                    new ExactPathMatcher("/api/v1/webForms/12"),
                    '{' +
                        "  \"id\": 12," +
                        "  \"token\": \"random token\"," +
                        "  \"status\": \"NOT_YET_OPENED\"," +
                        "  \"serviceResponseCode\": null," +
                        "  \"serviceResponseBody\": null" +
                        '}'
                )
            ),
            new FakeAccessToken("user access token")
        );

        final WebForm webForm = webForms.one(12L);
        assertThat(webForm.id(), is(12L));
        assertThat(webForm.token(), is("random token"));
        assertThat(webForm.status(), is(WebForm.Status.NOT_YET_OPENED));
        assertThat(webForm.serviceResponseCode(), is(Optional.empty()));
        assertThat(webForm.serviceResponseBody(), is(Optional.empty()));
    }
}
