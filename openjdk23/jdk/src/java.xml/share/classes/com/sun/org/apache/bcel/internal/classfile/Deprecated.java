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
import com.sun.org.apache.bcel.internal.util.Args;

/**
 * This class is derived from <em>Attribute</em> and denotes that this is a deprecated method. It is instantiated from
 * the <em>Attribute.readAttribute()</em> method.
 *
 * @see Attribute
 */
public final class Deprecated extends Attribute {

    private byte[] bytes;

    /**
     * Initialize from another object. Note that both objects use the same references (shallow copy). Use clone() for a
     * physical copy.
     *
     * @param c Source to copy.
     */
    public Deprecated(final Deprecated c) {
        this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
    }

    /**
     * @param nameIndex Index in constant pool to CONSTANT_Utf8
     * @param length Content length in bytes
     * @param bytes Attribute contents
     * @param constantPool Array of constants
     */
    public Deprecated(final int nameIndex, final int length, final byte[] bytes, final ConstantPool constantPool) {
        super(Const.ATTR_DEPRECATED, nameIndex, Args.require0(length, "Deprecated attribute length"), constantPool);
        this.bytes = bytes;
    }

    /**
     * Construct object from input stream.
     *
     * @param nameIndex Index in constant pool to CONSTANT_Utf8
     * @param length Content length in bytes
     * @param input Input stream
     * @param constantPool Array of constants
     * @throws IOException if an I/O error occurs.
     */
    Deprecated(final int nameIndex, final int length, final DataInput input, final ConstantPool constantPool) throws IOException {
        this(nameIndex, length, (byte[]) null, constantPool);
        if (length > 0) {
            bytes = new byte[length];
            input.readFully(bytes);
            println("Deprecated attribute with length > 0");
        }
    }

    /**
     * Called by objects that are traversing the nodes of the tree implicitly defined by the contents of a Java class.
     * I.e., the hierarchy of methods, fields, attributes, etc. spawns a tree of objects.
     *
     * @param v Visitor object
     */
    @Override
    public void accept(final Visitor v) {
        v.visitDeprecated(this);
    }

    /**
     * @return deep copy of this attribute
     */
    @Override
    public Attribute copy(final ConstantPool constantPool) {
        final Deprecated c = (Deprecated) clone();
        if (bytes != null) {
            c.bytes = bytes.clone();
        }
        c.setConstantPool(constantPool);
        return c;
    }

    /**
     * Dump source file attribute to file stream in binary format.
     *
     * @param file Output file stream
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void dump(final DataOutputStream file) throws IOException {
        super.dump(file);
        if (super.getLength() > 0) {
            file.write(bytes, 0, super.getLength());
        }
    }

    /**
     * @return data bytes.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param bytes the raw bytes that represents this byte array
     */
    public void setBytes(final byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * @return attribute name
     */
    @Override
    public String toString() {
        return Const.getAttributeName(Const.ATTR_DEPRECATED) + ": true";
    }
}
