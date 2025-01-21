/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.keys.content.x509;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLX509CRL extends SignatureElementProxy implements XMLX509DataContent {

    /**
     * Constructor XMLX509CRL
     *
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public XMLX509CRL(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);
    }

    /**
     * Constructor X509CRL
     *
     * @param doc
     * @param crlBytes
     */
    public XMLX509CRL(Document doc, byte[] crlBytes) {
        super(doc);

        this.addBase64Text(crlBytes);
    }

    /**
     * Method getCRLBytes
     *
     * @return the CRL bytes
     * @throws XMLSecurityException
     */
    public byte[] getCRLBytes() throws XMLSecurityException {
        return this.getBytesFromTextChild();
    }

    /** {@inheritDoc} */
    @Override
    public String getBaseLocalName() {
        return Constants._TAG_X509CRL;
    }
}
