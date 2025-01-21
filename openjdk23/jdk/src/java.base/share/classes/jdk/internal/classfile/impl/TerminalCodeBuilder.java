/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeModel;
import java.lang.classfile.attribute.CodeAttribute;

public sealed interface TerminalCodeBuilder extends CodeBuilder, LabelContext
        permits DirectCodeBuilder, BufferedCodeBuilder {
    int curTopLocal();

    static int setupTopLocal(MethodInfo methodInfo, CodeModel original) {
        int paramSlots = Util.maxLocals(methodInfo.methodFlags(), methodInfo.methodTypeSymbol());
        if (original == null) {
            return paramSlots;
        }
        if (original instanceof CodeAttribute attr) {
            return Math.max(paramSlots, attr.maxLocals());
        }
        if (original instanceof BufferedCodeBuilder.Model buffered) {
            return Math.max(paramSlots, buffered.curTopLocal());
        }
        throw new InternalError("Unknown code model " + original);
    }
}
