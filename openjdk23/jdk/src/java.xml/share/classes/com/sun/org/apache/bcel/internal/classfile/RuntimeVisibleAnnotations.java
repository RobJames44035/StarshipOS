/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.Const;

/**
 * represents an annotation that is represented in the class file and is provided to the JVM.
 *
 * @since 6.0
 */
public class RuntimeVisibleAnnotations extends Annotations {

    /**
     * @param nameIndex Index pointing to the name <em>Code</em>
     * @param length Content length in bytes
     * @param input Input stream
     * @param constantPool Array of constants
     * @throws IOException Thrown when an I/O exception of some sort has occurred.
     */
    public RuntimeVisibleAnnotations(final int nameIndex, final int length, final DataInput input, final ConstantPool constantPool) throws IOException {
        super(Const.ATTR_RUNTIME_VISIBLE_ANNOTATIONS, nameIndex, length, input, constantPool, true);
    }

    /**
     * @return deep copy of this attribute
     */
    @Override
    public Attribute copy(final ConstantPool constantPool) {
        return (Attribute) clone();
    }

    @Override
    public final void dump(final DataOutputStream dos) throws IOException {
        super.dump(dos);
        writeAnnotations(dos);
    }
}
