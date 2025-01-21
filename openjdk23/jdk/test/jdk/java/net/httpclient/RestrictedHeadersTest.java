/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8178699
 * @modules java.net.http
 * @run main/othervm RestrictedHeadersTest
 * @run main/othervm -Djdk.httpclient.allowRestrictedHeaders=content-length,connection RestrictedHeadersTest content-length connection
 * @run main/othervm -Djdk.httpclient.allowRestrictedHeaders=host,upgrade RestrictedHeadersTest host upgrade
 * @run main/othervm -Djdk.httpclient.allowRestrictedHeaders=via RestrictedHeadersTest via
 */

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Set;

public class RestrictedHeadersTest {
    public static void main(String[] args) {
        if (args.length == 0) {
            runDefaultTest();
        } else {
            runTest(Set.of(args));
        }
    }

    // This list must be same as impl

    static Set<String> defaultRestrictedHeaders =
            Set.of("connection", "content-length", "expect", "host", "upgrade");

    private static void runDefaultTest() {
        System.out.println("DEFAULT TEST: no property set");
        for (String header : defaultRestrictedHeaders) {
            checkHeader(header, "foo", false);
        }
        // miscellaneous others that should succeed
        checkHeader("foobar", "barfoo", true);
        checkHeader("date", "today", true);
    }

    private static void checkHeader(String name, String value, boolean succeed) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create("https://foo.com/"))
                    .header(name, value)
                    .GET()
                    .build();
            if (!succeed) {
                String s = name+"/"+value+" should have failed";
                throw new RuntimeException(s);
            }
            System.out.printf("%s = %s succeeded as expected\n", name, value);
        } catch (IllegalArgumentException iae) {
            if (succeed) {
                String s = name+"/"+value+" should have succeeded";
                throw new RuntimeException(s);
            }
            System.out.printf("%s = %s failed as expected\n", name, value);
        }
    }

    // args is the Set of allowed restricted headers
    private static void runTest(Set<String> args) {
        System.out.print("RUNTEST: allowed headers set in property: ");
        for (String arg : args) System.out.printf("%s ", arg);
        System.out.println("");

        for (String header : args) {
            checkHeader(header, "val", true);
        }
        for (String header : defaultRestrictedHeaders) {
            if (!args.contains(header)) {
                checkHeader(header, "foo", false);
            }
        }
    }
}
