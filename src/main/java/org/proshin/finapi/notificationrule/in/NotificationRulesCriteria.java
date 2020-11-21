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
package org.proshin.finapi.notificationrule.in;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.http.NameValuePair;
import org.proshin.finapi.notificationrule.TriggerEvent;
import org.proshin.finapi.primitives.pair.CommaSeparatedPair;
import org.proshin.finapi.primitives.pair.QueryParamEncodedPair;

public final class NotificationRulesCriteria implements Iterable<NameValuePair> {

    private final List<NameValuePair> pairs;

    public NotificationRulesCriteria() {
        this(new ArrayList<>());
    }

    public NotificationRulesCriteria(final List<NameValuePair> pairs) {
        this.pairs = pairs;
    }

    public NotificationRulesCriteria withIds(final Iterable<Long> ids) {
        this.pairs.add(new QueryParamEncodedPair(new CommaSeparatedPair<>("ids", ids)));
        return this;
    }

    public NotificationRulesCriteria withTriggerEvent(final TriggerEvent triggerEvent) {
        this.pairs.add(new QueryParamEncodedPair("triggerEvent", triggerEvent.name()));
        return this;
    }

    public NotificationRulesCriteria withDetails(final boolean includeDetails) {
        this.pairs.add(new QueryParamEncodedPair("includeDetails", includeDetails));
        return this;
    }

    @Nonnull
    @Override
    public Iterator<NameValuePair> iterator() {
        return this.pairs.iterator();
    }
}
