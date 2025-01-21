/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package q;

import p.Foo;

// access protected inner class Foo.T
public class Bar extends Foo {
    public static final Class<?> T_CLS = T.class;
    public static final Class<?> T_ARRAY_CLS = T[].class;

    public static void meth(T[] arr) {
        System.out.println("called method");
    }
}
