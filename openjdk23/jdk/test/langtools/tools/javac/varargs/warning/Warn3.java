/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5035307
 * @summary fixed-arity warning given too often
 * @author gafter
 *
 * @compile -Werror  Warn3.java
 */

package varargs.warning.warn3;

class Warn3 {
    void f(Class<?>... args) {}
    void g(Class... args) {
        f(args);
    }
}
