/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8043725
 * @summary javac fails with StackOverflowException
 * @compile T8043725.java
 */
class T8043725 {
    <T extends Comparable<T>> T m(T v) {
        //this will generate two upper bounds, T and Comparable<T'> respectively
        //causing infinite recursion in lub (because of JLS 18.3.1).
        return m(v);
    }
}
