/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.keys.content;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RetrievalMethod extends SignatureElementProxy implements KeyInfoContent {

    /** DSA retrieval */
    public static final String TYPE_DSA = Constants.SignatureSpecNS + "DSAKeyValue";
    /** RSA retrieval */
    public static final String TYPE_RSA = Constants.SignatureSpecNS + "RSAKeyValue";
    /** PGP retrieval */
    public static final String TYPE_PGP = Constants.SignatureSpecNS + "PGPData";
    /** SPKI retrieval */
    public static final String TYPE_SPKI = Constants.SignatureSpecNS + "SPKIData";
    /** MGMT retrieval */
    public static final String TYPE_MGMT = Constants.SignatureSpecNS + "MgmtData";
    /** X509 retrieval */
    public static final String TYPE_X509 = Constants.SignatureSpecNS + "X509Data";
    /** RAWX509 retrieval */
    public static final String TYPE_RAWX509 = Constants.SignatureSpecNS + "rawX509Certificate";

    /**
     * Constructor RetrievalMethod
     *
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public RetrievalMethod(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);
    }

    /**
     * Constructor RetrievalMethod
     *
     * @param doc
     * @param URI
     * @param transforms
     * @param Type
     */
    public RetrievalMethod(Document doc, String URI, Transforms transforms, String Type) {
        super(doc);

        setLocalAttribute(Constants._ATT_URI, URI);

        if (Type != null) {
            setLocalAttribute(Constants._ATT_TYPE, Type);
        }

        if (transforms != null) {
            appendSelf(transforms);
            addReturnToSelf();
        }
    }

    /**
     * Method getURIAttr
     *
     * @return the URI attribute
     */
    public Attr getURIAttr() {
        return getElement().getAttributeNodeNS(null, Constants._ATT_URI);
    }

    /**
     * Method getURI
     *
     * @return URI string
     */
    public String getURI() {
        return getLocalAttribute(Constants._ATT_URI);
    }

    /** @return the type*/
    public String getType() {
        return getLocalAttribute(Constants._ATT_TYPE);
    }

    /**
     * Method getTransforms
     *
     * @throws XMLSecurityException
     * @return the transformations
     */
    public Transforms getTransforms() throws XMLSecurityException {
        try {
            Element transformsElem =
                XMLUtils.selectDsNode(
                    getFirstChild(), Constants._TAG_TRANSFORMS, 0);

            if (transformsElem != null) {
                return new Transforms(transformsElem, this.baseURI);
            }

            return null;
        } catch (XMLSignatureException ex) {
            throw new XMLSecurityException(ex);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getBaseLocalName() {
        return Constants._TAG_RETRIEVALMETHOD;
    }
}
