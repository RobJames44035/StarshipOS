/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package org.xml.sax.ptests;

import static jaxp.library.JAXPTestUtilities.USER_DIR;
import static jaxp.library.JAXPTestUtilities.compareWithGold;
import static org.testng.Assert.assertTrue;
import static org.xml.sax.ptests.SAXTestConst.GOLDEN_DIR;
import static org.xml.sax.ptests.SAXTestConst.XML_DIR;

import java.io.File;

import javax.xml.parsers.SAXParserFactory;

import org.testng.annotations.Test;

/**
 * This class contains the testcases to test SAXParser with regard to
 * Namespace Table defined at http://www.megginson.com/SAX/Java/namespaces.html
 */
/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm org.xml.sax.ptests.SAXParserNSTableTest
 */
public class SAXParserNSTableTest {
    /**
     * namespace processing is enabled. namespace-prefix is also is enabled.
     * So it is a True-True combination.
     * The test is to test SAXParser with these conditions.
     *
     * @throws Exception If any errors occur.
     */
    @Test
    public void testWithTrueTrue() throws Exception {
        String outputFile = USER_DIR + "SPNSTableTT.out";
        String goldFile = GOLDEN_DIR + "NSTableTTGF.out";
        String xmlFile = XML_DIR + "namespace1.xml";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        spf.setFeature("http://xml.org/sax/features/namespace-prefixes",
                                    true);
        try (MyNSContentHandler handler = new MyNSContentHandler(outputFile)) {
            spf.newSAXParser().parse(new File(xmlFile), handler);
        }
        assertTrue(compareWithGold(goldFile, outputFile));

    }
    /**
     * namespace processing is enabled. Hence namespace-prefix is
     * expected to be automatically off. So it is a True-False combination.
     * The test is to test SAXParser with these conditions.
     *
     * @throws Exception If any errors occur.
     */
    public void testWithTrueFalse() throws Exception {
        String outputFile = USER_DIR + "SPNSTableTF.out";
        String goldFile = GOLDEN_DIR + "NSTableTFGF.out";
        String xmlFile = XML_DIR + "namespace1.xml";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        try (MyNSContentHandler handler = new MyNSContentHandler(outputFile)) {
            spf.newSAXParser().parse(new File(xmlFile), handler);
        }
        assertTrue(compareWithGold(goldFile, outputFile));
    }

    /**
     * namespace processing is not enabled. Hence namespace-prefix is
     * expected to be automatically on. So it is a False-True combination.
     * The test is to test SAXParser with these conditions.
     *
     * @throws Exception If any errors occur.
     */
    public void testWithFalseTrue() throws Exception {
        String outputFile = USER_DIR + "SPNSTableFT.out";
        String goldFile = GOLDEN_DIR + "NSTableFTGF.out";
        String xmlFile = XML_DIR + "namespace1.xml";
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        try (MyNSContentHandler handler = new MyNSContentHandler(outputFile)) {
            spf.newSAXParser().parse(new File(xmlFile), handler);
        }
        assertTrue(compareWithGold(goldFile, outputFile));
    }
}
