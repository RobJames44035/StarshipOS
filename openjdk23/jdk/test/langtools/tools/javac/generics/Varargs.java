/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4920438
 * @summary varargs doesn't work for generic methods
 * @author gafter
 *
 * @compile  Varargs.java
 */

package varargs.versus.generics;

// bug: varargs does not work for generic methods
class T {
    <T> void f(T t, Object... args) {}
    void g(Object x, Object y, Object z) {
        f(x, y, z);
    }
}
