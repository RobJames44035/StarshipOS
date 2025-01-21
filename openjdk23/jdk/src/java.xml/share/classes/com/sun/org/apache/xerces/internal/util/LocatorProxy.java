/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.util;

import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import org.xml.sax.Locator;
import org.xml.sax.ext.Locator2;

/**
 * Wraps {@link XMLLocator} and make it look like a SAX {@link Locator}.
 *
 * @author Arnaud Le Hors, IBM
 * @author Andy Clark, IBM
 *
 */
public class LocatorProxy implements Locator2 {

    //
    // Data
    //

    /** XML locator. */
    private final XMLLocator fLocator;

    //
    // Constructors
    //

    /** Constructs an XML locator proxy. */
    public LocatorProxy(XMLLocator locator) {
        fLocator = locator;
    }

    //
    // Locator methods
    //

    /** Public identifier. */
    public String getPublicId() {
        return fLocator.getPublicId();
    }

    /** System identifier. */
    public String getSystemId() {
        return fLocator.getExpandedSystemId();
    }

    /** Line number. */
    public int getLineNumber() {
        return fLocator.getLineNumber();
    }

    /** Column number. */
    public int getColumnNumber() {
        return fLocator.getColumnNumber();
    }

    //
    // Locator2 methods
    //

    public String getXMLVersion() {
        return fLocator.getXMLVersion();
    }

    public String getEncoding() {
        return fLocator.getEncoding();
    }

}
