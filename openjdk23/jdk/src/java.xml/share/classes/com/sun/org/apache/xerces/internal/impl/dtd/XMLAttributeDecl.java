/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dtd;

import com.sun.org.apache.xerces.internal.xni.QName;

/**

 */
public class XMLAttributeDecl {

    //
    // Data
    //

    /** name */
    public final QName name = new QName();

    /** simpleType */
    public final XMLSimpleType simpleType = new XMLSimpleType();

    /** optional */
    public boolean optional;

    //
    // Methods
    //

    /**
     * setValues
     *
     * @param name
     * @param simpleType
     * @param optional
     */
    public void setValues(QName name, XMLSimpleType simpleType, boolean optional) {
        this.name.setValues(name);
        this.simpleType.setValues(simpleType);
        this.optional   = optional;
    } // setValues

    /**
     * clear
     */
    public void clear() {
        this.name.clear();
        this.simpleType.clear();
        this.optional   = false;
    } // clear

} // class XMLAttributeDecl
