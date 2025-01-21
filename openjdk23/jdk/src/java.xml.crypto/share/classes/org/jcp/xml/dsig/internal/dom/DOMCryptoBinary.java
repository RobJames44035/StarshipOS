/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package org.jcp.xml.dsig.internal.dom;

import java.math.BigInteger;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMCryptoContext;

import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * A DOM-based representation of the XML {@code CryptoBinary} simple type
 * as defined in the W3C specification for XML-Signature Syntax and Processing.
 * The XML Schema Definition is defined as:
 *
 * <pre>{@code
 * <simpleType name="CryptoBinary">
 *   <restriction base = "base64Binary">
 *   </restriction>
 * </simpleType>
 * }</pre>
 *
 * @author Sean Mullan
 */
public final class DOMCryptoBinary extends DOMStructure {

    private final BigInteger bigNum;
    private final String value;

    /**
     * Create a {@code DOMCryptoBinary} instance from the specified
     * {@code BigInteger}
     *
     * @param bigNum the arbitrary-length integer
     * @throws NullPointerException if {@code bigNum} is {@code null}
     */
    public DOMCryptoBinary(BigInteger bigNum) {
        if (bigNum == null) {
            throw new NullPointerException("bigNum is null");
        }
        this.bigNum = bigNum;
        // convert to bitstring
        byte[] bytes = XMLUtils.getBytes(bigNum, bigNum.bitLength());
        value = XMLUtils.encodeToString(bytes);
    }

    /**
     * Creates a {@code DOMCryptoBinary} from a node.
     *
     * @param cbNode a CryptoBinary text node
     * @throws MarshalException if value cannot be decoded (invalid format)
     */
    public DOMCryptoBinary(Node cbNode) throws MarshalException {
        value = cbNode.getNodeValue();
        try {
            bigNum = new BigInteger(1, XMLUtils.decode(((Text) cbNode).getData()));
        } catch (Exception ex) {
            throw new MarshalException(ex);
        }
    }

    /**
     * Returns the {@code BigInteger} that this object contains.
     *
     * @return the {@code BigInteger} that this object contains
     */
    public BigInteger getBigNum() {
        return bigNum;
    }

    @Override
    public void marshal(Node parent, String prefix, DOMCryptoContext context)
        throws MarshalException {
        parent.appendChild
            (DOMUtils.getOwnerDocument(parent).createTextNode(value));
    }
}
