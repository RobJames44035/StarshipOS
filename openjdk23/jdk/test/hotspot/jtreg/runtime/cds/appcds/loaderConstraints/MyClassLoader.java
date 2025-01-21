/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.io.InputStream;
import java.io.IOException;

public class MyClassLoader extends ClassLoader {
    ClassLoader parent;
    ClassLoader appLoader;
    public MyClassLoader(ClassLoader parent, ClassLoader appLoader) {
        super(parent);
        this.parent = parent;
        this.appLoader = appLoader;
    }

    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        System.out.println("MyClassLoader: loadClass(\"" + name + "\", " + resolve + ")");
        Class c;

        if (name.equals("MyHttpHandlerC")) {
            byte[] bytes;
            try (InputStream is = appLoader.getResourceAsStream("MyHttpHandlerC.class")) {
                bytes = is.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException("Unexpected", e);
            }
            c = defineClass(name, bytes, 0, bytes.length);
        } else {
            c = super.loadClass(name, resolve);
        }
        System.out.println("MyClassLoader: loaded " + name + " = " + c);
        return c;
    }
}

