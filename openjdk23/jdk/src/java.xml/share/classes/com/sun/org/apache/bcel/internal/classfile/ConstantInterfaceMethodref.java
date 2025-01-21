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
 * This class represents a constant pool reference to an interface method.
 */
public final class ConstantInterfaceMethodref extends ConstantCP {

    /**
     * Initialize from another object.
     *
     * @param c Source to copy.
     */
    public ConstantInterfaceMethodref(final ConstantInterfaceMethodref c) {
        super(Const.CONSTANT_InterfaceMethodref, c.getClassIndex(), c.getNameAndTypeIndex());
    }

    /**
     * Initialize instance from input data.
     *
     * @param input input stream
     * @throws IOException if an I/O error occurs.
     */
    ConstantInterfaceMethodref(final DataInput input) throws IOException {
        super(Const.CONSTANT_InterfaceMethodref, input);
    }

    /**
     * @param classIndex Reference to the class containing the method
     * @param nameAndTypeIndex and the method signature
     */
    public ConstantInterfaceMethodref(final int classIndex, final int nameAndTypeIndex) {
        super(Const.CONSTANT_InterfaceMethodref, classIndex, nameAndTypeIndex);
    }

    /**
     * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.
     * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitConstantInterfaceMethodref(this);
    }
}
