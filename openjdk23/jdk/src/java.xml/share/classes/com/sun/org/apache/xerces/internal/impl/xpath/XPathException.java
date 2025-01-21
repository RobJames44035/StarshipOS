/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.xpath;

/**
 * XPath exception.
 *
 * @xerces.internal
 *
 * @author Andy Clark, IBM
 *
 */
public class XPathException
    extends Exception {

    /** Serialization version. */
    static final long serialVersionUID = -948482312169512085L;

    // Data

    // hold the value of the key this Exception refers to.
    private final String fKey;
    //
    // Constructors
    //

    /** Constructs an exception. */
    public XPathException() {
        super();
        fKey = "c-general-xpath";
    } // <init>()

    /** Constructs an exception with the specified key. */
    public XPathException(String key) {
        super();
        fKey = key;
    } // <init>(String)

    public String getKey() {
        return fKey;
    } // getKey():  String

} // class XPathException
