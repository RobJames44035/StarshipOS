/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.classloaders;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSigner;
import java.security.CodeSource;

/**
 *
 * This is just a classloader that doesn't follow delegation pattern.
 *
 */
public class DoItYourselfClassLoader extends FinalizableClassloader {

    private static long counter = 0;

    /**
     * We force different classes to have different protection domains
     */
    public Class<?> defineClass(String name, byte[] bytes) {
        URL url;
        try {
            url = new URL("http://random.url.com/" + (counter++));
        } catch (MalformedURLException e) {
            throw new RuntimeException("This is impossible, but there is mistake in simple call to URL constructor", e);
        }
        return defineClass(name, bytes, 0, bytes.length, new CodeSource(url, new CodeSigner[] {}));
    }

}
