/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import com.sun.net.httpserver.*;
import java.net.*;
import java.net.http.*;
import java.io.*;
import java.util.concurrent.*;
import javax.net.ssl.*;
import java.nio.file.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import jdk.test.lib.net.SimpleSSLContext;
import static java.net.http.HttpRequest.*;
import static java.net.http.HttpResponse.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpEchoHandler implements HttpHandler {
    static final Path CWD = Paths.get(".");

    public HttpEchoHandler() {}

    @Override
    public void handle(HttpExchange t)
            throws IOException {
        try {
            System.err.println("EchoHandler received request to " + t.getRequestURI());
            InputStream is = t.getRequestBody();
            Headers map = t.getRequestHeaders();
            Headers map1 = t.getResponseHeaders();
            map1.add("X-Hello", "world");
            map1.add("X-Bye", "universe");
            String fixedrequest = map.getFirst("XFixed");
            File outfile = Files.createTempFile(CWD, "foo", "bar").toFile();
            FileOutputStream fos = new FileOutputStream(outfile);
            int count = (int) is.transferTo(fos);
            is.close();
            fos.close();
            InputStream is1 = new FileInputStream(outfile);
            OutputStream os = null;
            // return the number of bytes received (no echo)
            String summary = map.getFirst("XSummary");
            if (fixedrequest != null && summary == null) {
                t.sendResponseHeaders(200, count);
                os = t.getResponseBody();
                is1.transferTo(os);
            } else {
                t.sendResponseHeaders(200, 0);
                os = t.getResponseBody();
                is1.transferTo(os);

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
