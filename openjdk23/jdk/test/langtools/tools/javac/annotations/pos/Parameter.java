/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4901285
 * @summary JSR175: Parameter annotations
 * @author gafter
 *
 * @compile Parameter.java
 */

package annotation.parameter;

@interface A {}

class T {
    void f(final @A int x) {
    }
    void g(@A final int y, int[] x) {
        for (@A int a : x) {}
        for (final @A int a : x) break;
        for (@A final int a : x) break;
    }
}
