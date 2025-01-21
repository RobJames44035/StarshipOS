/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * @test
 * @bug 6551600
 * @requires os.family == "windows"
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.CR6551600Test
 * @summary Test using UNC path as StreamResult.
 */
public class CR6551600Test {

    @Test
    public final void testUNCPath() {
        var hostName = "";
        try {
            hostName = java.net.InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException e) {
            // falls through
        }

        var uncPath = "\\\\" + hostName + "\\C$\\temp\\";

        if (!checkAccess(uncPath)) {
            System.out.println("Cannot access UNC path. Test exits.");
            return;
        }

        var uncFilePath = uncPath + "xslt_unc_test.xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("test");
            doc.appendChild(root);
            // create an identity transform
            Transformer t = TransformerFactory.newInstance().newTransformer();
            File f = new File(uncFilePath);
            StreamResult result = new StreamResult(f);
            DOMSource source = new DOMSource(doc);
            System.out.println("Writing to " + f);
            t.transform(source, result);
        } catch (Exception e) {
            // unexpected failure
            e.printStackTrace();
            Assert.fail(e.toString());
        }

        File file = new File(uncFilePath);
        if (file.exists()) {
            file.deleteOnExit();
        }
    }

    private boolean checkAccess(String path) {
        try {
            Path tmepFile = Files.createTempFile(Paths.get(path), "test", "6551600");
            Files.deleteIfExists(tmepFile);
            return true;
        } catch (Exception e) {
            System.out.println("Access check failed.");
            e.printStackTrace();
            return false;
        }
    }
}
