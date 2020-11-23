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
package org.proshin.finapi.mandator;

import org.cactoos.iterable.IterableOf;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.proshin.finapi.TestWithMockedEndpoint;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.mandator.in.UsersCriteria;
import org.proshin.finapi.primitives.OffsetDateTimeOf;

public final class FpMandatorTest extends TestWithMockedEndpoint {

    @Test
    public void testUsers() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/mandatorAdmin/getUserList")
                    .withMethod("GET")
                    .withHeader("Authorization", "Bearer admin-token")
                    .withQueryStringParameter("minRegistrationDate", "2019-01-01")
                    .withQueryStringParameter("maxRegistrationDate", "2019-01-02")
                    .withQueryStringParameter("minDeletionDate", "2019-01-03")
                    .withQueryStringParameter("maxDeletionDate", "2019-01-04")
                    .withQueryStringParameter("minLastActiveDate", "2019-01-05")
                    .withQueryStringParameter("maxLastActiveDate", "2019-01-06")
                    .withQueryStringParameter("includeMonthlyStats", "true")
                    .withQueryStringParameter("monthlyStatsStartDate", "2019-01-07")
                    .withQueryStringParameter("monthlyStatsEndDate", "2019-01-08")
                    .withQueryStringParameter("minBankConnectionCountInMonthlyStats", "1")
                    .withQueryStringParameter("isDeleted", "true")
                    .withQueryStringParameter("isLocked", "false")
                    .withQueryStringParameter("page", "3")
                    .withQueryStringParameter("perPage", "21")
                    .withQueryStringParameter("order", "id,asc", "date,dest")
            )
            .respond(
                HttpResponse.response("{\"users\":[{}]}")
            );
        new FpMandator(
            this.endpoint(),
            new FakeAccessToken("admin-token")
        ).users(
            new UsersCriteria()
                .withMinRegistrationDate(new OffsetDateTimeOf("2019-01-01 00:00:00.000").get())
                .withMaxRegistrationDate(new OffsetDateTimeOf("2019-01-02 00:00:00.000").get())
                .withMinDeletionDate(new OffsetDateTimeOf("2019-01-03 00:00:00.000").get())
                .withMaxDeletionDate(new OffsetDateTimeOf("2019-01-04 00:00:00.000").get())
                .withMinLastActiveDate(new OffsetDateTimeOf("2019-01-05 00:00:00.000").get())
                .withMaxLastActiveDate(new OffsetDateTimeOf("2019-01-06 00:00:00.000").get())
                .withMonthlyStats()
                .withMonthlyStatsStartDate(new OffsetDateTimeOf("2019-01-07 00:00:00.000").get())
                .withMonthlyStatsEndDate(new OffsetDateTimeOf("2019-01-08 00:00:00.000").get())
                .withMinBankConnectionCountInMonthlyStats(1)
                .withIsDeleted(true)
                .withIsLocked(false)
                .withPage(3, 21)
                .orderBy("id,asc", "date,dest")
        ).items().iterator().next();
    }

    @Test
    public void testDeleteUsers() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/mandatorAdmin/deleteUsers")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer admin-token")
                    .withBody(new JsonBody('{' +
                        "  \"userIds\": [" +
                        "    \"first_user\"," +
                        "    \"second_user\"," +
                        "    \"third_user\"," +
                        "    \"fourth_user\"" +
                        "  ]" +
                        '}'))
            )
            .respond(
                HttpResponse.response("{}")
            );
        new FpMandator(
            this.endpoint(),
            new FakeAccessToken("admin-token")
        ).deleteUsers(new IterableOf<>("first_user", "second_user", "third_user", "fourth_user"));
    }

    @Test
    public void testChangeClientCredentials() {
        this.server()
            .when(
                HttpRequest.request("/api/v1/mandatorAdmin/changeClientCredentials")
                    .withMethod("POST")
                    .withHeader("Authorization", "Bearer admin-token")
                    .withBody(new JsonBody('{' +
                        "  \"clientId\": \"client ID\"," +
                        "  \"oldClientSecret\": \"old secret\"," +
                        "  \"newClientSecret\": \"new secret\"" +
                        '}'))
            )
            .respond(
                HttpResponse.response("")
            );
        new FpMandator(
            this.endpoint(),
            new FakeAccessToken("admin-token")
        ).changeClientCredentials("client ID", "old secret", "new secret");
    }
}
