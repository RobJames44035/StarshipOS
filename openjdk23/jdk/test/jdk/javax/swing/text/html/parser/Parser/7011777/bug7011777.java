/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7011777
 * @summary Tests correct parsing of a HTML comment inside 'script' tags
 * @author Dmitry Markov
 */

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.StringReader;

public class bug7011777 {
    static String comment = "<!--\n" +
            "function foo() {\n" +
            "  var tag1 = \"</script>\";\n" +
            "  var tag2 = \"<div>\";\n" +
            "  var tag3 = \"</div>\";\n" +
            "  var tag4 = \"<script>\";\n" +
            "}\n" +
            "// -->";
    static String html = "<script>" + comment + "</script>";
    public static void main(String[] args) throws Exception {
            new ParserDelegator().parse(new StringReader(html), new MyParserCallback(), true);
    }

    static class MyParserCallback extends HTMLEditorKit.ParserCallback {

        @Override
        public void handleComment(char[] data, int pos) {
            String commentWithoutTags = comment.substring("<!--".length(), comment.length() - "-->".length());
            String str = new String(data);
            if (!commentWithoutTags.equals(str)) {
                System.out.println("Sample string:\n" + commentWithoutTags);
                System.out.println("Returned string:\n" + str);
                throw new RuntimeException("Test Failed, sample and returned strings are mismatched!");
            }
        }
    }

}
