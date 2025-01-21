/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.utils;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Purpose of this class is to enable the XML Parser to keep track of ID
 * attributes. This is done by 'registering' attributes of type ID at the
 * IdResolver.
 * @deprecated
 */
@Deprecated
public final class IdResolver {

    private IdResolver() {
        // we don't allow instantiation
    }

    /**
     * Method registerElementById
     *
     * @param element the element to register
     * @param id the ID attribute
     */
    public static void registerElementById(Element element, Attr id) {
        element.setIdAttributeNode(id, true);
    }

    /**
     * Method getElementById
     *
     * @param doc the document
     * @param id the value of the ID
     * @return the element obtained by the id, or null if it is not found.
     */
    public static Element getElementById(Document doc, String id) {
        return doc.getElementById(id);
    }

}
