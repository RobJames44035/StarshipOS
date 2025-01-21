/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.io.NameClassCache;

/**
 * @test
 * @bug 8280041
 * @summary Sanity test for ClassCache under continuous GC
 * @compile/module=java.base java/io/NameClassCache.java
 * @run main ContinuousGCTest
 */
public class ContinuousGCTest {
    static final NameClassCache CACHE = new NameClassCache();
    static final String VALUE = "ClassCache-ContinuousGCTest";

    public static void main(String... args) throws Throwable {
        for (int c = 0; c < 1000; c++) {
            test();
            System.gc();
        }
    }

    public static void test() {
        String cached = CACHE.get(ContinuousGCTest.class);
        if (!cached.equals(VALUE)) {
            throw new IllegalStateException("Cache failure, got: " + cached);
        }
    }
}
