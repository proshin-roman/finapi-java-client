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
package org.proshin.finapi.mock.out;

import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.proshin.finapi.endpoint.FpEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;

public class FpCategorizationResultTest {
    @Test
    public void test() {
        final CategorizationResult result = new FpCategorizationResult(
            new FpEndpoint(""),
            new FakeAccessToken("user-token"),
            new JSONObject('{' +
                "  \"transactionId\": \"transaction\"," +
                "  \"category\": {" +
                "    \"id\": 378," +
                "    \"name\": \"Sport & Fitness\"," +
                "    \"parentId\": 373," +
                "    \"parentName\": \"Freizeit, Hobbys & Soziales\"," +
                "    \"isCustom\": true," +
                "    \"children\": [1, 2, 3]" +
                "  }" +
                '}'),
            "/some-url"
        );
        assertThat(result.category().isPresent()).isTrue();
        assertThat(result.category().get().id()).isEqualTo(378L);
        assertThat(result.transaction()).isEqualTo("transaction");
    }
}
