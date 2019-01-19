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
package org.proshin.finapi.primitives;

import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONArray;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IterableJsonArrayTest {

    @Rule
    public ExpectedException rule = ExpectedException.none();

    @Test
    public void testHasNext() {
        final Iterator<Integer> iterator = new IterableJsonArray<>(
            new JSONArray(new int[]{1}),
            JSONArray::getInt
        ).iterator();
        assertThat(iterator.hasNext(), is(true));
        iterator.next();
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void testNoSuchElementException() {
        final Iterator<Integer> iterator = new IterableJsonArray<>(
            new JSONArray(new int[]{1}),
            JSONArray::getInt
        ).iterator();

        rule.expect(NoSuchElementException.class);
        rule.expectMessage("Array has 1 items when you tried to get an item with index=1");
        iterator.next();// it should be fine
        iterator.next();// it must fail
    }
}
