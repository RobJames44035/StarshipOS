/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.util;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;

/**
 * <p>An <code>XMLInputSource</code> analogue to <code>javax.xml.transform.stax.StAXSource</code>.</p>
 *
 */
public final class StAXInputSource extends XMLInputSource {

    private final XMLStreamReader fStreamReader;
    private final XMLEventReader fEventReader;
    private final boolean fConsumeRemainingContent;

    public StAXInputSource(XMLStreamReader source) {
        this(source, false);
    }

    public StAXInputSource(XMLStreamReader source, boolean consumeRemainingContent) {
        super(null, source.getLocation().getSystemId(), null, false);
        if (source == null) {
            throw new IllegalArgumentException("XMLStreamReader parameter cannot be null.");
        }
        fStreamReader = source;
        fEventReader = null;
        fConsumeRemainingContent = consumeRemainingContent;
    }

    public StAXInputSource(XMLEventReader source) {
        this(source, false);
    }

    public StAXInputSource(XMLEventReader source, boolean consumeRemainingContent) {
        super(null, getEventReaderSystemId(source), null, false);
        if (source == null) {
            throw new IllegalArgumentException("XMLEventReader parameter cannot be null.");
        }
        fStreamReader = null;
        fEventReader = source;
        fConsumeRemainingContent = consumeRemainingContent;
    }

    public XMLStreamReader getXMLStreamReader() {
        return fStreamReader;
    }

    public XMLEventReader getXMLEventReader() {
        return fEventReader;
    }

    public boolean shouldConsumeRemainingContent() {
        return fConsumeRemainingContent;
    }

    public void setSystemId(String systemId){
        throw new UnsupportedOperationException("Cannot set the system ID on a StAXInputSource");
    }

    private static String getEventReaderSystemId(XMLEventReader reader) {
        try {
            if (reader != null) {
                return reader.peek().getLocation().getSystemId();
            }
        }
        catch (XMLStreamException e) {}
        return null;
    }

} // StAXInputSource
