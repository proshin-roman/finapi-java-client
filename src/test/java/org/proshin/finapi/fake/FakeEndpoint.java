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
package org.proshin.finapi.fake;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.cactoos.list.ListOf;
import org.proshin.finapi.Jsonable;
import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.endpoint.Endpoint;

public final class FakeEndpoint implements Endpoint {

    private final List<FakeRoute> routes;

    public FakeEndpoint(final FakeRoute... routes) {
        this(new ListOf<>(routes));
    }

    public FakeEndpoint(final List<FakeRoute> routes) {
        this.routes = routes;
    }

    @Override
    public String get(final String path, final AccessToken token, final Iterable<NameValuePair> parameters) {
        for (final FakeRoute route : this.routes) {
            if (route.matches(path)) {
                return route.response();
            }
        }
        throw new AssertionError(String.format("No routes found for path '%s'", path));
    }

    @Override
    public String delete(final String path, final AccessToken token, final Iterable<NameValuePair> parameters) {
        for (final FakeRoute route : this.routes) {
            if (route.matches(path)) {
                return route.response();
            }
        }
        throw new AssertionError(String.format("No routes found for path '%s'", path));
    }

    @Override
    public HttpPost post(final String path) {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public String post(final String path, final HttpEntity entity, final int expected) {
        for (final FakeRoute route : this.routes) {
            if (route.matches(path)) {
                return route.response();
            }
        }
        throw new RuntimeException(
            String.format("No routes found for path '%s'", path));
    }

    @Override
    public HttpPost post(final String path, final AccessToken token) {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public String post(final String path, final AccessToken token, final int expected) {
        try {
            return this.post(path, token, new StringEntity(""), expected);
        } catch (final UnsupportedEncodingException e) {
            throw new AssertionError("Couldn't trigger a POST request", e);
        }
    }

    @Override
    public String post(final String path, final AccessToken token, final HttpEntity entity, final int expected) {
        for (final FakeRoute route : this.routes) {
            if (route.matches(token, path)) {
                return route.response();
            }
        }
        throw new AssertionError(String.format("No routes found for path '%s'", path));
    }

    @Override
    public String patch(final String path, final AccessToken token, final HttpEntity entity, final int expected) {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public String patch(final String path, final AccessToken token, final Jsonable body) {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
