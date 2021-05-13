/*
 * Copyright 2020 Roman Proshin
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
package org.proshin.finapi.account;

import javax.annotation.Nonnull;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

final class TypeTest {

    @ParameterizedTest
    @CsvSource({"1, Checking", "2, Savings", "3, CreditCard", "4, Security", "5, Loan", "6, Pocket", "7, Membership",
        "8, Bausparen"
    })
    void testCorrectTypes(final int typeCode, @Nonnull final Type expectedType) {
        assertThat(new TypeOf(typeCode).get()).isEqualTo(expectedType);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 9, 10, 100})
    void testIllegalCode(final int typeCode) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TypeOf(typeCode).get());
    }
}
