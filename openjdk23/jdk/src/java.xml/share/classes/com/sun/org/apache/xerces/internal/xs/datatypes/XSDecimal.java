/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xs.datatypes;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>Interface to expose the value of 'decimal' and related datatypes.</p>
 *
 * @author Naela Nissar, IBM
 *
 */
public interface XSDecimal {

    /**
     * @return the <code>BigDecimal</code> representation of this object
     */
    public BigDecimal getBigDecimal();

    /**
     * @return the <code>BigInteger</code> representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>BigInteger</code>
     */
    public BigInteger getBigInteger() throws NumberFormatException;

    /**
     * @return the long value representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>long</code>
     */
    public long getLong() throws NumberFormatException;

    /**
     * @return the int value representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>int</code>
     */
    public int getInt() throws NumberFormatException;

    /**
     * @return the short value representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>short</code>
     */
    public short getShort() throws NumberFormatException;

    /**
     * @return the byte value representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>byte</code>
     */
    public byte getByte() throws NumberFormatException;
}
