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
package org.proshin.finapi.primitives.paging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.json.JSONObject;
import org.junit.Test;

public class FpPagingTest {
    @Test
    public void test() {
        final FpPaging paging = new FpPaging(
            new JSONObject('{' +
                "  \"page\": 1," +
                "  \"perPage\": 20," +
                "  \"pageCount\": 10," +
                "  \"totalCount\": 200" +
                '}')
        );
        assertThat(paging.page(), is(1));
        assertThat(paging.perPage(), is(20));
        assertThat(paging.pageCount(), is(10));
        assertThat(paging.totalCount(), is(200));
    }
}
