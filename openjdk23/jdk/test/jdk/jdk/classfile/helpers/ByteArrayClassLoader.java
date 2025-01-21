/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package helpers;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class ByteArrayClassLoader extends ClassLoader {
    final Map<String, ClassData> classNameToClass;

    public ByteArrayClassLoader(ClassLoader parent, String name, byte[] bytes) {
        this(parent, Collections.singletonMap(name, new ClassData(name, bytes)));
    }

    public ByteArrayClassLoader(ClassLoader parent, Map<String, ClassData> classNameToClass) {
        super(parent);
        this.classNameToClass = classNameToClass;
    }

    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (classNameToClass.containsKey(name)) {
            return findClass(name);
        }
        return super.loadClass(name, resolve);
    }

    public Class<?> findClass(String name) throws ClassNotFoundException {
        ClassData d = classNameToClass.get(name);
        if (d != null) {
            if (d.klass != null) {
                return d.klass;
            }
            return d.klass = defineClass(name, d.bytes, 0, d.bytes.length);
        }
        return super.findClass(name);
    }

    public void loadAll() throws Exception {
        for (String className : classNameToClass.keySet()) {
            loadClass(className);
        }
    }

    public Method getMethod(String className, String methodName) throws Exception {
            for (Method m : loadClass(className).getDeclaredMethods()) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Method name '" + methodName + "' not found in '" + className + "'");
    }

    public static class ClassData {
        final String name;
        final byte[] bytes;
        Class<?> klass;

        public ClassData(String name, byte[] bytes) {
            this.name = name;
            this.bytes = bytes;
        }
    }
}
