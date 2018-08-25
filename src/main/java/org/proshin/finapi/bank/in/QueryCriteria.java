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
package org.proshin.finapi.bank.in;

import java.util.List;
import org.apache.http.NameValuePair;
import org.proshin.finapi.bank.Bank;

/**
 * @todo #8 Find a way to generalize paging stuff for input criterias. It should a single (or multiple) general
 *  implementation that allows to get rid of copy-pasting for paging.
 */
public interface QueryCriteria {

    QueryCriteria withIds(Iterable<Long> ids);

    QueryCriteria withSearch(String search);

    QueryCriteria withSupporting(final boolean supporting);

    QueryCriteria withPinsAreVolatile(boolean pinsAreVolatile);

    QueryCriteria withSupportedDataSources(Iterable<Bank.DataSource> supportedDataSources);

    QueryCriteria withLocation(Iterable<String> location);

    QueryCriteria withTestBank(boolean testBank);

    List<NameValuePair> asPairs();
}
