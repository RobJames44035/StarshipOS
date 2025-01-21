/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.classloaders;

import gc.g1.unloading.loading.LibLoader;

/**
 * We don't call loadClass or defineClass methods explicitly, we invoke loadThroughJNI.
 */
public class JNIClassloader extends FinalizableClassloader {

    static { new LibLoader().hashCode(); /* Load library*/ }

    private static native Class<?> loadThroughJNI0(String className, ClassLoader classloader, byte[] bytecode);

    public static Class<?> loadThroughJNI(String className, byte[] bytecode) {
        return loadThroughJNI0(className.replace('.', '/'), new JNIClassloader(), bytecode);
    }

}
