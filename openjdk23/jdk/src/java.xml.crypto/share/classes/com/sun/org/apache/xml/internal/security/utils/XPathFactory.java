/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.utils;


/**
 * A Factory to return an XPathAPI instance. If Xalan is available it returns XalanXPathAPI. If not, then
 * it returns JDKXPathAPI.
 */
public abstract class XPathFactory {

    private static final boolean xalanInstalled;

    static {
        boolean installed = false;
        try {
            Class<?> funcTableClass =
                ClassLoaderUtils.loadClass("com.sun.org.apache.xpath.internal.compiler.FunctionTable", XPathFactory.class);
            if (funcTableClass != null) {
                installed = true;
            }
        } catch (Exception e) { //NOPMD
            //ignore
        }
        xalanInstalled = installed;
    }

    /**
     * Get a new XPathFactory instance
     */
    public static XPathFactory newInstance() {
        // Xalan is available
        if (xalanInstalled && XalanXPathAPI.isInstalled()) {
            return new XalanXPathFactory();
        }
        // Some problem was encountered in fixing up the Xalan FunctionTable so fall back to the
        // JDK implementation
        return new JDKXPathFactory();
    }

    /**
     * Get a new XPathAPI instance
     */
    public abstract XPathAPI newXPathAPI();

}
