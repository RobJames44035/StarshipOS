/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.jdk.classfile;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.ConstantDescs.CD_void;

/**
 * TestConstants
 */
public class TestConstants {
    public static final ClassDesc CD_MyClass = ClassDesc.of("MyClass");
    public static final ClassDesc CD_System = ClassDesc.of("java.lang.System");
    public static final ClassDesc CD_PrintStream = ClassDesc.of("java.io.PrintStream");
    public static final ClassDesc CD_ArrayList = ClassDesc.of("java.util.ArrayList");

    public static final MethodTypeDesc MTD_void_int = MethodTypeDesc.ofDescriptor("(I)V");
    public static final MethodTypeDesc MTD_void_StringArray = MethodTypeDesc.of(CD_void, CD_String.arrayType());
}
