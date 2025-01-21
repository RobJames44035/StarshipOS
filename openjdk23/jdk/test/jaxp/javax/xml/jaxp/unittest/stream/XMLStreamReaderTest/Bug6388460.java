/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamReaderTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;

/*
 * @test
 * @bug 6388460
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamReaderTest.Bug6388460
 * @summary Test StAX parser can parse UTF-16 wsdl.
 */
public class Bug6388460 {

    @Test
    public void test() {
        try {

            Source source = new StreamSource(util.BOMInputStream.createStream("UTF-16BE", this.getClass().getResourceAsStream("Hello.wsdl.data")),
                        this.getClass().getResource("Hello.wsdl.data").toExternalForm());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, new StreamResult(baos));
            System.out.println(new String(baos.toByteArray()));
            ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
            InputSource inSource = new InputSource(bis);

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
            XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(inSource.getSystemId(), inSource.getByteStream());
            while (reader.hasNext()) {
                reader.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            Assert.fail("Exception occured: " + ex.getMessage());
        }
    }
}
