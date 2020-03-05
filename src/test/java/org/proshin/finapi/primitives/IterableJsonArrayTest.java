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
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONArray;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class IterableJsonArrayTest {

    @Test
    public void testHasNext() {
        final Iterator<Integer> iterator = new IterableJsonArray<>(
            new JSONArray(new int[]{1}),
            JSONArray::getInt
        ).iterator();
        assertThat(iterator.hasNext()).isTrue();
        iterator.next();
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void testNoSuchElementException() {
        final Iterator<Integer> iterator = new IterableJsonArray<>(
            new JSONArray(new int[]{1}),
            JSONArray::getInt
        ).iterator();

        iterator.next();// it should be fine
        final Exception exception =
            assertThrows(NoSuchElementException.class, iterator::next);
        assertThat(exception.getMessage())
            .isEqualTo(("Array has 1 items when you tried to get an item with index=1"));
    }
}
