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

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.proshin.finapi.endpoint.Endpoint;
import org.proshin.finapi.endpoint.FpEndpoint;

public abstract class TestWithMockedEndpoint {

    private static final AtomicInteger PORT_COUNTER = new AtomicInteger(10000);

    private final int port = PORT_COUNTER.getAndIncrement();
    private final ClientAndServer server = startClientAndServer(this.port);
    private final Endpoint endpoint = new FpEndpoint("http://localhost:" + this.port);

    @After
    public void stopServer() {
        this.server.stop(true);
    }

    protected Endpoint endpoint() {
        return this.endpoint;
    }

    protected ClientAndServer server() {
        return this.server;
    }
}
