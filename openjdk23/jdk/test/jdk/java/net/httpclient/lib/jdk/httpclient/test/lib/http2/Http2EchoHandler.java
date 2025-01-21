/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */
package jdk.httpclient.test.lib.http2;

import java.io.*;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.net.http.common.HttpHeadersBuilder;

public class Http2EchoHandler implements Http2Handler {
    static final Path CWD = Paths.get(".");

    public Http2EchoHandler() {}

    @Override
    public void handle(Http2TestExchange t)
            throws IOException {
        try {
            System.err.printf("EchoHandler received request to %s from %s\n",
                              t.getRequestURI(), t.getRemoteAddress());
            InputStream is = t.getRequestBody();
            HttpHeaders map = t.getRequestHeaders();
            HttpHeadersBuilder headersBuilder = t.getResponseHeaders();
            headersBuilder.addHeader("X-Hello", "world");
            headersBuilder.addHeader("X-Bye", "universe");
            String fixedrequest = map.firstValue("XFixed").orElse(null);
            File outfile = Files.createTempFile(CWD, "foo", "bar").toFile();
            //System.err.println ("QQQ = " + outfile.toString());
            FileOutputStream fos = new FileOutputStream(outfile);
            int count = (int) is.transferTo(fos);
            System.err.printf("EchoHandler read %d bytes\n", count);
            is.close();
            fos.close();
            InputStream is1 = new FileInputStream(outfile);
            OutputStream os = null;
            // return the number of bytes received (no echo)
            String summary = map.firstValue("XSummary").orElse(null);
            if (fixedrequest != null && summary == null) {
                t.sendResponseHeaders(200, count);
                os = t.getResponseBody();
                int count1 = (int)is1.transferTo(os);
                System.err.printf("EchoHandler wrote %d bytes\n", count1);
            } else {
                t.sendResponseHeaders(200, 0);
                os = t.getResponseBody();
                int count1 = (int)is1.transferTo(os);
                System.err.printf("EchoHandler wrote %d bytes\n", count1);

                if (summary != null) {
                    String s = Integer.toString(count);
                    os.write(s.getBytes());
                }
            }
            outfile.delete();
            os.close();
            is1.close();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }
}
