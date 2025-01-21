/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package p;

public class Test implements Runnable {
    private final Runnable r;
    public Test(Runnable r) {
        this.r = r;
    }

    /**
     * Tests if the native library is loaded.
     */
    public void run() {
        r.run();
    }
}
