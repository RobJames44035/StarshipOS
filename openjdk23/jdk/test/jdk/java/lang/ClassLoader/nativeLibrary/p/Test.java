/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package p;

public class Test implements Runnable {
    public static native int count();

    /**
     * Tests if the native library is loaded.
     */
    public void run() {
        System.loadLibrary("nativeLibraryTest");
        if (count() != 1) {
            throw new RuntimeException("Expected count = 1 but got " + count());
        }
    }
}
