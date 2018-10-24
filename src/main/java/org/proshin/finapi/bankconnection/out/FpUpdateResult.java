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
package org.proshin.finapi.bankconnection.out;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;

public final class FpUpdateResult implements UpdateResult {

    private final JSONObject origin;

    public FpUpdateResult(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Result result() {
        return Result.valueOf(this.origin.getString("result"));
    }

    @Override
    public Optional<String> errorMessage() {
        return new OptionalStringOf(this.origin, "errorMessage").get();
    }

    @Override
    public Optional<ErrorType> errorType() {
        return new OptionalStringOf(this.origin, "errorType").get()
                   .map(ErrorType::valueOf);
    }

    @Override
    public OffsetDateTime timestamp() {
        return new OffsetDateTimeOf(this.origin.getString("timestamp")).get();
    }
}
