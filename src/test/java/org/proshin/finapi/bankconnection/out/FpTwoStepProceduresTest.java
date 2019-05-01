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

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FpTwoStepProceduresTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatExceptionIsThrownIfDefaultDoesNotMatchAnyProcedure() {
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

        this.expectedException.expect(IllegalStateException.class);
        this.expectedException.expectMessage(
            "List of all two-step procedures doesn't contain an item matches ID of the default one");
        twoStepProcedures.defaultOne();
    }
}
