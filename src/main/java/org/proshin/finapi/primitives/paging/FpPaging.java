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
package org.proshin.finapi.primitives.paging;

import org.json.JSONObject;

/**
 * Paging info.
 * @todo #38 Write a test for FpPaging class.
 */
public final class FpPaging implements Paging {

    private final JSONObject origin;

    public FpPaging(final JSONObject origin) {
        this.origin = origin;
    }

    @Override
    public int page() {
        return this.origin.getInt("page");
    }

    @Override
    public int perPage() {
        return this.origin.getInt("perPage");
    }

    @Override
    public int pageCount() {
        return this.origin.getInt("pageCount");
    }

    @Override
    public int totalCount() {
        return this.origin.getInt("totalCount");
    }
}
