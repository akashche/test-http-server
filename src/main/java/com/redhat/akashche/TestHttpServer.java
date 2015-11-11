/*
 * Copyright 2015 akashche@redhat.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.akashche;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * User: alexkasko
 * Date: 11/11/15
 */
public class TestHttpServer {
    public static void main(String[] args) throws Exception {
        if (1 != args.length) {
            System.err.println("Port number must be specified as an only argument");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/hello", new HelloHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Started HTTP server on port: [" + port + "]");
        for (;;) {
            Thread.sleep(30 * 1000);
            logHeartbeat();
        }
    }

    private static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            byte[] response = "Hello".getBytes("UTF-8");
            he.getResponseHeaders().add("Content-Type", "text/html");
            he.sendResponseHeaders(200, response.length);
            OutputStream os = he.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    // http://en.wikipedia.org/wiki/Heart_sounds
    private static void logHeartbeat() {
        System.out.println("lub-dub");
    }
}
