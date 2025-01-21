/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.xs.util;

import com.sun.org.apache.xerces.internal.impl.dv.xs.XSSimpleTypeDecl;
import com.sun.org.apache.xerces.internal.impl.xs.XSComplexTypeDecl;
import com.sun.org.apache.xerces.internal.xs.XSSimpleTypeDefinition;
import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;

/**
 * Class defining utility/helper methods to support XML Schema 1.0 implementation.
 *
 * @xerces.internal
 *
 * @author Mukul Gandhi, IBM
 */
public class XS10TypeHelper {

    /*
     * Class constructor.
     */
    private XS10TypeHelper() {
       // a private constructor, to prohibit instantiating this class from an outside class/application.
       // this is a good practice, since all methods of this class are "static".
    }

    /*
     * Get name of an XSD type definition as a string value (which will typically be the value of "name" attribute of a
     * type definition, or an internal name determined by the validator for anonymous types).
     */
    public static String getSchemaTypeName(XSTypeDefinition typeDefn) {

        String typeNameStr;
        if (typeDefn instanceof XSSimpleTypeDefinition) {
            typeNameStr = ((XSSimpleTypeDecl) typeDefn).getTypeName();
        }
        else {
            typeNameStr = ((XSComplexTypeDecl) typeDefn).getTypeName();
        }

        return typeNameStr;

    } // getSchemaTypeName


} // class XS10TypeHelper
