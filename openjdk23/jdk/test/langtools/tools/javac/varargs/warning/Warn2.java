/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5035307
 * @summary fixed-arity warning given too often
 * @author gafter
 *
 * @compile -Werror  Warn2.java
 */

package varargs.warning.warn2;

class Warn2 {
    void f(String... args) {}
    void g(String... args) {
        f(args);
    }
}
