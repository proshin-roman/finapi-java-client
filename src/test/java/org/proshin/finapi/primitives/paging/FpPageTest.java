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

import static org.assertj.core.api.Assertions.assertThat;
import org.cactoos.iterable.Mapped;
import org.cactoos.text.Joined;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public final class FpPageTest {

    @Test
    public void testItems() throws Exception {
        final Page<Integer> items = new FpPage<>(
            "items",
            new JSONObject('{' +
                "  \"items\":[" +
                "    1, 2, 3, 5, 8, 11" +
                "  ]" +
                '}'),
            JSONArray::getInt
        );
        assertThat(new Joined(" - ", new Mapped<>(String::valueOf, items.items())).asString())
            .as("Joined text doesn't match to the expected one")
            .isEqualTo("1 - 2 - 3 - 5 - 8 - 11");
    }

    @Test
    public void testPaging() {
        final Page<Integer> items = new FpPage<>(
            "items",
            new JSONObject('{' +
                "  \"items\":[" +
                "    1, 2, 3, 5, 8, 11" +
                "  ]," +
                "  \"paging\": {" +
                "    \"page\": 1," +
                "    \"perPage\": 20," +
                "    \"pageCount\": 10," +
                "    \"totalCount\": 200" +
                "  }" +
                '}'),
            JSONArray::getInt
        );
        final Paging paging = items.paging();
        assertThat(paging.page()).isEqualTo(1);
        assertThat(paging.perPage()).isEqualTo(20);
        assertThat(paging.pageCount()).isEqualTo(10);
        assertThat(paging.totalCount()).isEqualTo(200);
    }
}
