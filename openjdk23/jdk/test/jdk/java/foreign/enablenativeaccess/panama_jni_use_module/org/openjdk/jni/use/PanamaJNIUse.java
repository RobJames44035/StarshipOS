/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package org.openjdk.jni.use;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;

import org.openjdk.jni.def.PanamaJNIDef;

public class PanamaJNIUse {
    public static void run() {
        testDirectAccessCLinker();
    }

    public static void testDirectAccessCLinker() {
        System.out.println("Trying to get downcall handle");
        PanamaJNIDef.nativeLinker0(Linker.nativeLinker(), FunctionDescriptor.ofVoid(), new Linker.Option[0]);
        System.out.println("Got downcall handle");
    }
}
