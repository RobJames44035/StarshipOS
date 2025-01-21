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
public abstract class Signature11ElementProxy extends ElementProxy {

    protected Signature11ElementProxy() {
    }

    /**
     * Constructor Signature11ElementProxy
     *
     * @param doc
     */
    public Signature11ElementProxy(Document doc) {
        if (doc == null) {
            throw new RuntimeException("Document is null");
        }

        setDocument(doc);
        setElement(XMLUtils.createElementInSignature11Space(doc, this.getBaseLocalName()));

        String prefix = ElementProxy.getDefaultPrefix(getBaseNamespace());
        if (prefix == null || prefix.length() == 0) {
            getElement().setAttributeNS(Constants.NamespaceSpecNS, "xmlns", getBaseNamespace());
        } else {
            getElement().setAttributeNS(Constants.NamespaceSpecNS, "xmlns:" + prefix, getBaseNamespace());
        }
    }

    /**
     * Constructor Signature11ElementProxy
     *
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public Signature11ElementProxy(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);

    }

    /** {@inheritDoc} */
    @Override
    public String getBaseNamespace() {
        return Constants.SignatureSpec11NS;
    }
}
