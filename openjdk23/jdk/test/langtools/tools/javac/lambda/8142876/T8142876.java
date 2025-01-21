/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8142876
 * @summary Javac does not correctly implement wildcards removal from functional interfaces
 * @compile T8142876.java
 */
class T8142876 {
    interface I<R extends Runnable, T> {
        void m();
    }

    void test() {
        I<? extends O, String> succeed = this::ff;
        I<? extends Comparable<String>, String> failed = this::ff;
    }

    interface O {}

    private void ff(){}
}
