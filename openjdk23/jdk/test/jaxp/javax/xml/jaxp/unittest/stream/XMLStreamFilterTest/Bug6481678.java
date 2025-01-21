/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamFilterTest;

import java.io.InputStream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.EventFilter;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6481678
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamFilterTest.Bug6481678
 * @summary Test Filtered XMLStreamReader parses namespace correctly.
 */
public class Bug6481678 {

    String rootElement = "fruits";
    String childElement = "varieties";
    String prefixApple = "a";
    String namespaceURIApple = "apple.fruit";
    String prefixOrange = "o";
    String namespaceURIOrange = "orange.fruit";
    String namespaceURIBanana = "banana.fruit";

    TypeFilter filter;
    XMLInputFactory factory;
    InputStream is;

    /** Creates a new instance of NamespaceTest */
    public Bug6481678(java.lang.String testName) {
        init();
    }

    private void init() {
        factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
        filter = createFilter();
    }

    String getXML() {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sbuffer.append("<" + rootElement + " state=\"WA\"");
        sbuffer.append(" xmlns:" + prefixApple + "=\"" + namespaceURIApple + "\"");
        sbuffer.append(" xmlns:" + prefixOrange + "=\"" + namespaceURIOrange + "\"");
        sbuffer.append(" xmlns=\"" + namespaceURIBanana + "\">");
        sbuffer.append("<" + prefixApple + ":" + childElement + ">");
        sbuffer.append("<" + prefixApple + ":fuji/>");
        sbuffer.append("<" + prefixApple + ":gala/>");
        sbuffer.append("</" + prefixApple + ":" + childElement + ">");
        sbuffer.append("</" + rootElement + ">");
        // System.out.println("XML = " + sbuffer.toString()) ;
        return sbuffer.toString();
    }

    public TypeFilter createFilter() {

        TypeFilter f = new TypeFilter();

        f.addType(XMLEvent.START_ELEMENT);
        f.addType(XMLEvent.END_ELEMENT);
        f.addType(XMLEvent.PROCESSING_INSTRUCTION);
        f.addType(XMLEvent.CHARACTERS);
        f.addType(XMLEvent.COMMENT);
        f.addType(XMLEvent.SPACE);
        f.addType(XMLEvent.START_DOCUMENT);
        f.addType(XMLEvent.END_DOCUMENT);
        return f;
    }

    /*
     * testcase for cr6481678 in our current impl (using cache), the reader
     * would read from cache when getters are called before next() is. refter to
     * testRootElementNamespace.
     */
    @Test
    public void testReadingNamespace() {
        is = new java.io.ByteArrayInputStream(getXML().getBytes());
        try {
            XMLStreamReader sr = factory.createFilteredReader(factory.createXMLStreamReader(is), (StreamFilter) filter);

            while (sr.hasNext()) {
                int eventType = sr.getEventType();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    if (sr.getLocalName().equals(rootElement)) {
                        Assert.assertTrue(sr.getNamespacePrefix(0).equals(prefixApple) && sr.getNamespaceURI(0).equals(namespaceURIApple));
                    }
                }
                eventType = sr.next();
            }
        } catch (Exception ex) {
            Assert.fail("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testRootElementNamespace() {
        is = new java.io.ByteArrayInputStream(getXML().getBytes());
        try {
            XMLStreamReader sr = factory.createFilteredReader(factory.createXMLStreamReader(is), (StreamFilter) filter);

            while (sr.hasNext()) {
                int eventType = sr.next();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    if (sr.getLocalName().equals(rootElement)) {
                        Assert.assertTrue(sr.getNamespacePrefix(0).equals(prefixApple) && sr.getNamespaceURI(0).equals(namespaceURIApple));
                    }
                }
            }
        } catch (Exception ex) {
            Assert.fail("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testChildElementNamespace() {
        is = new java.io.ByteArrayInputStream(getXML().getBytes());
        try {
            XMLStreamReader sr = factory.createFilteredReader(factory.createXMLStreamReader(is), (StreamFilter) filter);
            while (sr.hasNext()) {
                int eventType = sr.next();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    if (sr.getLocalName().equals(childElement)) {
                        QName qname = sr.getName();
                        Assert.assertTrue(qname.getPrefix().equals(prefixApple) && qname.getNamespaceURI().equals(namespaceURIApple)
                                && qname.getLocalPart().equals(childElement));
                    }
                }
            }
        } catch (Exception ex) {
            Assert.fail("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testNamespaceContext() {
        is = new java.io.ByteArrayInputStream(getXML().getBytes());
        try {
            XMLStreamReader sr = factory.createFilteredReader(factory.createXMLStreamReader(is), (StreamFilter) filter);
            while (sr.hasNext()) {
                int eventType = sr.next();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    if (sr.getLocalName().equals(childElement)) {
                        NamespaceContext context = sr.getNamespaceContext();
                        Assert.assertTrue(context.getPrefix(namespaceURIApple).equals(prefixApple));
                    }
                }
            }
        } catch (Exception ex) {
            Assert.fail("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testNamespaceCount() {
        is = new java.io.ByteArrayInputStream(getXML().getBytes());
        try {
            XMLStreamReader sr = factory.createFilteredReader(factory.createXMLStreamReader(is), (StreamFilter) filter);
            while (sr.hasNext()) {
                int eventType = sr.next();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    if (sr.getLocalName().equals(rootElement)) {
                        int count = sr.getNamespaceCount();
                        Assert.assertTrue(count == 3);
                    }
                }
            }
        } catch (Exception ex) {
            Assert.fail("Exception: " + ex.getMessage());
        }
    }

    class TypeFilter implements EventFilter, StreamFilter {

        protected boolean[] types = new boolean[20];

        public TypeFilter() {
        }

        public void addType(int type) {
            types[type] = true;
        }

        public boolean accept(XMLEvent e) {
            return types[e.getEventType()];
        }

        public boolean accept(XMLStreamReader r) {
            return types[r.getEventType()];
        }
    }
}
