/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package p2;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Main {
    private Main() { }

    public static URL getResourceInClassLoader(String name) {
        return Main.class.getClassLoader().getResource(name);
    }

    public static Enumeration<URL> getResourcesInClassLoader(String name) throws IOException {
        return Main.class.getClassLoader().getResources(name);
    }

    public static InputStream getResourceAsStreamInClassLoader(String name) {
        return Main.class.getClassLoader().getResourceAsStream(name);
    }
}
