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
package org.proshin.finapi.account.out;

import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.json.JSONObject;
import org.junit.Test;

public class FpSepaExecutingResponseTest {

    @Test
    public void test() {
        final SepaExecutingResponse response = new FpSepaExecutingResponse(
            new JSONObject('{' +
                "  \"successMessage\": \"Auftrag ausgeführt.\"," +
                "  \"warnMessage\": \"Es liegen Warnungen vor.\"" +
                '}')
        );
        assertThat(response.successMessage(), is(Optional.of("Auftrag ausgeführt.")));
        assertThat(response.warnMessage(), is(Optional.of("Es liegen Warnungen vor.")));
    }
}
