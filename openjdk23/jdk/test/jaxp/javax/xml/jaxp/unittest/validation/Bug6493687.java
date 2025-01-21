/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package validation;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

/*
 * @test
 * @bug 6493687
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm validation.Bug6493687
 * @summary Test validator.validate(new DOMSource(node)) without any exception.
 */
public class Bug6493687 {

    @Test
    public void test() throws Exception {
        System.out.println("Got here");
        Document doc = new XMLDocBuilder("Bug6493687.xml", "UTF-8", "Bug6493687.xsd").getDocument();
        System.out.println("Got here2");
        System.out.println(doc);
        System.out.println(doc.getDocumentElement().getNodeName());
        System.out.println("Got here3");
    }
}
