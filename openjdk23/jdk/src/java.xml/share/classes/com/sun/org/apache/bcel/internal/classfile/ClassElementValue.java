/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.classfile;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @since 6.0
 */
public class ClassElementValue extends ElementValue {
    // For primitive types and string type, this points to the value entry in
    // the cpool
    // For 'class' this points to the class entry in the cpool
    private final int idx;

    public ClassElementValue(final int type, final int idx, final ConstantPool cpool) {
        super(type, cpool);
        this.idx = idx;
    }

    @Override
    public void dump(final DataOutputStream dos) throws IOException {
        dos.writeByte(super.getType()); // u1 kind of value
        dos.writeShort(idx);
    }

    public String getClassString() {
        return super.getConstantPool().getConstantUtf8(idx).getBytes();
    }

    public int getIndex() {
        return idx;
    }

    @Override
    public String stringifyValue() {
        return super.getConstantPool().getConstantUtf8(idx).getBytes();
    }
}
