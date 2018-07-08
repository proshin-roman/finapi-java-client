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
package org.proshin.finapi.exception;

import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

public final class UnexpectedStatusCode extends RuntimeException {
    public UnexpectedStatusCode(final int expected, final int actual) {
        super(
            new UncheckedText(
                new FormattedText(
                    "Server returns code %d instead of %d",
                    actual, expected
                )
            ).asString()
        );
    }
}
