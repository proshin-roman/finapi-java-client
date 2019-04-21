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
package org.proshin.finapi.mandator.out;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONObject;
import org.junit.Test;

public class FpDeletionResultTest {
    @Test
    public void test() {
        final FpDeletionResult result = new FpDeletionResult(
            new JSONObject('{' +
                "  \"deletedUsers\": [" +
                "    \"first_user\"," +
                "    \"second_user\"" +
                "  ]," +
                "  \"nonDeletedUsers\": [" +
                "    \"third_user\"," +
                "    \"fourth_user\"" +
                "  ]" +
                '}')
        );
        assertThat(result.deletedUsers(), hasItems("first_user", "second_user"));
        assertThat(result.nonDeletedUsers(), hasItems("third_user", "fourth_user"));
    }
}
