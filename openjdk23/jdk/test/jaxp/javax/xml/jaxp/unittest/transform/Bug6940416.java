/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6940416
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.Bug6940416
 * @summary Test transforming correctly.
 */
public class Bug6940416 {

    @Test
    public void test() {
        String xslFilename = getClass().getResource("ViewEditor1.xsl").getFile();
        String inFilename = getClass().getResource("in.xml").getFile();
        // String outFilename =
        // getClass().getResource("out-6u17.xml").getFile();
        // the xml result
        StringWriter xmlResultString = new StringWriter();
        try {
            // Create transformer factory
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setAttribute("debug", true);
            // Use the factory to create a template containing the xsl file
            Templates template = factory.newTemplates(new StreamSource(new FileInputStream(xslFilename)));
            // Use the template to create a transformer
            Transformer xformer = template.newTransformer();
            // Prepare the input and output files
            Source source = new StreamSource(new FileInputStream(inFilename));
            // Result result = new StreamResult(new
            // FileOutputStream(outFilename));
            Result result = new StreamResult(xmlResultString);
            // Apply the xsl file to the source file and write the result to the
            // output file
            xformer.transform(source, result);

            // 6u17 results contain the following:
            /**
             * var g_strInitialTabID = "VIEWEDITOR_TAB_FIELDS";
             *
             * var g_strCurrentDataEditorTabID = "DATA_OBJECTS"; var
             * g_strCurrentPropertyEditorTabID = "VIEWEDITOR_TAB_GENERAL";
             *
             * while 6u18: var g_strInitialTabID = "";
             *
             * var g_strCurrentDataEditorTabID = ""; var
             * g_strCurrentPropertyEditorTabID = "VIEWEDITOR_TAB_GENERAL";
             */
            System.out.println(xmlResultString.toString());
            if (xmlResultString.toString().indexOf("VIEWEDITOR_TAB_FIELDS") == -1) {
                Assert.fail("regression from 6u17");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        } catch (TransformerConfigurationException e) {
            // An error occurred in the XSL file
            e.printStackTrace();
            Assert.fail(e.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
            // An error occurred while applying the XSL file
            // Get location of error in input file
            SourceLocator locator = e.getLocator();
            int col = locator.getColumnNumber();
            int line = locator.getLineNumber();
            String publicId = locator.getPublicId();
            String systemId = locator.getSystemId();
            Assert.fail("error while applying the XSL file." + "systemId : " + systemId + ". publicId : " + publicId + ". col : " + col + ". line : " + line);
        }
    }

}
