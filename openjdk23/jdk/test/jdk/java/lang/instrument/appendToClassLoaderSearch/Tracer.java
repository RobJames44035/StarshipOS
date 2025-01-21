/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 *
 *
 * Used by the unit tests for the Instrument appendToBootstrapClassLoaderSearch
 * tests. This class is a "supporting" class which the agent puts on the boot
 * class path.
 */
package org.tools;

public class Tracer {

    static {
        ClassLoader cl = Tracer.class.getClassLoader();
        if (cl != null) {
            throw new RuntimeException("Tracer loaded by: " + cl);
        }
        System.out.println("Tracer loaded by boot class loader.");
    }

    public static void trace(String msg) {
        System.out.println(msg);
    }

}
