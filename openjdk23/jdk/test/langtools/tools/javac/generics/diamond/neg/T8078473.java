/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
/*
 * @test
 * @bug 8078473 8078660
 * @summary  javac diamond finder crashes when used to build java.base module
 * @compile -Werror T8078473.java -XDrawDiagnostics -XDfind=diamond
 */

class T8078473<P, Q> {

    static class C<T, U> {
        C(T8078473<?, ?> p) {}
    }

    {
        new C<Q, Q>(this) {};
        new C<Q, Q>(this);
    }
}
