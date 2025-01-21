/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.utils.res;

/**
 *
 * It is a mutable object to wrap the long[] used in
 * the contents of the XResourceBundle class
 */
public class LongArrayWrapper {
    private long[] m_long;

    public LongArrayWrapper(long[] arg){
        m_long = arg;
    }

    public long getLong(int index){
        return m_long[index];
    }

    public int getLength(){
        return m_long.length;
    }
}
