/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

/*
 * @test
 * @bug 4251593
 * @summary Tests that hyperlinks can be inserted into JTextPane
 *          via InsertHTMLTextAction.
 */

public class bug4251593 {
    private static JTextPane editor;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            editor = new JTextPane();
            editor.setContentType("text/html");
            editor.setEditable(true);

            int beforeLen = editor.getDocument().getLength();

            String href = "<a HREF=\"https://java.sun.com\">javasoft </a>";
            Action a = new HTMLEditorKit.InsertHTMLTextAction("Tester", href, HTML.Tag.BODY, HTML.Tag.A);
            a.actionPerformed(new ActionEvent(editor, 0, null));

            int afterLen = editor.getDocument().getLength();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if ((afterLen - beforeLen) < 8) {
                throw new RuntimeException("Test Failed: link not inserted!!");
            }
        });
    }
}
