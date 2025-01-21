/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.bench.java.lang.foreign;

public class JNICB {

    static {
        System.loadLibrary("JNICB");
    }

    public static native long makeCB(String holder, String name, String signature);
}
