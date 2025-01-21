/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.xs.datatypes;

import com.sun.org.apache.xerces.internal.xs.XSException;
import java.util.List;

/**
 * <p>The <code>ByteList</code> is an immutable ordered collection of
 * <code>byte</code>.</p>
 *
 * @author Ankit Pasricha, IBM
 *
 * @LastModified: Oct 2017
 */
public interface ByteList extends List<Byte> {

    /**
     * The number of <code>byte</code>s in the list. The range of
     * valid child object indices is 0 to <code>length-1</code> inclusive.
     */
    public int getLength();

    /**
     * Checks if the <code>byte</code> <code>item</code> is a
     * member of this list.
     * @param item  <code>byte</code> whose presence in this list
     *   is to be tested.
     * @return  True if this list contains the <code>byte</code>
     *   <code>item</code>.
     */
    public boolean contains(byte item);

    /**
     * Returns the <code>index</code>th item in the collection. The index
     * starts at 0.
     * @param index  index into the collection.
     * @return  The <code>byte</code> at the <code>index</code>th
     *   position in the <code>ByteList</code>.
     * @exception XSException
     *   INDEX_SIZE_ERR: if <code>index</code> is greater than or equal to the
     *   number of objects in the list or less than zero.
     */
    public byte item(int index) throws XSException;

    /**
     * Construct and return a byte array for bytes contained in this list.
     */
    public byte[] toByteArray();
}
