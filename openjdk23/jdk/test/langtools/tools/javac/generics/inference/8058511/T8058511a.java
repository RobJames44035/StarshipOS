/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8058511
 * @summary StackOverflowError at com.sun.tools.javac.code.Types.lub
 * @compile T8058511a.java
 */
class T8058511a {
    <Z> void choose(Z z1, Z z2) { }

    void test(Class<Double> cd, Class<? extends double[]> cdarr) {
        choose(cd, cdarr);
    }
}
