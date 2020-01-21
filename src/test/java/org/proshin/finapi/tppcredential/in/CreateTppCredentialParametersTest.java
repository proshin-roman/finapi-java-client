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
package org.proshin.finapi.tppcredential.in;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import org.proshin.finapi.primitives.LocalDateOf;

public class CreateTppCredentialParametersTest {

    @Test
    public void test() {
        assertThat(
            new CreateTppCredentialParameters(
                99L, "custom label"
            )
                .withTppClientId("custom client id")
                .withTppClientSecret("custom client secret")
                .withTppApiKey("custom api key")
                .withValidFromDate(new LocalDateOf("2019-01-22").get())
                .withValidUntilDate(new LocalDateOf("2020-01-31").get())
                .asJson().toString(),
            is('{' +
                "\"tppClientId\":\"custom client id\"," +
                "\"validFromDate\":\"2019-01-22\"," +
                "\"label\":\"custom label\"," +
                "\"tppClientSecret\":\"custom client secret\"," +
                "\"validUntilDate\":\"2020-01-31\"," +
                "\"tppAuthenticationGroupId\":99," +
                "\"tppApiKey\":\"custom api key\"" +
                '}')
        );
    }
}
