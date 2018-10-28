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
package org.proshin.finapi.endpoint;

import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.cactoos.iterable.IterableOf;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.accesstoken.AccessToken;

public interface Endpoint {

    default String get(final String path, final AccessToken token, final NameValuePair... parameters) {
        return this.get(path, token, new IterableOf<>(parameters));
    }

    String get(String path, AccessToken token, Iterable<NameValuePair> parameters);

    default String delete(final String path, final AccessToken token, final NameValuePair... parameters) {
        return this.delete(path, token, new IterableOf<>(parameters));
    }

    String delete(String path, AccessToken token, Iterable<NameValuePair> parameters);

    HttpPost post(String path);

    String post(String path, HttpEntity entity, int expected);

    HttpPost post(String path, AccessToken token);

    String post(String path, AccessToken token, int expected);

    String post(String path, AccessToken token, HttpEntity entity, int expected);

    default String post(final String path, final AccessToken token, final Jsonable body) {
        return this.post(
            path,
            token,
            new StringEntity(
                body.asString(),
                ContentType.create("application/json", StandardCharsets.UTF_8)
            ),
            200
        );
    }

    default String post(final String path, final AccessToken token, final Jsonable body, final int expected) {
        return this.post(
            path,
            token,
            new StringEntity(
                body.asString(),
                ContentType.create("application/json", StandardCharsets.UTF_8)
            ),
            expected
        );
    }

    String patch(String path, AccessToken token, HttpEntity entity, int expected);

    String patch(final String path, final AccessToken token, final Jsonable body);
}
