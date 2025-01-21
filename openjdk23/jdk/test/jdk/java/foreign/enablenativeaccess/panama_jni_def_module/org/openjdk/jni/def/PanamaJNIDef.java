/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package org.openjdk.jni.def;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;

public class PanamaJNIDef {

    public static native void nativeLinker0(Linker linker, FunctionDescriptor desc, Linker.Option[] options);
}
