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
package org.proshin.finapi.label;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.json.JSONObject;
import org.junit.Test;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.fake.FakeEndpoint;

public class LabelTest {
    @Test
    public void testSuccessCase() {
        final Label label = new FpLabel(
            new FakeEndpoint(),
            new FakeAccessToken("fake token"),
            new JSONObject("{\"id\": 23, \"name\": \"Label name\"}")
        );
        assertThat(label.id(), is(23L));
        assertThat(label.name(), is("Label name"));
    }
}
