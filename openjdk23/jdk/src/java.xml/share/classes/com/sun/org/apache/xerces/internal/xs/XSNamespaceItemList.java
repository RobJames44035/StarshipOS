/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.xs;

import java.util.List;

/**
 *  The <code>XSNamesaceItemList</code> interface provides the abstraction of
 * an immutable ordered collection of <code>XSNamespaceItem</code>s, without
 * defining or constraining how this collection is implemented.
 *
 * @LastModified: Oct 2017
 */
public interface XSNamespaceItemList extends List<XSNamespaceItem> {
    /**
     *  The number of <code>XSNamespaceItem</code>s in the list. The range of
     * valid child object indices is 0 to <code>length-1</code> inclusive.
     */
    public int getLength();

    /**
     *  Returns the <code>index</code>th item in the collection or
     * <code>null</code> if <code>index</code> is greater than or equal to
     * the number of objects in the list. The index starts at 0.
     * @param index  index into the collection.
     * @return  The <code>XSNamespaceItem</code> at the <code>index</code>th
     *   position in the <code>XSNamespaceItemList</code>, or
     *   <code>null</code> if the index specified is not valid.
     */
    public XSNamespaceItem item(int index);

}
