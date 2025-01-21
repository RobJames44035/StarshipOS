/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.xpath.regex;

/**
 * @xerces.internal
 *
 * @author TAMURA Kent &lt;kent@trl.ibm.co.jp&gt;
 */
public class ParseException extends RuntimeException {

    /** Serialization version. */
    static final long serialVersionUID = -7012400318097691370L;

    final int location;

    /*
    public ParseException(String mes) {
        this(mes, -1);
    }
    */
    /**
     *
     */
    public ParseException(String mes, int location) {
        super(mes);
        this.location = location;
    }

    /**
     *
     * @return -1 if location information is not available.
     */
    public int getLocation() {
        return this.location;
    }
}
