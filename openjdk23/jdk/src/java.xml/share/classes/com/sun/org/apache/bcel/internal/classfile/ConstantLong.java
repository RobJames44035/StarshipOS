/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.bcel.internal.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.Const;

/**
 * This class is derived from the abstract {@link Constant} and represents a reference to a long object.
 *
 * @see     Constant
 * @LastModified: Jan 2020
 */
public final class ConstantLong extends Constant implements ConstantObject {

    private long bytes;

    /**
     * Initialize from another object.
     *
     * @param c Source to copy.
     */
    public ConstantLong(final ConstantLong c) {
        this(c.getBytes());
    }

    /**
     * Initialize instance from file data.
     *
     * @param file Input stream
     * @throws IOException if an I/O error occurs.
     */
    ConstantLong(final DataInput file) throws IOException {
        this(file.readLong());
    }

    /**
     * @param bytes Data
     */
    public ConstantLong(final long bytes) {
        super(Const.CONSTANT_Long);
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
        v.visitConstantLong(this);
    }

    /**
     * Dump constant long to file stream in binary format.
     *
     * @param file Output file stream
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void dump(final DataOutputStream file) throws IOException {
        file.writeByte(super.getTag());
        file.writeLong(bytes);
    }

    /**
     * @return data, i.e., 8 bytes.
     */
    public long getBytes() {
        return bytes;
    }

    /**
     * @return Long object
     */
    @Override
    public Object getConstantValue(final ConstantPool cp) {
        return Long.valueOf(bytes);
    }

    /**
     * @param bytes the raw bytes that represent this long
     */
    public void setBytes(final long bytes) {
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
