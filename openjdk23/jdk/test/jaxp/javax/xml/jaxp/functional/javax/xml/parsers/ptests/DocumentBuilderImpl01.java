/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package javax.xml.parsers.ptests;

import static javax.xml.parsers.ptests.ParserTestConst.XML_DIR;
import static jaxp.library.JAXPTestUtilities.FILE_SEP;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilePermission;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * This checks for the methods of DocumentBuilder
 */
/*
 * @test
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm javax.xml.parsers.ptests.DocumentBuilderImpl01
 */
public class DocumentBuilderImpl01 implements EntityResolver {
    /**
     * Provide DocumentBuilder.
     *
     * @return data provider has single DocumentBuilder.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be
     *         created which satisfies the configuration requested.
     */
    @DataProvider(name = "builder-provider")
    public Object[][] getBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        return new Object[][] { { docBuilder } };
    }

    /**
     * Test the default functionality of isValidation method. Expect
     * to return false because not setting the validation.
     * @param docBuilder document builder instance.
     */
    @Test(dataProvider = "builder-provider")
    public void testCheckDocumentBuilderImpl01(DocumentBuilder docBuilder) {
        assertFalse(docBuilder.isValidating());
    }

    /**
     * Test the default functionality of isNamespaceAware method.
     * @param docBuilder document builder instance.
     */
    @Test(dataProvider = "builder-provider")
    public void testCheckDocumentBuilderImpl02(DocumentBuilder docBuilder) {
        assertFalse(docBuilder.isNamespaceAware());
    }

    /**
     * Test the parse(InputStream).
     * @param docBuilder document builder instance.
     * @throws Exception If any errors occur.
     */
    @Test(dataProvider = "builder-provider")
    public void testCheckDocumentBuilderImpl04(DocumentBuilder docBuilder)
            throws Exception {
        try (FileInputStream fis = new FileInputStream(new File(XML_DIR,
                "DocumentBuilderImpl01.xml"))) {
            assertNotNull(docBuilder.parse(fis));
        }
    }

    /**
     * Test the parse(File).
     *
     * @param docBuilder document builder instance.
     * @throws Exception If any errors occur.
     */
    @Test(dataProvider = "builder-provider")
    public void testCheckDocumentBuilderImpl05(DocumentBuilder docBuilder)
            throws Exception {
        assertNotNull(docBuilder.parse(new File(XML_DIR,
                "DocumentBuilderImpl01.xml")));
    }

    /**
     * Test the parse(InputStream,systemId).
     * @param docBuilder document builder instance.
     * @throws Exception If any errors occur.
     */
    @Test(dataProvider = "builder-provider")
    public void testCheckDocumentBuilderImpl06(DocumentBuilder docBuilder)
            throws Exception {
        try (FileInputStream fis = new FileInputStream(new File(XML_DIR,
                "DocumentBuilderImpl02.xml"))) {
            assertNotNull(docBuilder.parse(fis, new File(XML_DIR).toURI()
                    .toASCIIString() + FILE_SEP));
        }
    }

    /**
     * Test the setEntityResolver.
     * @param docBuilder document builder instance.
     */
    @Test(dataProvider = "builder-provider")
    public void testCheckDocumentBuilderImpl07(DocumentBuilder docBuilder) {
        docBuilder.setEntityResolver(this);
        assertNotNull(resolveEntity("publicId", "http://www.myhost.com/today"));
    }

    /**
     * Allow the application to resolve external entities.
     *
     * @param publicId The public identifier of the external entity
     *        being referenced, or null if none was supplied.
     * @param systemId The system identifier of the external entity
     *        being referenced.
     * @return An InputSource object describing the new input source,
     *         or null to request that the parser open a regular
     *         URI connection to the system identifier.
     */
    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        if (systemId.equals("http://www.myhost.com/today"))
            return new InputSource(systemId);
        else
            return null;
    }
}
