/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sax;

import static jaxp.library.JAXPTestUtilities.getSystemProperty;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.testng.annotations.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @test
 * @bug 8213734
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng sax.SAXParserTest
 * @summary Tests functionalities for SAXParser.
 */
public class SAXParserTest {

    /*
     * @bug 8213734
     * Verifies that files opened by the SAXParser is closed when Exception
     * occurs.
     */
    @Test
    public void testCloseReaders() throws Exception {
        if (!getSystemProperty("os.name").contains("Windows")) {
            System.out.println("This test only needs to be run on Windows.");
            return;
        }
        Path testFile = createTestFile(null, "Test");
        System.out.println("Test file: " + testFile.toString());
        SAXParserFactory factory = SAXParserFactory.newDefaultInstance();
        SAXParser parser = factory.newSAXParser();
        try {
            parser.parse(testFile.toFile(), new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    throw new SAXException("Stop the parser.");
                }
            });
        } catch (SAXException e) {
            // Do nothing
        }

        // deletion failes on Windows when the file is not closed
        Files.deleteIfExists(testFile);
    }

    private static Path createTestFile(Path dir, String name) throws IOException {
        Path path = Files.createTempFile(name, ".xml");
            byte[] bytes = "<?xml version=\"1.0\"?><test a1=\"x\" a2=\"y\"/>"
        .getBytes(StandardCharsets.UTF_8);

        Files.write(path, bytes);
        return path;
    }
}
