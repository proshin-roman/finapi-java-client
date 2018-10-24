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

import org.proshin.finapi.bank.in.QueryCriteria;
import org.proshin.finapi.primitives.paging.Page;

/**
 * @todo #8:30min Fix state of Banks services: make sure all cases/services are implemented and fixed using unit tests.
 */
public interface Banks {

    Bank one(Long id);

    Page<Bank> search(QueryCriteria criteria);
}
