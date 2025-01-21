/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package com.sun.tools.jnativescan;

import java.lang.classfile.MethodModel;
import java.lang.classfile.constantpool.MemberRefEntry;
import java.lang.classfile.instruction.InvokeInstruction;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;

record MethodRef(ClassDesc owner, String name, MethodTypeDesc type) {
    public static MethodRef ofModel(MethodModel model) {
        return new MethodRef(model.parent().orElseThrow().thisClass().asSymbol(),
                model.methodName().stringValue(), model.methodTypeSymbol());
    }

    public static MethodRef ofInvokeInstruction(InvokeInstruction instruction) {
        return new MethodRef(instruction.owner().asSymbol(),
                instruction.name().stringValue(), instruction.typeSymbol());
    }

    @Override
    public String toString() {
        return JNativeScanTask.qualName(owner) + "::" + name + type.displayDescriptor();
    }
}
