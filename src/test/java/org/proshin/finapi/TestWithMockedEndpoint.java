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
package org.proshin.finapi;

import javax.annotation.Nonnull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.endpoint.FpEndpoint;

@ExtendWith(MockServerExtension.class)
@SuppressWarnings("AbstractClassWithoutAbstractMethods")
public abstract class TestWithMockedEndpoint {

    @SuppressWarnings({"InstanceVariableMayNotBeInitialized", "allfinal"})
    private MockServerClient server;
    @SuppressWarnings({"InstanceVariableMayNotBeInitialized", "allfinal"})
    private Endpoint endpoint;

    @BeforeEach
    public void init(@Nonnull final MockServerClient server) {
        this.server = server;
        this.endpoint = new FpEndpoint("http://localhost:" + this.server.remoteAddress().getPort());
    }

    protected Endpoint endpoint() {
        return this.endpoint;
    }

    protected MockServerClient server() {
        return this.server;
    }
}
