/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package transform;

import java.util.Properties;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.XMLFilter;

/*
 * @test
 * @bug 8206164
 * @modules java.xml
 * @run testng transform.SAXTFactoryTest
 * @summary Tests SAXTransformerFactory.
 */
public class SAXTFactoryTest {

    /*
     * Verifies that the default ErrorListener throws a TransformerException
     * when a fatal error is encountered. It is then wrapped and thrown again in
     * a TransformerConfigurationException.
    */
    @Test
    public void testErrorListener() throws Exception {
        try {
            SAXTransformerFactory saxTFactory =
                    (SAXTransformerFactory)TransformerFactory.newInstance();
            XMLFilter filter = saxTFactory.newXMLFilter(new ATemplatesImpl());
        } catch (TransformerConfigurationException tce) {
            Throwable cause = tce.getCause();
            Assert.assertTrue((cause != null && cause instanceof TransformerException),
                    "The TransformerFactoryImpl terminates upon a fatal error "
                            + "by throwing a TransformerException.");
        }

    }

    class ATemplatesImpl implements Templates {

        @Override
        public Transformer newTransformer() throws TransformerConfigurationException {
            throw new TransformerConfigurationException("TCE from ATemplatesImpl");
        }

        @Override
        public Properties getOutputProperties() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
