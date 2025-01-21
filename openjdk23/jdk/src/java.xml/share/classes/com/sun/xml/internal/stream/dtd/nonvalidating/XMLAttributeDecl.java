/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.xml.internal.stream.dtd.nonvalidating;

import com.sun.org.apache.xerces.internal.xni.QName;

/**
 */
public class XMLAttributeDecl {


    /** name */
    public final QName name = new QName();

    /** simpleType */
    public final XMLSimpleType simpleType = new XMLSimpleType();

    /** optional */
    public boolean optional;


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
    }

    /**
     * clear
     */
    public void clear() {
        this.name.clear();
        this.simpleType.clear();
        this.optional   = false;
    }

}
