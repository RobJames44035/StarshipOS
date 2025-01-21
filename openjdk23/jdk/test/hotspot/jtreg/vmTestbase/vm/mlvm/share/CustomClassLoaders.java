/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.share;

import java.io.IOException;

public class CustomClassLoaders {

    public static ClassLoader makeClassBytesLoader(final byte[] classBytes,
            final String className) {
        return new ClassLoader() {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                Env.traceDebug("Custom loader: requested=" + name + "; can supply=" + className);

                if (!name.equals(className))
                    return super.loadClass(name, resolve);

                Env.traceDebug("Custom loader: defining " + className + " (" + classBytes.length + ") bytes");

                return defineClass(className, classBytes, 0, classBytes.length);
            }
        };
    }

    public static ClassLoader makeCustomClassLoader(final String forClassName) throws IOException {
        return makeClassBytesLoader(FileUtils.readClass(forClassName), forClassName);
    }
}
