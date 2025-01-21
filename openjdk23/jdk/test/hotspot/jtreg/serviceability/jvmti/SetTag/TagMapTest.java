/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8306843
 * @summary Test that 10M tags doesn't time out.
 * @requires vm.jvmti
 * @run main/othervm/native -agentlib:TagMapTest
 *                          -Xlog:jvmti+table
 *                          TagMapTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TagMapTest {
    private static final List<TagMapTest> items = new ArrayList<>();

    private static native void setTag(Object object);
    private static native long getTag(Object object);
    private static native void iterate(boolean tagged);

    public static void main(String[] args) {
        System.loadLibrary("TagMapTest");
        for (int i = 0; i < 10_000_000; i++) {
            items.add(new TagMapTest());
        }

        long startTime = System.nanoTime();
        for (TagMapTest item : items) {
            setTag(item);
        }
        System.out.println("setTag: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");

        startTime = System.nanoTime();
        for (TagMapTest item : items) {
            getTag(item);
        }
        System.out.println("getTag: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");

        startTime = System.nanoTime();
        iterate(true);
        System.out.println("iterate tagged: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");

        startTime = System.nanoTime();
        iterate(false);
        System.out.println("iterate all: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");
    }
}
