/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @test
 * @bug 8005391
 * @author Alexander Shusherov
 * @summary Floating behavior of HTMLEditorKit parser
 * @run main bug8005391
 */
public class bug8005391 {

    private static final String htmlDoc = "<html><body><tt><a href='one'>1</a>2</tt></body></html>";

    public static void main(String[] args) throws Exception {
        int N = 10;

        for (int i = 0; i < N; i++) {
            HTMLEditorKit kit = new HTMLEditorKit();
            Class c = Class.forName("javax.swing.text.html.parser.ParserDelegator");
            HTMLEditorKit.Parser parser = (HTMLEditorKit.Parser) c.newInstance();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            HTMLEditorKit.ParserCallback htmlReader = doc.getReader(0);
            parser.parse(new CharArrayReader(htmlDoc.toCharArray()), htmlReader, true);
            htmlReader.flush();
            CharArrayWriter writer = new CharArrayWriter(1000);
            kit.write(writer, doc, 0, doc.getLength());
            writer.flush();

            String result = writer.toString();
            if (!result.contains("<tt><a")) {
                throw new RuntimeException("The <a> and <tt> tags are swapped");
            }
        }
    }
}
