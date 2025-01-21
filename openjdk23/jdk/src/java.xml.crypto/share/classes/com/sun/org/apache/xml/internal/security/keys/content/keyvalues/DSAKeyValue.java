/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.keys.content.keyvalues;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DSAKeyValue extends SignatureElementProxy implements KeyValueContent {

    /**
     * Constructor DSAKeyValue
     *
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public DSAKeyValue(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);
    }

    /**
     * Constructor DSAKeyValue
     *
     * @param doc
     * @param P
     * @param Q
     * @param G
     * @param Y
     */
    public DSAKeyValue(Document doc, BigInteger P, BigInteger Q, BigInteger G, BigInteger Y) {
        super(doc);

        addReturnToSelf();
        this.addBigIntegerElement(P, Constants._TAG_P);
        this.addBigIntegerElement(Q, Constants._TAG_Q);
        this.addBigIntegerElement(G, Constants._TAG_G);
        this.addBigIntegerElement(Y, Constants._TAG_Y);
    }

    /**
     * Constructor DSAKeyValue
     *
     * @param doc
     * @param key
     * @throws IllegalArgumentException
     */
    public DSAKeyValue(Document doc, Key key) throws IllegalArgumentException {
        super(doc);

        addReturnToSelf();

        if (key instanceof DSAPublicKey) {
            DSAParams params = ((DSAPublicKey) key).getParams();
            this.addBigIntegerElement(params.getP(), Constants._TAG_P);
            this.addBigIntegerElement(params.getQ(), Constants._TAG_Q);
            this.addBigIntegerElement(params.getG(), Constants._TAG_G);
            this.addBigIntegerElement(((DSAPublicKey) key).getY(), Constants._TAG_Y);
        } else {
            Object[] exArgs = { Constants._TAG_DSAKEYVALUE, key.getClass().getName() };

            throw new IllegalArgumentException(I18n.translate("KeyValue.IllegalArgument", exArgs));
        }
    }

    /** {@inheritDoc} */
    @Override
    public PublicKey getPublicKey() throws XMLSecurityException {
        try {
            DSAPublicKeySpec pkspec =
                new DSAPublicKeySpec(
                    this.getBigIntegerFromChildElement(
                        Constants._TAG_Y, Constants.SignatureSpecNS
                    ),
                    this.getBigIntegerFromChildElement(
                        Constants._TAG_P, Constants.SignatureSpecNS
                    ),
                    this.getBigIntegerFromChildElement(
                        Constants._TAG_Q, Constants.SignatureSpecNS
                    ),
                    this.getBigIntegerFromChildElement(
                        Constants._TAG_G, Constants.SignatureSpecNS
                    )
                );
            KeyFactory dsaFactory = KeyFactory.getInstance("DSA");

            return dsaFactory.generatePublic(pkspec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new XMLSecurityException(ex);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getBaseLocalName() {
        return Constants._TAG_DSAKEYVALUE;
    }
}
