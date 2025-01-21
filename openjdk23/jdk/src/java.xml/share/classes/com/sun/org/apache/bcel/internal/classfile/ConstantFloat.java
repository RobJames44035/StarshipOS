/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.Const;

/**
 * This class is derived from the abstract {@link Constant} and represents a reference to a float object.
 *
 * @see     Constant
 * @LastModified: Jun 2019
 */
public final class ConstantFloat extends Constant implements ConstantObject {

    private float bytes;

    /**
     * Initialize from another object. Note that both objects use the same references (shallow copy). Use clone() for a
     * physical copy.
     *
     * @param c Source to copy.
     */
    public ConstantFloat(final ConstantFloat c) {
        this(c.getBytes());
    }

    /**
     * Initialize instance from file data.
     *
     * @param file Input stream
     * @throws IOException if an I/O error occurs.
     */
    ConstantFloat(final DataInput file) throws IOException {
        this(file.readFloat());
    }

    /**
     * @param bytes Data
     */
    public ConstantFloat(final float bytes) {
        super(Const.CONSTANT_Float);
        this.bytes = bytes;
    }

    /**
     * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.
     * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitConstantFloat(this);
    }

    /**
     * Dump constant float to file stream in binary format.
     *
     * @param file Output file stream
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void dump(final DataOutputStream file) throws IOException {
        file.writeByte(super.getTag());
        file.writeFloat(bytes);
    }

    /**
     * @return data, i.e., 4 bytes.
     */
    public float getBytes() {
        return bytes;
    }

    /**
     * @return Float object
     */
    @Override
    public Object getConstantValue(final ConstantPool cp) {
        return Float.valueOf(bytes);
    }

    /**
     * @param bytes the raw bytes that represent this float
     */
    public void setBytes(final float bytes) {
        this.bytes = bytes;
    }

    /**
     * @return String representation.
     */
    @Override
    public String toString() {
        return super.toString() + "(bytes = " + bytes + ")";
    }
}
