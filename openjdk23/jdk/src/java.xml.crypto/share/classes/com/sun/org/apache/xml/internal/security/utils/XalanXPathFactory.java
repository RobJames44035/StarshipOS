/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.utils;


/**
 * A Factory to return a XalanXPathAPI instance.
 */
public class XalanXPathFactory extends XPathFactory {

    /**
     * Get a new XPathAPI instance
     */
    public XPathAPI newXPathAPI() {
        return new XalanXPathAPI();
    }
}
