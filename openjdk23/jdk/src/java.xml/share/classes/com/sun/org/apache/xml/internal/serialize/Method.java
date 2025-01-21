/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xml.internal.serialize;


/**
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 * @see OutputFormat
 *
 * @deprecated As of JDK 9, Xerces 2.9.0, Xerces DOM L3 Serializer implementation
 * is replaced by that of Xalan. Main class
 * {@link com.sun.org.apache.xml.internal.serialize.DOMSerializerImpl} is replaced
 * by {@link com.sun.org.apache.xml.internal.serializer.dom3.LSSerializerImpl}.
 */
@Deprecated
public final class Method
{


    /**
     * The output method for XML documents.
     */
    public static final String XML = "xml";


    /**
     * The output method for HTML documents.
     */
    public static final String HTML = "html";


    /**
     * The output method for HTML documents as XHTML.
     */
    public static final String XHTML = "xhtml";


    /**
     * The output method for text documents.
     */
    public static final String TEXT = "text";


    /**
     * The output method for FO documents as PDF.
     */
    public static final String FOP = "fop";


}
