/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class SignatureElementProxy
 *
 */
public abstract class SignatureElementProxy extends ElementProxy {

    protected SignatureElementProxy() {
    }

    /**
     * Constructor SignatureElementProxy
     *
     * @param doc
     */
    public SignatureElementProxy(Document doc) {
        if (doc == null) {
            throw new RuntimeException("Document is null");
        }

        setDocument(doc);
        setElement(XMLUtils.createElementInSignatureSpace(doc,
                this.getBaseLocalName()));
    }

    /**
     * Constructor SignatureElementProxy
     *
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public SignatureElementProxy(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);

    }

    /** {@inheritDoc} */
    @Override
    public String getBaseNamespace() {
        return Constants.SignatureSpecNS;
    }
}
