/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package jdk.internal.classfile.impl;

import java.lang.classfile.attribute.LocalVariableInfo;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.classfile.instruction.LocalVariable;
import java.lang.constant.ClassDesc;

public final class BoundLocalVariable
        extends AbstractBoundLocalVariable
        implements LocalVariableInfo,
                   LocalVariable {

    public BoundLocalVariable(CodeImpl code, int offset) {
        super(code, offset);
    }

    @Override
    public Utf8Entry type() {
        return secondaryEntry();
    }

    @Override
    public ClassDesc typeSymbol() {
        return Util.fieldTypeSymbol(type());
    }

    @Override
    public void writeTo(DirectCodeBuilder writer) {
        writer.addLocalVariable(this);
    }

    @Override
    public String toString() {
        return String.format("LocalVariable[name=%s, slot=%d, type=%s]", name().stringValue(), slot(), type().stringValue());
    }
}
