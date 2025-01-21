/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package p2;

public class C {

    public static void export(String pn, Module m) {
        C.class.getModule().addExports(pn, m);
    }
}
