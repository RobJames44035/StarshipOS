/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamReaderTest;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamReaderTest.IssueTracker24
 * @summary Test no prefix is represented by "", not null.
 */
public class IssueTracker24 {

    @Test
    public void testInconsistentGetPrefixBehaviorWhenNoPrefix() throws Exception {
        String xml = "<root><child xmlns='foo'/><anotherchild/></root>";

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader r = factory.createXMLStreamReader(new StringReader(xml));
        r.require(XMLStreamReader.START_DOCUMENT, null, null);
        r.next();
        r.require(XMLStreamReader.START_ELEMENT, null, "root");
        Assert.assertEquals(r.getPrefix(), "", "prefix should be empty string");
        r.next();
        r.require(XMLStreamReader.START_ELEMENT, null, "child");
        r.next();
        r.next();
        r.require(XMLStreamReader.START_ELEMENT, null, "anotherchild");
        Assert.assertEquals(r.getPrefix(), "", "prefix should be empty string");
    }

}
