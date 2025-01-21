/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4267840
   @summary Tests how HTMLEditorKit.write() works on small documents
   @run main bug4267840
*/

import javax.swing.JTextPane;
import javax.swing.text.EditorKit;
import javax.swing.SwingUtilities;
import java.io.File;
import java.io.FileOutputStream;

public class bug4267840 {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            final JTextPane textpane = new JTextPane();
            textpane.setContentType("text/html");
            final EditorKit kit = textpane.getEditorKit();

            textpane.setText("A word");
            File file = new File("bug4267840.out");
            try {
                FileOutputStream out = new FileOutputStream(file);
                kit.write(out, textpane.getDocument(), 0,
                          textpane.getDocument().getLength());
                out.close();
            } catch (Exception e) {}
            try {
                if (file.length() < 6) {  // simply can't be
                    throw new RuntimeException("Failed: " +
                                          " HTMLEditorKit.write() is broken");
                }
            } finally {
                file.delete();
            }
        });
    }
}
