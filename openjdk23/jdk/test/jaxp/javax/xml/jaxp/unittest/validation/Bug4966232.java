/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/*
 * @test
 * @bug 4966232
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4966232
 * @summary Test SchemaFactory.newSchema(Source) returns a Schema instance for DOMSource & SAXSource.
 */
public class Bug4966232 {

    // test for W3C XML Schema 1.0 - newSchema(Source schema)
    // supports and return a valid Schema instance
    // SAXSource - valid schema

    @Test
    public void testSchemaFactory01() throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        InputSource is = new InputSource(Bug4966232.class.getResourceAsStream("test.xsd"));
        SAXSource ss = new SAXSource(is);
        Schema s = sf.newSchema(ss);
        Assert.assertNotNull(s);
    }

    // test for W3C XML Schema 1.0 - newSchema(Source schema)
    // supports and return a valid Schema instance
    // DOMSource - valid schema

    @Test
    public void testSchemaFactory02() throws Exception {
        Document doc = null;
        SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        doc = dbf.newDocumentBuilder().parse(Bug4966232.class.getResource("test.xsd").toExternalForm());
        DOMSource ds = new DOMSource(doc);
        Schema s = sf.newSchema(ds);
        Assert.assertNotNull(s);
    }
}
