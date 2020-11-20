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
package org.proshin.finapi.transaction.in;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.NameValuePair;
import org.proshin.finapi.primitives.StringOf;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.UrlEncodedPair;

public final class DeleteTransactionsCriteria implements Iterable<NameValuePair> {
    private final List<NameValuePair> pairs;

    public DeleteTransactionsCriteria() {
        this(new ArrayList<>());
    }

    public DeleteTransactionsCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public DeleteTransactionsCriteria withMaxDeletionDate(final LocalDate maxDeletionDate) {
        this.pairs.add(new UrlEncodedPair("maxDeletionDate", new StringOf(maxDeletionDate)));
        return this;
    }

    public DeleteTransactionsCriteria withMinDeletionDate(final LocalDate minDeletionDate) {
        this.pairs.add(new UrlEncodedPair("minDeletionDate", new StringOf(minDeletionDate)));
        return this;
    }

    public DeleteTransactionsCriteria withAccounts(final Iterable<Long> accounts) {
        this.pairs.add(new UrlEncodedPair(new CommaSeparatedPair<>("accountIds", accounts)));
        return this;
    }

    public DeleteTransactionsCriteria withUnsafeMode() {
        this.pairs.add(new UrlEncodedPair("safeMode", false));
        return this;
    }

    public DeleteTransactionsCriteria withRememberingDeletion() {
        this.pairs.add(new UrlEncodedPair("rememberDeletion", true));
        return this;
    }

    @Nonnull
    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
