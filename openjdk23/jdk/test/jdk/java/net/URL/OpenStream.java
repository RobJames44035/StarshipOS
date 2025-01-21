/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
 * @bug 4064962 8202708
 * @summary openStream should work even when not using proxies and
 *          UnknownHostException is thrown as expected.
 */

import java.io.*;
import java.net.*;


public class OpenStream {

    private static final String badHttp = "http://foo.bar.baz/";
    private static final String badUnc = "file://h7qbp368oix47/not-exist.txt";

    public static void main(String[] args) throws IOException {
        testHttp();
        testUnc();
    }

    static void testHttp() throws IOException {
        checkThrows(badHttp);
    }

    static void testUnc() throws IOException {
        boolean isWindows = System.getProperty("os.name").startsWith("Windows");
        if (isWindows) {
            checkThrows(badUnc);
        }
    }

    static void checkThrows(String url) throws IOException {
        URL u = new URL(url);
        try {
            InputStream in = u.openConnection(Proxy.NO_PROXY).getInputStream();
        } catch (UnknownHostException x) {
            System.out.println("UnknownHostException is thrown as expected.");
            return;
        }
        throw new RuntimeException("Expected UnknownHostException to be " +
                "thrown for " + url);

    }
}

