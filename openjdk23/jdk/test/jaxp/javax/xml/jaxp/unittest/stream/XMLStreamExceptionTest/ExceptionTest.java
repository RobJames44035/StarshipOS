/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLStreamExceptionTest;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamExceptionTest.ExceptionTest
 * @summary Test XMLStreamException contains the message of the wrapped exception.
 */
public class ExceptionTest {

    @Test
    public void testException() {

        final String EXPECTED_OUTPUT = "Test XMLStreamException";
        try {
            Exception ex = new IOException("Test XMLStreamException");
            throw new XMLStreamException(ex);
        } catch (XMLStreamException e) {
            Assert.assertTrue(e.getMessage().contains(EXPECTED_OUTPUT), "XMLStreamException does not contain the message " + "of the wrapped exception");
        }
    }
}
