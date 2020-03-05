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
package org.proshin.finapi.tppcertificate.in;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.proshin.finapi.primitives.LocalDateOf;
import org.proshin.finapi.tppcertificate.CertificateType;

public class CreateTppCertificateParametersTest {

    @Test
    public void test() {
        assertThat(
            new CreateTppCertificateParameters(
                CertificateType.QWAC,
                "public key",
                "private key"
            )
                .withLabel("custom label")
                .withPassphrase("custom passphrase")
                .withValidFromDate(new LocalDateOf("2019-11-29").get())
                .withValidUntilDate(new LocalDateOf("2020-12-31").get())
                .asJson().toString())
            .isEqualTo('{' +
                "\"privateKey\":\"private key\"," +
                "\"validFromDate\":\"2019-11-29\"," +
                "\"passphrase\":\"custom passphrase\"," +
                "\"publicKey\":\"public key\"," +
                "\"label\":\"custom label\"," +
                "\"validUntilDate\":\"2020-12-31\"," +
                "\"type\":\"QWAC\"}"
            );
    }
}
