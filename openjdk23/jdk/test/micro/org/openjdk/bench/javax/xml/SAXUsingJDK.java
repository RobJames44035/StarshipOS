/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.javax.xml;

import org.openjdk.jmh.annotations.Benchmark;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;

/**
 * Micro testing SAXParser performance using the JDK classes
 */
public class SAXUsingJDK extends AbstractXMLMicro {

    @Benchmark
    public XMLReader testParse() throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        byte[] bytes = getFileBytesFromResource(doc);
        spf.setValidating(false);
        SAXParser parser = spf.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        InputSource source = new InputSource();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        source.setByteStream(bais);
        reader.parse(source);
        return reader;
    }

}
