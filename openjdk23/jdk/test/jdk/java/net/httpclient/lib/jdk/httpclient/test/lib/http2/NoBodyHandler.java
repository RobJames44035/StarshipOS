/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.httpclient.test.lib.http2;

import java.io.*;
import static java.lang.System.out;

public class NoBodyHandler implements Http2Handler {

    @Override
    public void handle(Http2TestExchange t) throws IOException {
        try {
            out.println("NoBodyHandler received request to " + t.getRequestURI());
            try (InputStream is = t.getRequestBody()) {
                byte[] ba = is.readAllBytes();
                out.println(Thread.currentThread().getName() + ": Read " + ba.length);
            }
            t.sendResponseHeaders(200, 0);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }
}
