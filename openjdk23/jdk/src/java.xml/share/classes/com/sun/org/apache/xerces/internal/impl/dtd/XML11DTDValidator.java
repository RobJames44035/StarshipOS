/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl.dtd;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;

/**
 * This allows the validator to correctlyhandle XML 1.1
 * documents.
 *
 * @xerces.internal
 *
 * @author Neil Graham
 */
public class XML11DTDValidator extends XMLDTDValidator {

    //
    // Constants
    //

    protected final static String DTD_VALIDATOR_PROPERTY =
        Constants.XERCES_PROPERTY_PREFIX+Constants.DTD_VALIDATOR_PROPERTY;

    //
    // Constructors
    //

    /** Default constructor. */
    public XML11DTDValidator() {

        super();
    } // <init>()

    // overridden so that this class has access to the same
    // grammarBucket as the corresponding DTDProcessor
    // will try and use...
    public void reset(XMLComponentManager manager) {
        XMLDTDValidator curr = null;
        if((curr = (XMLDTDValidator)manager.getProperty(DTD_VALIDATOR_PROPERTY)) != null &&
                curr != this) {
            fGrammarBucket = curr.getGrammarBucket();
        }
        super.reset(manager);
    } //reset(XMLComponentManager)

    protected void init() {
        if(fValidation || fDynamicValidation) {
            super.init();
            // now overwrite some entries in parent:

            try {
                fValID       = fDatatypeValidatorFactory.getBuiltInDV("XML11ID");
                fValIDRef    = fDatatypeValidatorFactory.getBuiltInDV("XML11IDREF");
                fValIDRefs   = fDatatypeValidatorFactory.getBuiltInDV("XML11IDREFS");
                fValNMTOKEN  = fDatatypeValidatorFactory.getBuiltInDV("XML11NMTOKEN");
                fValNMTOKENS = fDatatypeValidatorFactory.getBuiltInDV("XML11NMTOKENS");

            }
            catch (Exception e) {
                // should never happen
                e.printStackTrace(System.err);
            }
        }
    } // init()

} // class XML11DTDValidator
