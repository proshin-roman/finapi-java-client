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
package org.proshin.finapi.notificationrule.in;

import org.cactoos.iterable.IterableOfLongs;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.proshin.finapi.notificationrule.TriggerEvent;
import org.proshin.finapi.notificationrule.in.params.BankLoginErrorParams;

public class CreatingParametersTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testIncompatibleEventAndParams() {
        this.expectedException.expect(RuntimeException.class);
        this.expectedException.expectMessage("Incompatible params and triggerEvent arguments");

        new CreatingParameters(TriggerEvent.NEW_TRANSACTIONS)
            .withParams(new BankLoginErrorParams(new IterableOfLongs(1L)));
    }
}
