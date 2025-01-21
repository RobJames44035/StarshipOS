/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.util;

/**
 * All internalized xml symbols. They can be compared using "==".
 *
 * @author Sandy Gao, IBM
 */
public class XMLSymbols {

    // public constructor.
    public XMLSymbols(){}

    //==========================
    // Commonly used strings
    //==========================

    /**
     * The empty string.
     */
    public final static String EMPTY_STRING = "".intern();

    //==========================
    // Namespace prefixes/uris
    //==========================

    /**
     * The internalized "xml" prefix.
     */
    public final static String PREFIX_XML = "xml".intern();

    /**
     * The internalized "xmlns" prefix.
     */
    public final static String PREFIX_XMLNS = "xmlns".intern();

    //==========================
    // DTD symbols
    //==========================

    /** Symbol: "ANY". */
    public static final String fANYSymbol = "ANY".intern();

    /** Symbol: "CDATA". */
    public static final String fCDATASymbol = "CDATA".intern();

    /** Symbol: "ID". */
    public static final String fIDSymbol = "ID".intern();

    /** Symbol: "IDREF". */
    public static final String fIDREFSymbol = "IDREF".intern();

    /** Symbol: "IDREFS". */
    public static final String fIDREFSSymbol = "IDREFS".intern();

    /** Symbol: "ENTITY". */
    public static final String fENTITYSymbol = "ENTITY".intern();

    /** Symbol: "ENTITIES". */
    public static final String fENTITIESSymbol = "ENTITIES".intern();

    /** Symbol: "NMTOKEN". */
    public static final String fNMTOKENSymbol = "NMTOKEN".intern();

    /** Symbol: "NMTOKENS". */
    public static final String fNMTOKENSSymbol = "NMTOKENS".intern();

    /** Symbol: "NOTATION". */
    public static final String fNOTATIONSymbol = "NOTATION".intern();

    /** Symbol: "ENUMERATION". */
    public static final String fENUMERATIONSymbol = "ENUMERATION".intern();

    /** Symbol: "#IMPLIED. */
    public static final String fIMPLIEDSymbol = "#IMPLIED".intern();

    /** Symbol: "#REQUIRED". */
    public static final String fREQUIREDSymbol = "#REQUIRED".intern();

    /** Symbol: "#FIXED". */
    public static final String fFIXEDSymbol = "#FIXED".intern();


}
