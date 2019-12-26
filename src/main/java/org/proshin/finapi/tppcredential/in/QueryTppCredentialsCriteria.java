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
package org.proshin.finapi.tppcredential.in;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;

// @todo #240: Define all parameters supported by "Get all TPP credentials" endpoint
public class QueryTppCredentialsCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public QueryTppCredentialsCriteria() {
        this(new ArrayList<>());
    }

    public QueryTppCredentialsCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
