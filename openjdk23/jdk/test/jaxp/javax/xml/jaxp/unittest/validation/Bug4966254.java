/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import util.DraconianErrorHandler;

/*
 * @test
 * @bug 4966254
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug4966254
 * @summary Test validate(StreamSource) & validate(StreamSource,null) works instead of throws IOException.
 */
public class Bug4966254 {

    static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    @Test
    public void testValidator01() throws Exception {
        getValidator().validate(getInstance());
    }

    @Test
    public void testValidator02() throws Exception {
        getValidator().validate(getInstance(), null);
    }

    private StreamSource getInstance() {
        return new StreamSource(Bug4966254.class.getResource(("Bug4966254.xml")).toExternalForm());
    }

    private Validator getValidator() throws SAXException {
        Schema s = getSchema();
        Validator v = s.newValidator();
        Assert.assertNotNull(v);
        v.setErrorHandler(new DraconianErrorHandler());
        return v;
    }

    private Schema getSchema() throws SAXException {
        SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema s = sf.newSchema(Bug4966254.class.getResource("Bug4966254.xsd"));
        Assert.assertNotNull(s);
        return s;
    }
}
