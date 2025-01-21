/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamReaderTest;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamReaderTest.IssueTracker35
 * @summary Test StAX parse xsd document including external DTD.
 */
public class IssueTracker35 {

    @Test
    public void testSkippingExternalDTD() throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        try(
                InputStream is= getClass().getResourceAsStream("XMLSchema.xsd");
        ) {
                XMLStreamReader reader = xif.createXMLStreamReader(getClass().getResource("XMLSchema.xsd").getFile(), is);
                int e;
                while ((e = reader.next()) == XMLStreamConstants.COMMENT);

                Assert.assertEquals(e, XMLStreamConstants.DTD, "should be DTD");
                reader.nextTag();
                Assert.assertEquals(reader.getLocalName(), "schema", "next tag should be schema");
        }
    }
}
