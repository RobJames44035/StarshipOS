/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
/*
 * @test
 * @bug 7002837 8064365 8078660
 *
 * @summary  Diamond: javac generates diamond inference errors when in 'finder' mode
 * @author mcimadamore
 * @compile -Werror -XDrawDiagnostics -XDfind=diamond T7002837.java
 *
 */

class T7002837<X extends java.io.Serializable & Comparable<?>> {
    T7002837() {}
    { new T7002837<Integer>(); }
}
