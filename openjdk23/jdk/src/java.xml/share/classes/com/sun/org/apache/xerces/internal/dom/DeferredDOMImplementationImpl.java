/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.DOMImplementation;

/**
 * <p>This DOMImplementation class is description of a particular
 * implementation of the Document Object Model. As such its data is
 * static, shared by all instances of this implementation.</p>
 *
 * <p>This implementation simply extends DOMImplementationImpl to differentiate
 * between the Deferred DOM Implementations and Non-Deferred DOM Implementations.</p>
 *
 * @xerces.internal
 *
 * @author Neil Delima, IBM
 *
 */
public class DeferredDOMImplementationImpl
    extends DOMImplementationImpl {

    //
    // Data
    //

    // static

    /** Dom implementation singleton. */
    static DeferredDOMImplementationImpl singleton = new DeferredDOMImplementationImpl();


    //
    // Public methods
    //

    /** NON-DOM: Obtain and return the single shared object */
    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }
}
