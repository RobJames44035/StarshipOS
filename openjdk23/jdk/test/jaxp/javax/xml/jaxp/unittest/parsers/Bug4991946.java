/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package parsers;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.testng.annotations.Test;

/*
 * @test
 * @bug 4991946
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm parsers.Bug4991946
 * @summary Can parse the element type is anyType in the schema and is substituted by the simple type via the 'xsi:type' attribute in xml document.
 */
public class Bug4991946 {

    protected static SAXParser createParser() throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setValidating(true);
        SAXParser parser = spf.newSAXParser();
        parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

        return parser;
    }

    @Test
    public void test1() throws Exception {
        SAXParser parser = createParser();
        parser.parse(Bug4991946.class.getResource("Bug4991946.xml").toExternalForm(), new util.DraconianErrorHandler());
    }
}
