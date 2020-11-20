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
package org.proshin.finapi.payment;

import java.math.BigDecimal;
import org.cactoos.iterable.IterableOfLongs;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.payment.in.FpQueryCriteria;
import org.proshin.finapi.primitives.paging.Page;

final class FpPaymentsTest extends TestWithMockedEndpoint {

    @Test
    void testQuery() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/payments")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer random token")
                    .withQueryStringParameter("ids", "1,2,3")
                    .withQueryStringParameter("accountIds", "2,3,4")
                    .withQueryStringParameter("minAmount", "-11.23")
                    .withQueryStringParameter("maxAmount", "99.99")
                    .withQueryStringParameter("page", "2")
                    .withQueryStringParameter("perPage", "34")
                    .withQueryStringParameter("order", "id,asc", "name,desc")
            )
            .respond(
                HttpResponse.response("{}")
            );
        final Page<Payment> page = new FpPayments(
            this.endpoint(),
            new FakeAccessToken("random token")
        ).query(
            new FpQueryCriteria()
                .withIds(new IterableOfLongs(1L, 2L, 3L))
                .withAccountIds(new IterableOfLongs(2L, 3L, 4L))
                .withMinAmount(new BigDecimal("-11.23"))
                .withMaxAmount(new BigDecimal("99.99"))
                .withPage(2, 34)
                .orderBy("id,asc", "name,desc")
        );
    }
}
