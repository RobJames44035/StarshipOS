/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.xs;

import java.util.List;

/**
 *  The <code>StringList</code> is an immutable ordered collection of
 * <code>GenericString</code>.
 *
 * @LastModified: Oct 2017
 */
public interface StringList extends List<String> {
    /**
     *  The number of <code>GenericString</code>s in the list. The range of
     * valid child object indices is 0 to <code>length-1</code> inclusive.
     */
    public int getLength();

    /**
     *  Checks if the <code>GenericString</code> <code>item</code> is a member
     * of this list.
     * @param item  <code>GenericString</code> whose presence in this list is
     *   to be tested.
     * @return  True if this list contains the <code>GenericString</code>
     *   <code>item</code>.
     */
    public boolean contains(String item);

    /**
     *  Returns the <code>index</code>th item in the collection or
     * <code>null</code> if <code>index</code> is greater than or equal to
     * the number of objects in the list. The index starts at 0.
     * @param index  index into the collection.
     * @return  The <code>GenericString</code> at the <code>index</code>th
     *   position in the <code>StringList</code>, or <code>null</code> if
     *   the index specified is not valid.
     */
    public String item(int index);

}
