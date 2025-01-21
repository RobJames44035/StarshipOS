/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package common;

import com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl;
import javax.xml.XMLConstants;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/*
 * Test implementation of SAXParser. It is extended from JDK parser and two methods
 * are overriden to disable support of specific features and properties.
 * This class is used in ValidationWarningsTest and TransformationWarningsTest
 * to generate multiple warnings during xml validation and transformation processes.
*/
public class TestSAXDriver extends SAXParserImpl.JAXPSAXParser {

    @Override
    public synchronized void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (XMLConstants.FEATURE_SECURE_PROCESSING.equals(name)) {
            throw new SAXNotRecognizedException(name+" feature is not recognised by test SAX parser intentionally.");
        } else {
            super.setFeature(name, value);
        }
    }

    @Override
    public synchronized void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (XMLConstants.ACCESS_EXTERNAL_DTD.equals(name) || ENT_EXP_LIMIT_PROP.equals(name)) {
            throw new SAXNotRecognizedException(name+" property is not recognised by test SAX parser intentionally.");
        } else {
            super.setProperty(name, value);
        }
    }

    private static final String ENT_EXP_LIMIT_PROP = "http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit";
}
