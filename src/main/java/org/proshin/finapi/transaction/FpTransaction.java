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
package org.proshin.finapi.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.category.Category;
import org.proshin.finapi.category.FpCategory;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.label.FpLabel;
import org.proshin.finapi.label.Label;
import org.proshin.finapi.primitives.IterableJsonArray;
import org.proshin.finapi.primitives.OffsetDateTimeOf;
import org.proshin.finapi.primitives.optional.OptionalLongOf;
import org.proshin.finapi.primitives.optional.OptionalObjectOf;
import org.proshin.finapi.primitives.optional.OptionalStringOf;
import org.proshin.finapi.transaction.in.EditTransactionParameters;
import org.proshin.finapi.transaction.in.SplitTransactionParameters;
import org.proshin.finapi.transaction.out.Counterpart;
import org.proshin.finapi.transaction.out.FpCounterpart;
import org.proshin.finapi.transaction.out.FpPayPalData;
import org.proshin.finapi.transaction.out.FpType;
import org.proshin.finapi.transaction.out.PayPalData;
import org.proshin.finapi.transaction.out.Type;

public final class FpTransaction implements Transaction {

    private final Endpoint endpoint;
    private final AccessToken token;
    private final JSONObject origin;
    private final String url;

    public FpTransaction(final Endpoint endpoint, final AccessToken token, final JSONObject origin,
        final String url) {
        this.endpoint = endpoint;
        this.token = token;
        this.origin = origin;
        this.url = url;
    }

    @Override
    public Long id() {
        return this.origin.getLong("id");
    }

    @Override
    public Optional<Long> parent() {
        return new OptionalLongOf(this.origin, "parentId").get();
    }

    @Override
    public Long account() {
        return this.origin.getLong("accountId");
    }

    @Override
    public OffsetDateTime valueDate() {
        return new OffsetDateTimeOf(this.origin.getString("valueDate")).get();
    }

    @Override
    public OffsetDateTime bankBookingDate() {
        return new OffsetDateTimeOf(this.origin.getString("bankBookingDate")).get();
    }

    @Override
    public OffsetDateTime finapiBookingDate() {
        return new OffsetDateTimeOf(this.origin.getString("finapiBookingDate")).get();
    }

    @Override
    public BigDecimal amount() {
        return this.origin.getBigDecimal("amount");
    }

    @Override
    public Optional<String> purpose() {
        return new OptionalStringOf(this.origin, "purpose").get();
    }

    @Override
    public Counterpart counterpart() {
        return new FpCounterpart(this.origin);
    }

    @Override
    public Type type() {
        return new FpType(this.origin);
    }

    @Override
    public Optional<String> sepaPurposeCode() {
        return new OptionalStringOf(this.origin, "sepaPurposeCode").get();
    }

    @Override
    public Optional<String> primanota() {
        return new OptionalStringOf(this.origin, "primanota").get();
    }

    @Override
    public Optional<Category> category() {
        return new OptionalObjectOf(this.origin, "category").get()
            .map(json -> new FpCategory(this.endpoint, this.token, json));
    }

    @Override
    public Iterable<Label> labels() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("labels"),
            (array, index) -> new FpLabel(this.endpoint, this.token, array.getJSONObject(index))
        );
    }

    @Override
    public boolean isPotentialDuplicate() {
        return this.origin.getBoolean("isPotentialDuplicate");
    }

    @Override
    public boolean isAdjustingEntry() {
        return this.origin.getBoolean("isAdjustingEntry");
    }

    @Override
    public boolean isNew() {
        return this.origin.getBoolean("isNew");
    }

    @Override
    public OffsetDateTime importDate() {
        return new OffsetDateTimeOf(this.origin.getString("importDate")).get();
    }

    @Override
    public Iterable<Long> children() {
        return new IterableJsonArray<>(
            this.origin.getJSONArray("children"),
            JSONArray::getLong
        );
    }

    @Override
    public Optional<PayPalData> payPalData() {
        return new OptionalObjectOf(this.origin, "paypalData").get()
            .map(FpPayPalData::new);
    }

    @Override
    public Optional<String> endToEndReference() {
        return new OptionalStringOf(this.origin, "endToEndReference").get();
    }

    @Override
    public Transaction split(final SplitTransactionParameters parameters) {
        return new FpTransaction(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.post(
                    String.format("%s/%d/split", this.url, this.id()),
                    this.token,
                    parameters
                )
            ),
            this.url
        );
    }

    @Override
    public Transaction restore() {
        return new FpTransaction(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.post(
                    String.format(
                        "%s/%d/restore",
                        this.url,
                        this.id()
                    ),
                    this.token
                )
            ),
            this.url
        );
    }

    @Override
    public Transaction edit(final EditTransactionParameters parameters) {
        return new FpTransaction(
            this.endpoint,
            this.token,
            new JSONObject(
                this.endpoint.patch(
                    this.url + this.id(),
                    this.token,
                    parameters
                )
            ),
            this.url
        );
    }

    @Override
    public void delete() {
        this.endpoint.delete(this.url + this.id(), this.token);
    }
}
