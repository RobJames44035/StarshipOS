/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

/*
 * @test
 * @bug 8075139
 * @summary Basic test for java.protocol.handler.pkgs
 * @compile handlers/foo/Handler.java handlers/bar/Handler.java HandlersPkgPrefix.java
 * @run main/othervm HandlersPkgPrefix
 */

public class HandlersPkgPrefix {
    static final Consumer<Result> KNOWN = r -> {
        if (r.exception != null)
            throw new RuntimeException("Unexpected exception " + r.exception);
        String p = r.url.getProtocol();
        if (!r.protocol.equals(p))
            throw new RuntimeException("Expected:" + r.protocol + ", got:" + p);
    };
    static final Consumer<Result> UNKNOWN = r -> {
        if (r.url != null)
            throw new RuntimeException("Unexpected url:" + r.url);
        if (!(r.exception instanceof MalformedURLException))
            throw new RuntimeException("Expected MalformedURLException, got:"
                                       + r.exception);
    };

    public static void main(String[] args) {
        withPrefix("unknown", "", UNKNOWN);
        withPrefix("unknown", "handlers", UNKNOWN);

        withPrefix("foo", "", UNKNOWN);
        withPrefix("foo", "xxx|yyy|zzz", UNKNOWN);
        withPrefix("foo", "||||", UNKNOWN);
        withPrefix("foo", "|a|b|c|handlers", KNOWN);

        withPrefix("bar", "", UNKNOWN);
        withPrefix("bar", "x.y.z|y.y.y|z.z.z", UNKNOWN);
        withPrefix("bar", " x.y.z | y.y.y | z.z.z| |  ", UNKNOWN);
        withPrefix("bar", "| a | b | c | handlers | d | e", KNOWN);
    }

    static void withPrefix(String protocol, String pkgPrefix,
                           Consumer<Result> resultChecker) {
        System.out.println("Testing, " + protocol + ", " + pkgPrefix);

        // The long standing implementation behavior is that the
        // property is read multiple times, not cached.
        System.setProperty("java.protocol.handler.pkgs", pkgPrefix);
        URL url = null;
        Exception exception = null;
        try {
            url = new URL(protocol + "://");
        } catch (MalformedURLException x) {
            exception = x;
        }
        resultChecker.accept(new Result(protocol, url, exception));
    }

    static class Result {
        final String protocol;
        final URL url;
        final Exception exception;
        Result(String protocol, URL url, Exception exception) {
            this.protocol = protocol;
            this.url = url;
            this.exception = exception;
        }
    }
}

