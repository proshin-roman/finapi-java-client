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
package org.proshin.finapi.primitives;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public final class StringOfTest {

    @Test
    public void testStringOfOffsetDateTime() {
        assertThat(new StringOf(OffsetDateTime.of(2018, 10, 25, 1, 2, 3, 4, ZoneOffset.UTC)).toString())
            .isEqualTo("2018-10-25");
    }

    @Test
    public void testStringOfBigDecimal() {
        assertThat(new StringOf(new BigDecimal("123456.012")).toString())
            .isEqualTo("123456.01");
    }
}
