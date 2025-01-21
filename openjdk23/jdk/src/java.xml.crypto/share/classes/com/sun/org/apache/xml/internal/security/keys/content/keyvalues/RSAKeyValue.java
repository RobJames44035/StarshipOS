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
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RSAKeyValue extends SignatureElementProxy implements KeyValueContent {

    /**
     * Constructor RSAKeyValue
     *
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public RSAKeyValue(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);
    }

    /**
     * Constructor RSAKeyValue
     *
     * @param doc
     * @param modulus
     * @param exponent
     */
    public RSAKeyValue(Document doc, BigInteger modulus, BigInteger exponent) {
        super(doc);

        addReturnToSelf();
        this.addBigIntegerElement(modulus, Constants._TAG_MODULUS);
        this.addBigIntegerElement(exponent, Constants._TAG_EXPONENT);
    }

    /**
     * Constructor RSAKeyValue
     *
     * @param doc
     * @param key
     * @throws IllegalArgumentException
     */
    public RSAKeyValue(Document doc, Key key) throws IllegalArgumentException {
        super(doc);

        addReturnToSelf();

        if (key instanceof RSAPublicKey ) {
            this.addBigIntegerElement(
                ((RSAPublicKey) key).getModulus(), Constants._TAG_MODULUS
            );
            this.addBigIntegerElement(
                ((RSAPublicKey) key).getPublicExponent(), Constants._TAG_EXPONENT
            );
        } else {
            Object[] exArgs = { Constants._TAG_RSAKEYVALUE, key.getClass().getName() };

            throw new IllegalArgumentException(I18n.translate("KeyValue.IllegalArgument", exArgs));
        }
    }

    /** {@inheritDoc} */
    @Override
    public PublicKey getPublicKey() throws XMLSecurityException {
        try {
            KeyFactory rsaFactory = KeyFactory.getInstance("RSA");

            RSAPublicKeySpec rsaKeyspec =
                new RSAPublicKeySpec(
                    this.getBigIntegerFromChildElement(
                        Constants._TAG_MODULUS, Constants.SignatureSpecNS
                    ),
                    this.getBigIntegerFromChildElement(
                        Constants._TAG_EXPONENT, Constants.SignatureSpecNS
                    )
                );
            PublicKey pk = rsaFactory.generatePublic(rsaKeyspec);

            return pk;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new XMLSecurityException(ex);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getBaseLocalName() {
        return Constants._TAG_RSAKEYVALUE;
    }
}
