/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package p2;

import java.io.InputStream;
import java.net.URL;

public class Main {
    private Main() { }

    public static URL getResource(String name) {
        return Main.class.getResource(name);
    }

    public static InputStream getResourceAsStream(String name) {
        return Main.class.getResourceAsStream(name);
    }

    public static URL getResource(Class<?> c, String name) {
        return c.getResource(name);
    }

    public static InputStream getResourceAsStream(Class<?> c, String name) {
        return c.getResourceAsStream(name);
    }
}
