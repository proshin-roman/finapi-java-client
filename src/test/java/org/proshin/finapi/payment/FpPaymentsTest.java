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
import java.util.Iterator;
import java.util.Optional;
import org.cactoos.iterable.IterableOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.fake.FakeEndpoint;
import org.proshin.finapi.fake.FakeRoute;
import org.proshin.finapi.fake.matcher.path.ExactPathMatcher;
import org.proshin.finapi.payment.in.FpQueryCriteria;
import org.proshin.finapi.payment.out.Status;
import org.proshin.finapi.payment.out.Type;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.primitives.paging.Paging;

public class FpPaymentsTest {

    @Test
    public void testQuery() {
        final Page<Payment> page = new FpPayments(
            new FakeEndpoint(
                new FakeRoute(
                    new ExactPathMatcher("/api/v1/payments"),
                    '{' +
                        "  \"payments\": [" +
                        "    {" +
                        "      \"id\": 1," +
                        "      \"accountId\": 2," +
                        "      \"requestDate\": \"2019-01-01 00:00:00.000\"," +
                        "      \"executionDate\": \"2019-02-02 00:00:00.000\"," +
                        "      \"type\": \"MONEY_TRANSFER\"," +
                        "      \"status\": \"PENDING\"," +
                        "      \"bankMessage\": \"string\"," +
                        "      \"amount\": \"99.99\"," +
                        "      \"orderCount\": 1" +
                        "    }" +
                        "  ]," +
                        "  \"paging\": {" +
                        "    \"page\": 1," +
                        "    \"perPage\": 20," +
                        "    \"pageCount\": 10," +
                        "    \"totalCount\": 200" +
                        "  }" +
                        '}'
                )
            ),
            new FakeAccessToken("random token")
        ).query(new FpQueryCriteria().withIds(new IterableOf<>(1L)));

        final Iterable<Payment> payments = page.items();
        final Iterator<Payment> iterator = payments.iterator();
        assertThat(iterator.hasNext(), is(true));
        final Payment payment = iterator.next();
        assertThat(iterator.hasNext(), is(false));

        assertThat(payment.id(), is(1L));
        assertThat(payment.accountId(), is(2L));
        assertThat(payment.requestDate(), is(new OffsetDateTimeOf("2019-01-01 00:00:00.000").get()));
        assertThat(payment.executionDate(), is(Optional.of(new OffsetDateTimeOf("2019-02-02 00:00:00.000").get())));
        assertThat(payment.type(), is(Type.MONEY_TRANSFER));
        assertThat(payment.status(), is(Status.PENDING));
        assertThat(payment.bankMessage(), is(Optional.of("string")));
        assertThat(payment.amount(), is(new BigDecimal("99.99")));
        assertThat(payment.orderCount(), is(1));

        final Paging paging = page.paging();
        assertThat(paging.page(), is(1));
        assertThat(paging.perPage(), is(20));
        assertThat(paging.pageCount(), is(10));
        assertThat(paging.totalCount(), is(200));
    }
}
