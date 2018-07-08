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
package org.proshin.finapi.user;

import java.util.Optional;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;
import org.json.JSONObject;
import org.proshin.finapi.User;

public final class FpUser implements User {

    private final UncheckedScalar<JSONObject> origin;

    public FpUser(final Scalar<JSONObject> origin) {
        this(new UncheckedScalar<>(origin));
    }

    public FpUser(final UncheckedScalar<JSONObject> origin) {
        this.origin = origin;
    }

    @Override
    public String id() {
        return this.origin.value().getString("id");
    }

    @Override
    public String password() {
        return this.origin.value().getString("password");
    }

    @Override
    public Optional<String> email() {
        final String name = "email";
        if (this.origin.value().isNull(name)) {
            return Optional.empty();
        } else {
            return Optional.of(this.origin.value().getString(name));
        }
    }

    @Override
    public Optional<String> phone() {
        final String name = "phone";
        if (this.origin.value().isNull(name)) {
            return Optional.empty();
        } else {
            return Optional.of(this.origin.value().getString(name));
        }
    }

    @Override
    public boolean isAutoUpdateEnabled() {
        return this.origin.value().getBoolean("isAutoUpdateEnabled");
    }
}
