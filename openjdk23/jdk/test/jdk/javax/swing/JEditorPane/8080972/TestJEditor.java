/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.ViewFactory;

/*
 * @test
 * @bug 8080972 8169887
 * @summary Audit Core Reflection in module java.desktop for places that will
 *          require changes to work with modules
 * @run main TestJEditor
 */
public class TestJEditor {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(TestJEditor::testJEditorPane);
    }

    private static void testJEditorPane() {
        JEditorPane.registerEditorKitForContentType("text/html", UserEditorKit.class.getName());
        EditorKit editorKit = JEditorPane.createEditorKitForContentType("text/html");

        if (!(editorKit instanceof UserEditorKit)) {
            throw new RuntimeException("Editor kit is not UserEditorKit!");
        }
    }

    public static class UserEditorKit extends EditorKit {

        @Override
        public String getContentType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ViewFactory getViewFactory() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Action[] getActions() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Caret createCaret() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Document createDefaultDocument() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void read(InputStream in, Document doc, int pos) throws IOException, BadLocationException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void write(OutputStream out, Document doc, int pos, int len) throws IOException, BadLocationException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void read(Reader in, Document doc, int pos) throws IOException, BadLocationException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void write(Writer out, Document doc, int pos, int len) throws IOException, BadLocationException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
