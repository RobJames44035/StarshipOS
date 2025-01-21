/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamWriterTest;

import java.io.ByteArrayOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6600882
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamWriterTest.Bug6600882Test
 * @summary Test toString(), hashCode() of XMLStreamWriter .
 */
public class Bug6600882Test {


    @Test
    public void test() {
        try {
            XMLOutputFactory of = XMLOutputFactory.newInstance();
            XMLStreamWriter w = of.createXMLStreamWriter(new ByteArrayOutputStream());
            XMLStreamWriter w1 = of.createXMLStreamWriter(new ByteArrayOutputStream());
            System.out.println(w);
            Assert.assertTrue(w.equals(w) && w.hashCode() == w.hashCode());
            Assert.assertFalse(w1.equals(w));
        } catch (Throwable ex) {
            Assert.fail(ex.toString());
        }
    }

}
