/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.algorithms;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Algorithm class which stores the Algorithm URI as a string.
 */
public abstract class Algorithm extends SignatureElementProxy {

    /**
     *
     * @param doc
     * @param algorithmURI is the URI of the algorithm as String
     */
    public Algorithm(Document doc, String algorithmURI) {
        super(doc);
        this.setAlgorithmURI(algorithmURI);
    }

    /**
     * Constructor Algorithm
     *
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public Algorithm(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);
    }

    /**
     * Method getAlgorithmURI
     *
     * @return The URI of the algorithm
     */
    public String getAlgorithmURI() {
        return getLocalAttribute(Constants._ATT_ALGORITHM);
    }

    /**
     * Sets the algorithm's URI as used in the signature.
     *
     * @param algorithmURI is the URI of the algorithm as String
     */
    protected void setAlgorithmURI(String algorithmURI) {
        if (algorithmURI != null) {
            setLocalAttribute(Constants._ATT_ALGORITHM, algorithmURI);
        }
    }
}
