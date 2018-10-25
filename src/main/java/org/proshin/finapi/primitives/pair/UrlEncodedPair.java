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
package org.proshin.finapi.primitives.pair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public final class UrlEncodedPair implements NameValuePair {

    private final NameValuePair origin;

    public UrlEncodedPair(final String name, final Object value) {
        this(name, value.toString());
    }

    public UrlEncodedPair(final String name, final String value) {
        this(new BasicNameValuePair(name, value));
    }

    public UrlEncodedPair(final NameValuePair origin) {
        this.origin = origin;
    }

    @Override
    public String getName() {
        return this.origin.getName();
    }

    @Override
    public String getValue() {
        try {
            return URLEncoder.encode(this.origin.getValue(), StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
