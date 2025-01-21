/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.util;

import com.sun.org.apache.xerces.internal.xni.XMLLocator;

/**
 * <p>A light wrapper around an <code>XMLLocator</code>.</p>
 *
 * @author Michael Glavassevich, IBM
 *
 * @version $Id: XMLLocatorWrapper.java 533423 2007-04-28 20:47:15Z mrglavas $
 */
public final class XMLLocatorWrapper implements XMLLocator {

    private XMLLocator fLocator = null;

    public XMLLocatorWrapper() {}

    public void setLocator(XMLLocator locator) {
        fLocator = locator;
    }

    public XMLLocator getLocator() {
        return fLocator;
    }

    /*
     * XMLLocator methods
     */

    public String getPublicId() {
        if (fLocator != null) {
            return fLocator.getPublicId();
        }
        return null;
    }

    public String getLiteralSystemId() {
        if (fLocator != null) {
            return fLocator.getLiteralSystemId();
        }
        return null;
    }

    public String getBaseSystemId() {
        if (fLocator != null) {
            return fLocator.getBaseSystemId();
        }
        return null;
    }

    public String getExpandedSystemId() {
        if (fLocator != null) {
            return fLocator.getExpandedSystemId();
        }
        return null;
    }

    public int getLineNumber() {
        if (fLocator != null) {
            return fLocator.getLineNumber();
        }
        return -1;
    }

    public int getColumnNumber() {
        if (fLocator != null) {
            return fLocator.getColumnNumber();
        }
        return -1;
    }

    public int getCharacterOffset() {
        if (fLocator != null) {
            return fLocator.getCharacterOffset();
        }
        return -1;
    }

    public String getEncoding() {
        if (fLocator != null) {
            return fLocator.getEncoding();
        }
        return null;
    }

    public String getXMLVersion() {
        if (fLocator != null) {
            return fLocator.getXMLVersion();
        }
        return null;
    }
}
