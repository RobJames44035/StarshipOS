/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.c14n.helper;

import org.w3c.dom.Attr;

/**
 * Temporary swapped static functions from the normalizer Section
 *
 */
public final class C14nHelper {

    /**
     * Constructor C14nHelper
     */
    private C14nHelper() {
        // don't allow instantiation
    }

    /**
     * Method namespaceIsRelative
     *
     * @param namespace
     * @return true if the given namespace is relative.
     */
    public static boolean namespaceIsRelative(Attr namespace) {
        return !namespaceIsAbsolute(namespace);
    }

    /**
     * Method namespaceIsRelative
     *
     * @param namespaceValue
     * @return true if the given namespace is relative.
     */
    public static boolean namespaceIsRelative(String namespaceValue) {
        return !namespaceIsAbsolute(namespaceValue);
    }

    /**
     * Method namespaceIsAbsolute
     *
     * @param namespace
     * @return true if the given namespace is absolute.
     */
    public static boolean namespaceIsAbsolute(Attr namespace) {
        return namespaceIsAbsolute(namespace.getValue());
    }

    /**
     * Method namespaceIsAbsolute
     *
     * @param namespaceValue
     * @return true if the given namespace is absolute.
     */
    public static boolean namespaceIsAbsolute(String namespaceValue) {
        // assume empty namespaces are absolute
        if (namespaceValue.length() == 0) {
            return true;
        }
        return namespaceValue.indexOf(':') > 0;
    }

}
