/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @compile lookup/Lookup.java
 * @compile invoker/Invoker.java
 * @run main/othervm --enable-native-access=ALL-UNNAMED TestLoaderLookup
 */

import java.lang.foreign.*;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class TestLoaderLookup {
    public static void main(String[] args) throws ReflectiveOperationException {
        ClassLoader loader1 = newClassLoader("lookup");
        Class<?> lookup = loader1.loadClass("lookup.Lookup");
        Method fooSymbol = lookup.getDeclaredMethod("fooSymbol");
        MemorySegment foo = (MemorySegment) fooSymbol.invoke(null);

        ClassLoader loader2 = newClassLoader("invoker");
        Class<?> invoker = loader2.loadClass("invoker.Invoker");
        Method invoke = invoker.getDeclaredMethod("invoke", MemorySegment.class);
        invoke.invoke(null, foo);

        loader1 = null;
        lookup = null;
        fooSymbol = null;
        // Make sure that the loader is kept reachable
        for (int i = 0 ; i < 1000 ; i++) {
            invoke.invoke(null, foo); // might crash if loader1 is GC'ed
            System.gc();
        }
    }

    public static ClassLoader newClassLoader(String path) {
        try {
            return new URLClassLoader(new URL[] {
                    Paths.get(System.getProperty("test.classes", path)).toUri().toURL(),
            }, null);
        } catch (MalformedURLException e){
            throw new RuntimeException("Unexpected URL conversion failure", e);
        }
    }
}
