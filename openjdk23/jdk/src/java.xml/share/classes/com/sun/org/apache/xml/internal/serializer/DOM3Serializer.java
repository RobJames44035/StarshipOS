/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
/*
 * $Id:  $
 */

package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.ls.LSSerializerFilter;

/**
 * Interface for a DOM serializer capable of serializing DOMs as specified in
 * the DOM Level 3 Save Recommendation.
 * <p>
 * The DOM3Serializer is a facet of a serializer and is obtained from the
 * asDOM3Serializer() method of the org.apache.xml.serializer.Serializer interface.
 * A serializer may or may not support a level 3 DOM serializer, if it does not then the
 * return value from asDOM3Serializer() is null.
 * <p>
 * Example:
 * <pre>
 * Document     doc;
 * Serializer   ser;
 * OutputStream os;
 * DOMErrorHandler handler;
 *
 * ser = ...;
 * os = ...;
 * handler = ...;
 *
 * ser.setOutputStream( os );
 * DOM3Serialzier dser = (DOM3Serialzier)ser.asDOM3Serializer();
 * dser.setErrorHandler(handler);
 * dser.serialize(doc);
 * </pre>
 *
 * @see org.apache.xml.serializer.Serializer
 *
 * @xsl.usage general
 *
 */
public interface DOM3Serializer {
    /**
     * Serializes the Level 3 DOM node. Throws an exception only if an I/O
     * exception occured while serializing.
     *
     * This interface is a public API.
     *
     * @param node the Level 3 DOM node to serialize
     * @throws IOException if an I/O exception occured while serializing
     */
    public void serializeDOM3(Node node) throws IOException;

    /**
     * Sets a DOMErrorHandler on the DOM Level 3 Serializer.
     *
     * This interface is a public API.
     *
     * @param handler the Level 3 DOMErrorHandler
     */
    public void setErrorHandler(DOMErrorHandler handler);

    /**
     * Returns a DOMErrorHandler set on the DOM Level 3 Serializer.
     *
     * This interface is a public API.
     *
     * @return A Level 3 DOMErrorHandler
     */
    public DOMErrorHandler getErrorHandler();

    /**
     * Sets a LSSerializerFilter on the DOM Level 3 Serializer to filter nodes
     * during serialization.
     *
     * This interface is a public API.
     *
     * @param filter the Level 3 LSSerializerFilter
     */
    public void setNodeFilter(LSSerializerFilter filter);

    /**
     * Returns a LSSerializerFilter set on the DOM Level 3 Serializer to filter nodes
     * during serialization.
     *
     * This interface is a public API.
     *
     * @return The Level 3 LSSerializerFilter
     */
    public LSSerializerFilter getNodeFilter();

    /**
     * Sets the new line character to be used during serialization
     * @param newLine a String that is the end-of-line character sequence to be
     * used in serialization.
     */
    public void setNewLine(String newLine);
}
