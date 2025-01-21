/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.Const;

/**
 * This class is derived from the abstract {@link Constant} and represents a reference to a Double object.
 *
 * @see     Constant
 * @LastModified: Jun 2019
 */
public final class ConstantDouble extends Constant implements ConstantObject {

    private double bytes;

    /**
     * Initialize from another object.
     *
     * @param c Source to copy.
     */
    public ConstantDouble(final ConstantDouble c) {
        this(c.getBytes());
    }

    /**
     * Initialize instance from file data.
     *
     * @param file Input stream
     * @throws IOException if an I/O error occurs.
     */
    ConstantDouble(final DataInput file) throws IOException {
        this(file.readDouble());
    }

    /**
     * @param bytes Data
     */
    public ConstantDouble(final double bytes) {
        super(Const.CONSTANT_Double);
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
        v.visitConstantDouble(this);
    }

    /**
     * Dump constant double to file stream in binary format.
     *
     * @param file Output file stream
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void dump(final DataOutputStream file) throws IOException {
        file.writeByte(super.getTag());
        file.writeDouble(bytes);
    }

    /**
     * @return data, i.e., 8 bytes.
     */
    public double getBytes() {
        return bytes;
    }

    /**
     * @return Double object
     */
    @Override
    public Object getConstantValue(final ConstantPool cp) {
        return Double.valueOf(bytes);
    }

    /**
     * @param bytes the raw bytes that represent the double value
     */
    public void setBytes(final double bytes) {
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
