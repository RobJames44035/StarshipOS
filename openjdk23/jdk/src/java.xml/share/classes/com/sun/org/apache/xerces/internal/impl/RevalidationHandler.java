/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl;

import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentFilter;
/**
 * DOM Revalidation handler adds additional functionality to XMLDocumentHandler
 *
 * @xerces.internal
 * @author Elena Litani, IBM
 */
public interface RevalidationHandler extends XMLDocumentFilter {

    /**
     * Character content.
     *
     * @param data   The character data.
     * @param augs   Augmentations
     * @return True if data is whitespace only
     */
    public boolean characterData(String data, Augmentations augs);


} // interface DOMRevalidationHandler
