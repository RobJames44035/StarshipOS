/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dv.dtd;

import com.sun.org.apache.xerces.internal.impl.dv.*;
import com.sun.org.apache.xerces.internal.util.XMLChar;

/**
 * <P>IDREFDatatypeValidator - represents the IDREFS
 * attribute type from XML 1.0 recommendation. The
 * Value Space of IDREF is the set of all strings
 * that match the NCName production and have been
 * used in an XML Document as the value of an element
 * or attribute of Type ID. The Lexical space of
 * IDREF is the set of strings that match the NCName
 * production.</P>
 * <P>The Value space of IDREF is scoped to a specific
 * instance document</P>
 *
 * @xerces.internal
 *
 * @author Jeffrey Rodriguez, IBM
 * @author Sandy Gao, IBM
 *
 */
public class IDREFDatatypeValidator implements DatatypeValidator {

    // construct an IDREF datatype validator
    public IDREFDatatypeValidator() {
    }

    /**
     * Checks that "content" string is valid IDREF value.
     * If invalid a Datatype validation exception is thrown.
     *
     * @param content       the string value that needs to be validated
     * @param context       the validation context
     * @throws InvalidDatatypeException if the content is
     *         invalid according to the rules for the validators
     * @see InvalidDatatypeValueException
     */
    public void validate(String content, ValidationContext context) throws InvalidDatatypeValueException {

        //Check if is valid key-[81] EncName ::= [A-Za-z] ([A-Za-z0-9._] | '-')*
        if(context.useNamespaces()) {
            if (!XMLChar.isValidNCName(content)) {
                throw new InvalidDatatypeValueException("IDREFInvalidWithNamespaces", new Object[]{content});
            }
        }
        else {
            if (!XMLChar.isValidName(content)) {
                throw new InvalidDatatypeValueException("IDREFInvalid", new Object[]{content});
            }
        }

        context.addIdRef(content);

    }

}
