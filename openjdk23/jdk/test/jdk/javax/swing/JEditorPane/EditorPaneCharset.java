/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/*
 * @test
 * @bug 8328953
 * @summary Verifies JEditorPane.read doesn't throw ChangedCharSetException
            but handles it and reads HTML in the specified encoding
 * @run main EditorPaneCharset
 */

public final class EditorPaneCharset {
    private static final String CYRILLIC_TEXT =
            "\u041F\u0440\u0438\u0432\u0435\u0442, \u043C\u0438\u0440!";
    private static final String HTML_CYRILLIC =
            "<html lang=\"ru\">\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" " +
            "          content=\"text/html; charset=windows-1251\">\n" +
            "</head><body>\n" +
            "<p>" + CYRILLIC_TEXT + "</p>\n" +
            "</body></html>\n";

    public static void main(String[] args) throws IOException, BadLocationException {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        Document document = editorPane.getDocument();

        // Shouldn't throw ChangedCharSetException
        editorPane.read(
                new ByteArrayInputStream(
                        HTML_CYRILLIC.getBytes(
                                Charset.forName("windows-1251"))),
                document);

        Element root = document.getDefaultRootElement();
        Element body = root.getElement(1);
        Element p = body.getElement(0);
        String pText = document.getText(p.getStartOffset(),
                                        p.getEndOffset() - p.getStartOffset() - 1);
        if (!CYRILLIC_TEXT.equals(pText)) {
            throw new RuntimeException("Text doesn't match");
        }
    }
}
