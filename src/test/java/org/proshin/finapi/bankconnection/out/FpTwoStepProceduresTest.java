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
package org.proshin.finapi.bankconnection.out;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

final class FpTwoStepProceduresTest {

    @Test
    void testThatExceptionIsThrownIfDefaultDoesNotMatchAnyProcedure() {
        final TwoStepProcedures twoStepProcedures = new FpTwoStepProcedures(
            new JSONObject('{' +
                "  \"defaultTwoStepProcedureId\": \"000\"," +
                "  \"twoStepProcedures\": [" +
                "    {" +
                "      \"procedureId\": \"955\"," +
                "      \"procedureName\": \"mobileTAN\"," +
                "      \"procedureChallengeType\": \"TEXT\"," +
                "      \"implicitExecute\": true" +
                "    }" +
                "  ]" +
                '}')
        );

        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(twoStepProcedures::defaultOne)
            .withMessage("List of all two-step procedures doesn't contain an item matches ID of the default one");
    }
}
