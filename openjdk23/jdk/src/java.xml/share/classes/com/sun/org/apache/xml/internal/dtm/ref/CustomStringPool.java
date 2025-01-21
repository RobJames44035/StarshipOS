/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * $Id: CustomStringPool.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import java.util.HashMap;
import java.util.Map;

/**
 * CustomStringPool is an example of an application-provided data structure for a
 * DTM implementation to hold symbol references, e.g. element names. It will
 * follow the DTMStringPool interface and use two simple methods
 * indexToString(int i) and stringToIndex(String s) to map between a set of
 * string values and a set of integer index values. Therefore, an application
 * may improve DTM processing speed by substituting the DTM symbol resolution
 * tables with application specific quick symbol resolution tables.
 * <p>
 * %REVIEW% The only difference between this an DTMStringPool seems to be that
 * it uses a java.lang.Hashtable full of Integers rather than implementing its
 * own hashing. Joe deliberately avoided that approach when writing
 * DTMStringPool, since it is both much more memory-hungry and probably slower
 * -- especially in JDK 1.1.x, where Hashtable is synchronized. We need to
 * either justify this implementation or discard it.
 *
 * <p>
 * Status: In progress, under discussion.
 *
 * @LastModified: Oct 2017
 */
public class CustomStringPool extends DTMStringPool {

    final Map<String, Integer> m_stringToInt = new HashMap<>();
    public static final int NULL = -1;

    public CustomStringPool() {
        super();
    }

    public void removeAllElements() {
        m_intToString.clear();
        if (m_stringToInt != null) {
            m_stringToInt.clear();
        }
    }

    /**
     * @return string whose value is uniquely identified by this integer index.
     * @throws java.lang.IndexOutOfBoundsException if index doesn't map to
     * a string.
     */
    @Override
    public String indexToString(int i)
            throws IndexOutOfBoundsException {
        return m_intToString.get(i);
    }

    /**
     * @return integer index uniquely identifying the value of this string.
     */
    @Override
    public int stringToIndex(String s) {
        if (s == null) {
            return NULL;
        }
        Integer iobj = m_stringToInt.get(s);
        if (iobj == null) {
            m_intToString.add(s);
            iobj = m_intToString.size();
            m_stringToInt.put(s, iobj);
        }
        return iobj;
    }
}
