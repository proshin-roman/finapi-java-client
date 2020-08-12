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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public final class BigDecimalOfTest {

    @Test
    public void testJsonDouble() {
        assertThat(new BigDecimalOf(new JSONObject().put("key", -12.34), "key").get())
            .isEqualTo(new BigDecimal("-12.34"));
    }

    @Test
    public void testJsonInt() {
        assertThat(new BigDecimalOf(new JSONObject().put("key", -12), "key").get())
            .isEqualTo(new BigDecimal("-12"));
    }

    @Test
    public void testJsonString() {
        assertThat(new BigDecimalOf(new JSONObject().put("key", "-12.34"), "key").get())
            .isEqualTo(new BigDecimal("-12.34"));
    }

    @Test
    public void testNullValue() {
        assertThatThrownBy(() -> new BigDecimalOf(new JSONObject(), "key").get())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Field 'key' cannot be null");
    }

    @Test
    public void testInvalidValue() {
        assertThatThrownBy(() -> new BigDecimalOf(new JSONObject().put("key", "abc"), "key").get())
            .isInstanceOf(JSONException.class)
            .hasMessage("JSONObject[\"key\"] is not a number.");
    }
}
