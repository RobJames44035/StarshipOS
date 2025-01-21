/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package dom;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

/*
 * @test
 * @bug 6367542
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm dom.Bug6367542
 * @summary Test DOMImplementationRegistry.getDOMImplementation("XML") returns a DOMImplementation instance.
 */
public class Bug6367542 {

    @Test
    public void testDOMImplementationRegistry() {
        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementation domImpl = registry.getDOMImplementation("XML");
            Assert.assertTrue(domImpl != null, "Non null implementation is expected for getDOMImplementation('XML')");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occured: " + e.getMessage());
        }
    }
}
