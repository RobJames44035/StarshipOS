/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package java.lang;

public class BootNativeLibrary {
    static {
        System.loadLibrary("bootLoaderTest");
    }

    public static native Class<?> findClass(String name);
}
