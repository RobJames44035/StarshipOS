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
 * Represent the schema type "integer"
 *
 * @xerces.internal
 *
 * @author Sandy Gao, IBM
 *
 */
public class IntegerDV extends DecimalDV {

    public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
        try {
            return new XDecimal(content, true);
        } catch (NumberFormatException nfe) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{content, "integer"});
        }
    }

} // class EntityDV
