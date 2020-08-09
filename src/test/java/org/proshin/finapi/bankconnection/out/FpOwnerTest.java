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

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public final class FpOwnerTest {
    @Test
    public void test() {
        final Owner owner = new FpOwner(
            new JSONObject('{' +
                "  \"firstName\": \"Max\"," +
                "  \"lastName\": \"Mustermann\"," +
                "  \"salutation\": \"Herr\"," +
                "  \"title\": \"Dr.\"," +
                "  \"email\": \"email@localhost.de\"," +
                "  \"dateOfBirth\": \"1980-01-01\"," +
                "  \"postCode\": \"80000\"," +
                "  \"country\": \"Deutschland\"," +
                "  \"city\": \"München\"," +
                "  \"street\": \"Musterstraße\"," +
                "  \"houseNumber\": \"99\"" +
                '}')
        );
        assertThat(owner.firstName()).isEqualTo(Optional.of("Max"));
        assertThat(owner.lastName()).isEqualTo(Optional.of("Mustermann"));
        assertThat(owner.salutation()).isEqualTo(Optional.of("Herr"));
        assertThat(owner.title()).isEqualTo(Optional.of("Dr."));
        assertThat(owner.email()).isEqualTo(Optional.of("email@localhost.de"));
        assertThat(owner.dateOfBirth()).isEqualTo(Optional.of(LocalDate.of(1980, 1, 1)));
        assertThat(owner.postCode()).isEqualTo(Optional.of("80000"));
        assertThat(owner.country()).isEqualTo(Optional.of("Deutschland"));
        assertThat(owner.city()).isEqualTo(Optional.of("München"));
        assertThat(owner.street()).isEqualTo(Optional.of("Musterstraße"));
        assertThat(owner.houseNumber()).isEqualTo(Optional.of("99"));
    }
}
