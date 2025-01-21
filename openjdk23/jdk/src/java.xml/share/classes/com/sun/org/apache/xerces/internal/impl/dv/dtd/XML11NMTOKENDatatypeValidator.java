/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dv.dtd;

import com.sun.org.apache.xerces.internal.impl.dv.*;
import com.sun.org.apache.xerces.internal.util.XML11Char;

/**
 * NMTOKEN datatype validator for NMTokens from XML 1.1.
 *
 * @xerces.internal
 *
 * @author Jeffrey Rodriguez, IBM
 * @author Sandy Gao, IBM
 * @author Neil Graham, IBM
 *
 */
public class XML11NMTOKENDatatypeValidator extends NMTOKENDatatypeValidator {

    // construct a NMTOKEN datatype validator
    public XML11NMTOKENDatatypeValidator() {
        super();
    }

    /**
     * Checks that "content" string is valid NMTOKEN value.
     * If invalid a Datatype validation exception is thrown.
     *
     * @param content       the string value that needs to be validated
     * @param context       the validation context
     * @throws InvalidDatatypeException if the content is
     *         invalid according to the rules for the validators
     * @see InvalidDatatypeValueException
     */
    public void validate(String content, ValidationContext context) throws InvalidDatatypeValueException {
        if (!XML11Char.isXML11ValidNmtoken(content)) {
           throw new InvalidDatatypeValueException("NMTOKENInvalid", new Object[]{content});
       }
   }

} // class XML11NMTOKENDatatypeValidator
