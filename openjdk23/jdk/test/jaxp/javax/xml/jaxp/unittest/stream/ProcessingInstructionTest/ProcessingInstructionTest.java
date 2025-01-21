/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.ProcessingInstructionTest;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.ProcessingInstructionTest.ProcessingInstructionTest
 * @summary Test XMLStreamReader parses Processing Instruction.
 */
public class ProcessingInstructionTest {

    @Test
    public void testPITargetAndData() {
        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            String PITarget = "soffice";
            String PIData = "WebservicesArchitecture";
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<?" + PITarget + " " + PIData + "?>" + "<foo></foo>";
            // System.out.println("XML = " + xml) ;
            InputStream is = new java.io.ByteArrayInputStream(xml.getBytes());
            XMLStreamReader sr = xif.createXMLStreamReader(is);
            while (sr.hasNext()) {
                int eventType = sr.next();
                if (eventType == XMLStreamConstants.PROCESSING_INSTRUCTION) {
                    String target = sr.getPITarget();
                    String data = sr.getPIData();
                    Assert.assertTrue(target.equals(PITarget) && data.equals(PIData));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
