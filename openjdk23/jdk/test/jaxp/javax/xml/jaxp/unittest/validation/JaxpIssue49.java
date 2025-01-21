/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import java.io.FileInputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;

/*
 * @test
 * @bug 6684227
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.JaxpIssue49
 * @summary Test property current-element-node works.
 */
public class JaxpIssue49 {

    private Schema schema;
    private Validator validator;

    @Test
    public void testValidatorTest() throws Exception {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            String file = getClass().getResource("types.xsd").getFile();
            Source[] sources = new Source[] { new StreamSource(new FileInputStream(file), file) };
            Schema schema = sf.newSchema(sources);
            validator = schema.newValidator();
            validate();
        } catch (Exception e) {
            Node node = (Node) validator.getProperty("http://apache.org/xml/properties/dom/current-element-node");
            if (node != null) {
                System.out.println("Node: " + node.getLocalName());
            } else
                Assert.fail("No node returned");
        }
    }

    public void validate() throws Exception {
        validator.reset();
        Source source = new StreamSource(getClass().getResourceAsStream("JaxpIssue49.xml"));
        // If you comment the following line, it works
        source = toDOMSource(source);
        validator.validate(source);
    }

    DOMSource toDOMSource(Source source) throws Exception {
        if (source instanceof DOMSource) {
            return (DOMSource) source;
        }
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        DOMResult result = new DOMResult();
        trans.transform(source, result);
        trans.transform(new DOMSource(result.getNode()), new StreamResult(System.out));
        return new DOMSource(result.getNode());
    }

}
