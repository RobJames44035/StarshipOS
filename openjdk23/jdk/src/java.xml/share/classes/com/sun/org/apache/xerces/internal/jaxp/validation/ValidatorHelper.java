/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.jaxp.validation;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.xml.sax.SAXException;

/**
 * <p>Instances of ValidatorHelper are able to validate
 * specific source and result types.</p>
 *
 * @author Michael Glavassevich, IBM
 */
interface ValidatorHelper {

    public void validate(Source source, Result result)
        throws SAXException, IOException;
}
