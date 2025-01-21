/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package org.openjdk.jni;

import org.openjdk.jni.use.PanamaJNIUse;

public class PanamaMainJNI {

    public static void main(String[] args) {
        System.loadLibrary("LinkerInvokerModule");
        PanamaJNIUse.run();
    }
}
