/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Node;

/**
 * Represents an XML (or HTML) comment.
 *
 * @xerces.internal
 *
 * @since  PR-DOM-Level-1-19980818.
 */
public class CommentImpl
    extends CharacterDataImpl
    implements CharacterData, Comment {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -2685736833408134044L;

    //
    // Constructors
    //

    /** Factory constructor. */
    public CommentImpl(CoreDocumentImpl ownerDoc, String data) {
        super(ownerDoc, data);
    }

    //
    // Node methods
    //

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     */
    public short getNodeType() {
        return Node.COMMENT_NODE;
    }

    /** Returns the node name. */
    public String getNodeName() {
        return "#comment";
    }

} // class CommentImpl
