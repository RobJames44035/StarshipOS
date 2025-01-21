/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package varargs.warn2;

class T {
    static void f(String fmt, Object... args) {}

    public static void meth() {
        f("foo", null);
    }
}
