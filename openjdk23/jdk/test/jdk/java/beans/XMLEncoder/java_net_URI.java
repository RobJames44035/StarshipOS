/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6245149
 * @summary Tests URI encoding
 * @run main/othervm java_net_URI
 * @author Sergey Malenkov
 */

import java.net.URI;
import java.net.URISyntaxException;

public final class java_net_URI extends AbstractTest<URI> {
    public static void main(String[] args) {
        new java_net_URI().test();
    }

    protected URI getObject() {
        try {
            return new URI("http://www.sun.com/");
        } catch (URISyntaxException exception) {
            throw new Error("unexpected exception", exception);
        }
    }

    protected URI getAnotherObject() {
        try {
            return new URI("ftp://www.sun.com/");
        } catch (URISyntaxException exception) {
            throw new Error("unexpected exception", exception);
        }
    }
}
