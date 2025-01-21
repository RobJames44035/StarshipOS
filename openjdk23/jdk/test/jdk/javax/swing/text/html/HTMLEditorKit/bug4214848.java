/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.io.StringReader;
import java.io.StringWriter;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

/*
 * @test
 * @bug 4214848
 * @summary Tests whether  HTMLEditorKit.read(...)
 *          creates Document for html with empty BODY
 */

public class bug4214848 {
    public static void main (String[] args) throws Exception {
        StringWriter sw = new StringWriter();
        String test = "<HTML><BODY></BODY></HTML>";
        HTMLEditorKit kit = new HTMLEditorKit();
        Document doc = kit.createDefaultDocument();
        kit.read(new StringReader(test), doc, 0); // prepare test document
        kit.write(sw, doc, 0, 10);
        String out = sw.toString().toLowerCase();
        if (out.indexOf("<body>") != out.lastIndexOf("<body>")) {
            throw new RuntimeException("Test failed: extra <body> section generated");
        }
    }
}
