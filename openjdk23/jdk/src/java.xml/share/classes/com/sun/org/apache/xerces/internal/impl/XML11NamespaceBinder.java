/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl;


/**
 * This class performs namespace binding on the startElement and endElement
 * method calls in accordance with Namespaces in XML 1.1.  It extends the standard,
 * Namespace-1.0-compliant binder in order to do this.
 *
 * @xerces.internal
 *
 * @author Neil Graham, IBM
 *
 */
public class XML11NamespaceBinder extends XMLNamespaceBinder {

    //
    // Constants
    //

    //
    // Data
    //

    //
    // Constructors
    //

    /** Default constructor. */
    public XML11NamespaceBinder() {
    } // <init>()
    //
    // Public methods
    //

    //
    // Protected methods
    //

    // returns true iff the given prefix is bound to "" *and*
    // this is disallowed by the version of XML namespaces in use.
    protected boolean prefixBoundToNullURI(String uri, String localpart) {
        return false;
    } // prefixBoundToNullURI(String, String):  boolean

} // class XML11NamespaceBinder
