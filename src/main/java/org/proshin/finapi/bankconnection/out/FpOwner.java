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

import java.time.LocalDate;
import java.util.Optional;
import org.json.JSONObject;
import org.proshin.finapi.primitives.LocalDateOf;
import org.proshin.finapi.primitives.optional.OptionalOf;

public final class FpOwner implements Owner {

    private final JSONObject origin;

    public FpOwner(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public Optional<String> firstName() {
        return new OptionalOf<>(
            this.origin,
            "firstName",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<String> lastName() {
        return new OptionalOf<>(
            this.origin,
            "lastName",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<String> salutation() {
        return new OptionalOf<>(
            this.origin,
            "salutation",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<String> title() {
        return new OptionalOf<>(
            this.origin,
            "title",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<String> email() {
        return new OptionalOf<>(
            this.origin,
            "email",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<LocalDate> dateOfBirth() {
        return new OptionalOf<>(
            this.origin,
            "dateOfBirth",
            (jsonObject, key) -> new LocalDateOf(jsonObject.getString(key)).get()
        ).get();
    }

    @Override
    public Optional<String> postCode() {
        return new OptionalOf<>(
            this.origin,
            "postCode",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<String> country() {
        return new OptionalOf<>(
            this.origin,
            "country",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<String> city() {
        return new OptionalOf<>(
            this.origin,
            "city",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<String> street() {
        return new OptionalOf<>(
            this.origin,
            "street",
            JSONObject::getString
        ).get();
    }

    @Override
    public Optional<String> houseNumber() {
        return new OptionalOf<>(
            this.origin,
            "houseNumber",
            JSONObject::getString
        ).get();
    }
}
