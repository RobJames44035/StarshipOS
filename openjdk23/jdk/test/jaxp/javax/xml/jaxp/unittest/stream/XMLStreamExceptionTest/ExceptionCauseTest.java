/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package stream.XMLStreamExceptionTest;

import java.io.IOException;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm stream.XMLStreamExceptionTest.ExceptionCauseTest
 * @summary Test XMLStreamException constructor initializes chained exception
 */
public class ExceptionCauseTest {

    @Test
    public void testExceptionCause() {

        // Create exception with cause
        Throwable cause = new Throwable("cause");
        Location location = new Location() {
            public int getLineNumber() { return 0; }
            public int getColumnNumber() { return 0; }
            public int getCharacterOffset() { return 0; }
            public String getPublicId() { return null; }
            public String getSystemId() { return null; }
        };
        XMLStreamException e = new XMLStreamException("message", location, cause);

        // Verify cause
        Assert.assertSame(e.getCause(), cause, "XMLStreamException has the wrong cause");
    }
}
