/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8058511
 * @summary StackOverflowError at com.sun.tools.javac.code.Types.lub
 * @compile T8058511b.java
 */
class T8058511b {
    void test(Class<Double> cd, Class<? extends double[]> cdarr) {
        ((false) ? cd : cdarr).toString();
    }
}
