/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.transforms.implementations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315Excl;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315ExclOmitComments;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Class TransformC14NExclusive
 *
 */
public class TransformC14NExclusive extends TransformSpi {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String engineGetURI() {
        return Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected XMLSignatureInput enginePerformTransform(
        XMLSignatureInput input, OutputStream os, Element transformElement,
        String baseURI, boolean secureValidation
    ) throws CanonicalizationException {
        try {
            String inclusiveNamespaces = null;

            if (length(transformElement,
                InclusiveNamespaces.ExclusiveCanonicalizationNamespace,
                InclusiveNamespaces._TAG_EC_INCLUSIVENAMESPACES) == 1
            ) {
                Element inclusiveElement =
                    XMLUtils.selectNode(
                        transformElement.getFirstChild(),
                        InclusiveNamespaces.ExclusiveCanonicalizationNamespace,
                        InclusiveNamespaces._TAG_EC_INCLUSIVENAMESPACES,
                        0
                    );

                inclusiveNamespaces =
                    new InclusiveNamespaces(
                        inclusiveElement, baseURI).getInclusiveNamespaces();
            }

            Canonicalizer20010315Excl c14n = getCanonicalizer();

            if (os == null && (input.isOctetStream() || input.isElement() || input.isNodeSet())) {
                try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
                    c14n.engineCanonicalize(input, inclusiveNamespaces, writer, secureValidation);
                    writer.flush();
                    XMLSignatureInput output = new XMLSignatureInput(writer.toByteArray());
                    output.setSecureValidation(secureValidation);
                    return output;
                } catch (IOException ex) {
                    throw new CanonicalizationException("empty", new Object[] {ex.getMessage()});
                }
            } else {
                c14n.engineCanonicalize(input, inclusiveNamespaces, os, secureValidation);
                XMLSignatureInput output = new XMLSignatureInput((byte[])null);
                output.setSecureValidation(secureValidation);
                output.setOutputStream(os);
                return output;
            }
        } catch (XMLSecurityException ex) {
            throw new CanonicalizationException(ex);
        }
    }

    protected Canonicalizer20010315Excl getCanonicalizer() {
        return new Canonicalizer20010315ExclOmitComments();
    }

    /**
     * Method length
     *
     * @param namespace
     * @param localname
     * @return the number of elements {namespace}:localname under this element
     */
    private int length(Element element, String namespace, String localname) {
        int number = 0;
        Node sibling = element.getFirstChild();
        while (sibling != null) {
            if (localname.equals(sibling.getLocalName())
                && namespace.equals(sibling.getNamespaceURI())) {
                number++;
            }
            sibling = sibling.getNextSibling();
        }
        return number;
    }
}
