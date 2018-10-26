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

import java.util.Iterator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.fake.FakeEndpoint;
import org.proshin.finapi.fake.FakeRoute;
import org.proshin.finapi.label.in.LabelsCriteria;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.primitives.paging.Paging;

public class LabelsTest {

    @Test
    public void testOne() {
        final Label label = new FpLabels(
            new FakeEndpoint(
                new FakeRoute("{\"id\": 12, \"name\": \"Label name\"}")
            ),
            new FakeAccessToken("fake token")
        ).one(12L);

        assertThat(label.id(), is(12L));
        assertThat(label.name(), is("Label name"));
    }

    @Test
    public void testQuery() {
        final Page<Label> labels = new FpLabels(
            new FakeEndpoint(
                new FakeRoute(
                    '{' +
                        "  \"labels\": [" +
                        "    {" +
                        "      \"id\": 12," +
                        "      \"name\": \"Label name\"" +
                        "    }" +
                        "  ]," +
                        "  \"paging\": {" +
                        "    \"page\": 1," +
                        "    \"perPage\": 13," +
                        "    \"pageCount\": 2," +
                        "    \"totalCount\": 14" +
                        "  }" +
                        '}'
                )
            ),
            new FakeAccessToken("fake token")
        ).query(new LabelsCriteria());

        final Iterator<Label> iterator = labels.items().iterator();
        assertThat(iterator.hasNext(), is(true));
        final Label label = iterator.next();
        assertThat(label.id(), is(12L));
        assertThat(label.name(), is("Label name"));
        assertThat(iterator.hasNext(), is(false));

        final Paging paging = labels.paging();
        assertThat(paging.page(), is(1));
        assertThat(paging.perPage(), is(13));
        assertThat(paging.pageCount(), is(2));
        assertThat(paging.totalCount(), is(14));
    }
}
