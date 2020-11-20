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
package org.proshin.finapi.account.out;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

final class FpSepaRequestingResponseTest {

    @Test
    void test() {
        final SepaRequestingResponse response = new FpSepaRequestingResponse(new JSONObject('{' +
            "  \"successMessage\": \"Auftrag ausgeführt.\"," +
            "  \"warnMessage\": \"Es liegen Warnungen vor.\"," +
            "  \"paymentId\": 1," +
            "  \"challengeMessage\": \"Bitte geben Sie die TAN ein, die Sie per SMS erhalten.\"," +
            "  \"answerFieldLabel\": \"TAN-Nummer\"," +
            "  \"tanListNumber\": \"001\"," +
            "  \"opticalData\": \"11048813833205002812775114302C30315D\"," +
            "  \"photoTanMimeType\": \"image/svg+xml\"," +
            "  \"photoTanData\": \"some image data\"" +
            '}'));
        assertThat(response.successMessage()).isEqualTo(Optional.of("Auftrag ausgeführt."));
        assertThat(response.warnMessage()).isEqualTo(Optional.of("Es liegen Warnungen vor."));
        assertThat(response.paymentId()).isEqualTo(1L);
        assertThat(response.challengeMessage())
            .isEqualTo(Optional.of("Bitte geben Sie die TAN ein, die Sie per SMS erhalten."));
        assertThat(response.answerFieldLabel()).isEqualTo(Optional.of("TAN-Nummer"));
        assertThat(response.tanListNumber()).isEqualTo(Optional.of("001"));
        assertThat(response.opticalData()).isEqualTo(Optional.of("11048813833205002812775114302C30315D"));
        assertThat(response.photoTanMimeType()).isEqualTo(Optional.of("image/svg+xml"));
        assertThat(response.photoTanData()).isEqualTo(Optional.of("some image data"));
    }
}
