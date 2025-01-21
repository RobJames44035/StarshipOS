/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.dom;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.*;

/**
 * Our own document implementation, which knows how to create an element
 * with PSVI information.
 *
 * @xerces.internal
 *
 * @author Sandy Gao, IBM
 *
 */
public class PSVIDocumentImpl extends DocumentImpl {

    /** Serialization version. */
    static final long serialVersionUID = -8822220250676434522L;

    /**
     * Create a document.
     */
    public PSVIDocumentImpl() {
        super();
    }

    /**
     * For DOM2 support.
     * The createDocument factory method is in DOMImplementation.
     */
    public PSVIDocumentImpl(DocumentType doctype) {
        super(doctype);
    }

    /**
     * Deep-clone a document, including fixing ownerDoc for the cloned
     * children. Note that this requires bypassing the WRONG_DOCUMENT_ERR
     * protection. I've chosen to implement it by calling importNode
     * which is DOM Level 2.
     *
     * @return org.w3c.dom.Node
     * @param deep boolean, iff true replicate children
     */
    public Node cloneNode(boolean deep) {

        PSVIDocumentImpl newdoc = new PSVIDocumentImpl();
        callUserDataHandlers(this, newdoc, UserDataHandler.NODE_CLONED);
        cloneNode(newdoc, deep);

        // experimental
        newdoc.mutationEvents = mutationEvents;

        return newdoc;

    } // cloneNode(boolean):Node

    /**
     * Retrieve information describing the abilities of this particular
     * DOM implementation. Intended to support applications that may be
     * using DOMs retrieved from several different sources, potentially
     * with different underlying representations.
     */
    public DOMImplementation getImplementation() {
        // Currently implemented as a singleton, since it's hardcoded
        // information anyway.
        return PSVIDOMImplementationImpl.getDOMImplementation();
    }

    /**
     * Create an element with PSVI information
     */
    public Element createElementNS(String namespaceURI, String qualifiedName)
        throws DOMException {
        return new PSVIElementNSImpl(this, namespaceURI, qualifiedName);
    }

    /**
     * Create an element with PSVI information
     */
    public Element createElementNS(String namespaceURI, String qualifiedName,
                                   String localpart) throws DOMException {
        return new PSVIElementNSImpl(this, namespaceURI, qualifiedName, localpart);
    }

    /**
     * Create an attribute with PSVI information
     */
    public Attr createAttributeNS(String namespaceURI, String qualifiedName)
        throws DOMException {
        return new PSVIAttrNSImpl(this, namespaceURI, qualifiedName);
    }

    /**
     * Create an attribute with PSVI information
     */
    public Attr createAttributeNS(String namespaceURI, String qualifiedName,
                                  String localName) throws DOMException {
        return new PSVIAttrNSImpl(this, namespaceURI, qualifiedName, localName);
    }

    /**
     *
     * The configuration used when <code>Document.normalizeDocument</code> is
     * invoked.
     * @since DOM Level 3
     */
    public DOMConfiguration getDomConfig(){
        super.getDomConfig();
        return fConfiguration;
    }

    // REVISIT: Forbid serialization of PSVI DOM until
    // we support object serialization of grammars -- mrglavas

    private void writeObject(ObjectOutputStream out)
        throws IOException {
        throw new NotSerializableException(getClass().getName());
        }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
        throw new NotSerializableException(getClass().getName());
    }

} // class PSVIDocumentImpl
