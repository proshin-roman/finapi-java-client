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

import org.proshin.finapi.accesstoken.AccessToken;
import org.proshin.finapi.fake.matcher.path.AnyPathMatcher;
import org.proshin.finapi.fake.matcher.path.PathMatcher;
import org.proshin.finapi.fake.matcher.token.AnyTokenMatcher;
import org.proshin.finapi.fake.matcher.token.TokenMatcher;

public final class FakeRoute {
    private final PathMatcher pathMatcher;
    private final TokenMatcher tokenMatcher;
    private final String response;

    public FakeRoute() {
        this("");
    }

    public FakeRoute(final String response) {
        this(new AnyPathMatcher(), new AnyTokenMatcher(), response);
    }

    public FakeRoute(final PathMatcher pathMatcher, final String response) {
        this(pathMatcher, new AnyTokenMatcher(), response);
    }

    public FakeRoute(final PathMatcher pathMatcher, final TokenMatcher tokenMatcher, final String response) {
        this.pathMatcher = pathMatcher;
        this.tokenMatcher = tokenMatcher;
        this.response = response;
    }

    boolean matches(final String path) {
        return this.pathMatcher.matches(path);
    }

    boolean matches(final AccessToken accessToken, final String path) {
        return this.tokenMatcher.matches(accessToken.accessToken()) && this.pathMatcher.matches(path);
    }

    String response() {
        return this.response;
    }
}
