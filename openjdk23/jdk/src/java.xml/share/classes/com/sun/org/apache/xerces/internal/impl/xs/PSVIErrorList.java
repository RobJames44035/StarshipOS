/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.impl.xs;

import com.sun.org.apache.xerces.internal.xs.StringList;
import java.util.AbstractList;

/**
 * StringList implementation for schema error codes and error messages.
 *
 * @xerces.internal
 *
 * @author Michael Glavassevich, IBM
 *
 * @LastModified: Oct 2017
 */
final class PSVIErrorList extends AbstractList<String> implements StringList {

    private final String[] fArray;
    private final int fLength;
    private final int fOffset;

    public PSVIErrorList(String[] array, boolean even) {
        fArray = array;
        fLength = (fArray.length >> 1);
        fOffset = even ? 0 : 1;
    }

    public boolean contains(String item) {
        if (item == null) {
            for (int i = 0; i < fLength; ++i) {
                if (fArray[(i << 1) + fOffset] == null) {
                    return true;
                }
            }
        }
        else {
            for (int i = 0; i < fLength; ++i) {
                if (item.equals(fArray[(i << 1) + fOffset])) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getLength() {
        return fLength;
    }

    public String item(int index) {
        if (index < 0 || index >= fLength) {
            return null;
        }
        return fArray[(index << 1) + fOffset];
    }

    /*
     * List methods
     */

    public String get(int index) {
        if (index >= 0 && index < fLength) {
            return fArray[(index << 1) + fOffset];
        }
        throw new IndexOutOfBoundsException("Index: " + index);
    }

    public int size() {
        return getLength();
    }

} // class PSVIErrorList
