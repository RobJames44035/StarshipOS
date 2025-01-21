/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.DOMImplementation;

public class DocumentImpl implements Document, Node {

    public short getNodeType() {
        return 9; //DOCUMENT_NODE = 9
    }

    public org.w3c.dom.Document getOwnerDocument() {
        return null;
    }

    public Node getFirstChild() {
        return null;
    }

    public String getPrefix() {
        return "TestPrefix";
    }

    public String getLocalName() {
        return "LocalName";
    }

    public boolean hasAttributes() {
        return false;
    }

    public Node renameNode(Node n, String namespaceURI, String name) {
        return n;
    }

    public org.w3c.dom.DocumentType getDoctype() {
        return null;
    }

    public DOMImplementation getImplementation() {
        return DOMImplementationImpl.getDOMImplementation();
    }

}
