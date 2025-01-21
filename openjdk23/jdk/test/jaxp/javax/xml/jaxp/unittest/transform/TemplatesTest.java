/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package transform;

import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.TemplatesTest
 * @summary This class contains tests for Templates.
 */
public class TemplatesTest {

    /**
     * @bug 8079323 Test Templates serialization
     * <p>
     * Serialization compatibility test: verify that serializing the Templates
     * that contain auxiliary classes will result in a NotSerializableException
     * due to the use of Xalan's non-serializable Hashtable.
     *
     * @param templates an instance of Templates
     * @throws Exception as expected.
     */
    @Test(dataProvider = "templates", expectedExceptions = NotSerializableException.class)
    public void testSerialization(Templates templates) throws Exception {
        Transformer xformer = templates.newTransformer();
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);) {
            out.writeObject(templates);
            out.flush();
        }
    }

    /*
     * DataProvider: Templates
     */
    @DataProvider(name = "templates")
    public Object[][] getTemplates() throws Exception {
        return new Object[][]{{TransformerFactory.newInstance().
                newTemplates(new StreamSource(new StringReader(XSL)))}};
    }

    static final String XSL = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"
            + "<xsl:stylesheet version=\"1.0\""
            + "      xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">"
            + "<xsl:variable name=\"validAffectsRelClasses\">"
            + "</xsl:variable>"
            + "<xsl:key name=\"UniqueAffectsRelObjects\""
            + "      match=\"/ObjectSetRoot/Object["
            + "      contains($validAffectsRelClasses, @Class)]\""
            + "      use=\"not(@OBID=preceding-sibling::Object["
            + "      contains($validAffectsRelClasses, @Class)]/@OBID)\"/>"
            + "</xsl:stylesheet>";
}
