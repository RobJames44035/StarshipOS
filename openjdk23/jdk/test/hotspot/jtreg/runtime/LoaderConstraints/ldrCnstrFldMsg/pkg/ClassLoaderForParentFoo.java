/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package pkg;

import java.util.*;
import java.io.*;

// This class loader loads Foo and Parent and calls back to l1 to load Grand.
public class ClassLoaderForParentFoo extends ClassLoader {

    private final Set<String> names = new HashSet<>();

    ClassLoader l1;

    public ClassLoaderForParentFoo(ClassLoader l, String... names) {
        l1 = l;
        for (String n : names) this.names.add(n);
    }

    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.contains("Grand")) return l1.loadClass("pkg.Grand");
        if (!names.contains(name)) return super.loadClass(name, resolve);
        Class<?> result = findLoadedClass(name);
        if (result == null) {
            // Load our own version of Foo that will be referenced by Parent.
            if (name.contains("Parent")) loadClass("pkg.Foo", resolve);
            String filename = name.replace('.', '/') + ".class";
            try (InputStream data = getResourceAsStream(filename)) {
                if (data == null) throw new ClassNotFoundException();
                try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                    int b;
                    do {
                        b = data.read();
                        if (b >= 0) buffer.write(b);
                    } while (b >= 0);
                    byte[] bytes = buffer.toByteArray();
                    result = defineClass(name, bytes, 0, bytes.length);
                }
            } catch (IOException e) {
                throw new ClassNotFoundException("Error reading class file", e);
            }
        }
        if (resolve) resolveClass(result);
        return result;
    }

}
