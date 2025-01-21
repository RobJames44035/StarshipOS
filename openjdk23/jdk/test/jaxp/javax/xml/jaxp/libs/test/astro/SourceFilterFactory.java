/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package test.astro;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;

public abstract class SourceFilterFactory extends AbstractFilterFactory {
    @Override
    protected TransformerHandler getTransformerHandler(String xslFileName) throws SAXException, ParserConfigurationException,
            TransformerConfigurationException, IOException {
        return getFactory().newTransformerHandler(getSource(xslFileName));
    }

    abstract protected Source getSource(String xslFileName) throws SAXException, ParserConfigurationException, IOException;

    private SAXTransformerFactory getFactory() {
        return (SAXTransformerFactory) TransformerFactory.newInstance();
    }
}
