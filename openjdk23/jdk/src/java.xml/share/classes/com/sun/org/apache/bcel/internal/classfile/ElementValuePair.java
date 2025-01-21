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
 * An annotation's element value pair.
 *
 * @since 6.0
 */
public class ElementValuePair {

    static final ElementValuePair[] EMPTY_ARRAY = {};

    private final ElementValue elementValue;

    private final ConstantPool constantPool;

    private final int elementNameIndex;

    public ElementValuePair(final int elementNameIndex, final ElementValue elementValue, final ConstantPool constantPool) {
        this.elementValue = elementValue;
        this.elementNameIndex = elementNameIndex;
        this.constantPool = constantPool;
    }

    protected void dump(final DataOutputStream dos) throws IOException {
        dos.writeShort(elementNameIndex); // u2 name of the element
        elementValue.dump(dos);
    }

    public int getNameIndex() {
        return elementNameIndex;
    }

    public String getNameString() {
        return constantPool.getConstantUtf8(elementNameIndex).getBytes();
    }

    public final ElementValue getValue() {
        return elementValue;
    }

    public String toShortString() {
        final StringBuilder result = new StringBuilder();
        result.append(getNameString()).append("=").append(getValue().toShortString());
        return result.toString();
    }
}
