/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.bcel.internal.classfile;

import java.io.DataInput;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.Const;

/**
 * This class represents a constant pool reference to a field.
 */
public final class ConstantFieldref extends ConstantCP {

    /**
     * Initialize from another object.
     *
     * @param c Source to copy.
     */
    public ConstantFieldref(final ConstantFieldref c) {
        super(Const.CONSTANT_Fieldref, c.getClassIndex(), c.getNameAndTypeIndex());
    }

    /**
     * Initialize instance from input data.
     *
     * @param input input stream
     * @throws IOException if an I/O error occurs.
     */
    ConstantFieldref(final DataInput input) throws IOException {
        super(Const.CONSTANT_Fieldref, input);
    }

    /**
     * @param classIndex Reference to the class containing the Field
     * @param nameAndTypeIndex and the Field signature
     */
    public ConstantFieldref(final int classIndex, final int nameAndTypeIndex) {
        super(Const.CONSTANT_Fieldref, classIndex, nameAndTypeIndex);
    }

    /**
     * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.
     * I.e., the hierarchy of Fields, fields, attributes, etc. spawns a tree of objects.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitConstantFieldref(this);
    }
}
