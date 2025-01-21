/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.File;
import java.io.StringReader;
import java.util.PropertyPermission;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.testng.Assert;
import org.testng.annotations.Test;
import static jaxp.library.JAXPTestUtilities.USER_DIR;

/*
 * @test
 * @bug 6216226
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Bug6216226Test
 * @summary Test StreamResult(File) is closed after transform().
 */
public class Bug6216226Test {

    @Test
    public final void test() {
        try {
            File test = new File(USER_DIR + "bug6216226.txt");
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer xformer = tf.newTransformer();
            StringReader st = new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><doc></doc>");
            StreamSource s = new StreamSource(st);
            StreamResult r = new StreamResult(test);
            xformer.transform(s, r);
            if (!test.delete()) {
                Assert.fail("cannot delete file: " + test.getPath());
            }
        } catch (Exception e) {
            // unexpected failure
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }
}
