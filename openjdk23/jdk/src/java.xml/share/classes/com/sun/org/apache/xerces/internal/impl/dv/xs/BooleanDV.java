/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dv.xs;

import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;

/**
 * Represent the schema type "boolean"
 *
 * @xerces.internal
 *
 * @author Neeraj Bajaj, Sun Microsystems, inc.
 * @author Sandy Gao, IBM
 *
 */
public class BooleanDV extends TypeValidator{

    private static final String fValueSpace[] = {"false", "true", "0", "1"};

    public short getAllowedFacets(){
        return (XSSimpleTypeDecl.FACET_PATTERN | XSSimpleTypeDecl.FACET_WHITESPACE);
    }

    public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
        Boolean ret = null;

        if (content.equals(fValueSpace[0]) || content.equals(fValueSpace[2]))
            ret = Boolean.FALSE;
        else if (content.equals(fValueSpace[1]) || content.equals(fValueSpace[3]))
            ret = Boolean.TRUE;
        else
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{content, "boolean"});
        return ret;
    }

} // class BooleanDV
