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
package org.proshin.finapi.bank;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.cactoos.list.ListOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import static org.proshin.finapi.bank.Bank.DataSource.FINTS_SERVER;
import static org.proshin.finapi.bank.Bank.DataSource.WEB_SCRAPER;
import org.proshin.finapi.bank.in.FpQueryCriteria;
import org.proshin.finapi.fake.FakeAccessToken;
import org.proshin.finapi.fake.FakeEndpoint;
import org.proshin.finapi.fake.FakeRoute;
import org.proshin.finapi.primitives.paging.Page;
import org.proshin.finapi.primitives.paging.Paging;

public final class BanksTest {

    @Test
    public void testThatOneReturnsValidBank() {
        final Bank bank = new FpBanks(
            new FakeEndpoint(
                new FakeRoute(
                    String.join("",
                        "{",
                        " \"id\": 123,",
                        "\"name\": \"Bank name\",",
                        "\"loginHint\": null,",
                        "\"bic\": null,",
                        "\"blz\": \"00000000\",",
                        "\"blzs\": [\"00000000\"],",
                        "\"loginFieldUserId\": \"User ID field label\",",
                        "\"loginFieldCustomerId\": \"Customer ID field label\",",
                        "\"loginFieldPin\": \"PIN\",",
                        "\"isSupported\": true,",
                        "\"supportedDataSources\": [\"FINTS_SERVER\", \"WEB_SCRAPER\"],",
                        "\"pinsAreVolatile\": false,",
                        "\"location\": \"DE\",",
                        "\"city\": null,",
                        "\"isTestBank\": true,",
                        "\"popularity\": 2,",
                        "\"health\": 100,",
                        "\"lastCommunicationAttempt\": null,",
                        "\"lastSuccessfulCommunication\": \"2018-08-03 11:22:33.444\"",
                        "}"
                    )
                )
            ),
            new FakeAccessToken("access token")
        ).one(123L);

        assertThat(bank.id(), is(123L));
        assertThat(bank.name(), is("Bank name"));
        assertThat(bank.loginHint(), is(Optional.empty()));
        assertThat(bank.bic(), is(Optional.empty()));
        assertThat(bank.blz(), is("00000000"));
        assertThat(bank.loginFieldUserId(), is(Optional.of("User ID field label")));
        assertThat(bank.loginFieldCustomerId(), is(Optional.of("Customer ID field label")));
        assertThat(bank.loginFieldPin(), is(Optional.of("PIN")));
        assertThat(bank.isSupported(), is(true));
        assertThat(bank.supportedDataSources(), contains(FINTS_SERVER, WEB_SCRAPER));
        assertThat(bank.pinsAreVolatile(), is(false));
        assertThat(bank.location(), is(Optional.of("DE")));
        assertThat(bank.city(), is(Optional.empty()));
        assertThat(bank.isTestBank(), is(true));
        assertThat(bank.popularity(), is(2));
        assertThat(bank.health(), is(100));
        assertThat(bank.lastCommunicationAttempt(), is(Optional.empty()));
        assertThat(
            bank.lastSuccessfulCommunication().map(OffsetDateTime::toString),
            is(Optional.of("2018-08-03T11:22:33.444+02:00"))
        );
    }

    @Test
    public void testThatSearchReturnsValidBanks() {
        final Page<Bank> banks = new FpBanks(
            new FakeEndpoint(
                new FakeRoute(
                    String.join("",
                        "{",
                        "  banks: [{",
                        "    \"id\": 123,",
                        "    \"name\": \"Bank name\",",
                        "    \"loginHint\": null,",
                        "    \"bic\": null,",
                        "    \"blz\": \"00000000\",",
                        "    \"blzs\": [\"00000000\"],",
                        "    \"loginFieldUserId\": \"User ID field label\",",
                        "    \"loginFieldCustomerId\": \"Customer ID field label\",",
                        "    \"loginFieldPin\": \"PIN\",",
                        "    \"isSupported\": true,",
                        "    \"supportedDataSources\": [\"FINTS_SERVER\", \"WEB_SCRAPER\"],",
                        "    \"pinsAreVolatile\": false,",
                        "    \"location\": \"DE\",",
                        "    \"city\": null,",
                        "    \"isTestBank\": true,",
                        "    \"popularity\": 2,",
                        "    \"health\": 100,",
                        "    \"lastCommunicationAttempt\": null,",
                        "    \"lastSuccessfulCommunication\": \"2018-08-03 11:22:33.444\"",
                        "  }],",
                        "  \"paging\": {",
                        "    \"page\": 1,",
                        "    \"perPage\": 500,",
                        "    \"pageCount\": 1,",
                        "    \"totalCount\": 1",
                        "  }",
                        "}"
                    )
                )
            ),
            new FakeAccessToken("access token")
        ).search(new FpQueryCriteria());

        assertThat(new ListOf<>(banks), hasSize(1));

        final Bank bank = banks.items().iterator().next();
        assertThat(bank.id(), is(123L));
        assertThat(bank.name(), is("Bank name"));
        assertThat(bank.loginHint(), is(Optional.empty()));
        assertThat(bank.bic(), is(Optional.empty()));
        assertThat(bank.blz(), is("00000000"));
        assertThat(bank.loginFieldUserId(), is(Optional.of("User ID field label")));
        assertThat(bank.loginFieldCustomerId(), is(Optional.of("Customer ID field label")));
        assertThat(bank.loginFieldPin(), is(Optional.of("PIN")));
        assertThat(bank.isSupported(), is(true));
        assertThat(bank.supportedDataSources(), contains(FINTS_SERVER, WEB_SCRAPER));
        assertThat(bank.pinsAreVolatile(), is(false));
        assertThat(bank.location(), is(Optional.of("DE")));
        assertThat(bank.city(), is(Optional.empty()));
        assertThat(bank.isTestBank(), is(true));
        assertThat(bank.popularity(), is(2));
        assertThat(bank.health(), is(100));
        assertThat(bank.lastCommunicationAttempt(), is(Optional.empty()));
        assertThat(
            bank.lastSuccessfulCommunication().map(OffsetDateTime::toString),
            is(Optional.of("2018-08-03T11:22:33.444+02:00"))
        );

        final Paging paging = banks.paging();
        assertThat(paging.page(), is(1));
        assertThat(paging.perPage(), is(500));
        assertThat(paging.pageCount(), is(1));
        assertThat(paging.totalCount(), is(1));
    }
}
