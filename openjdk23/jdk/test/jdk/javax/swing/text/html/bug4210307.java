/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.html.FormView;

/*
 * @test
 * @bug 4210307 4210308
 * @summary Tests that FormView button text is internationalized
 */

public class bug4210307 {
    private static final String RESET_PROPERTY = "TEST RESET";
    private static final String SUBMIT_PROPERTY = "TEST SUBMIT";

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            Object oldReset = UIManager.put("FormView.resetButtonText",
                    RESET_PROPERTY);
            Object oldSubmit = UIManager.put("FormView.submitButtonText",
                    SUBMIT_PROPERTY);

            try {
                JEditorPane ep = new JEditorPane("text/html",
                        "<html><input type=\"submit\"></html>");
                Document doc = ep.getDocument();
                Element elem = findInputElement(doc.getDefaultRootElement());
                TestView view = new TestView(elem);
                view.test(SUBMIT_PROPERTY);

                ep = new JEditorPane("text/html",
                        "<html><input type=\"reset\"></html>");
                doc = ep.getDocument();
                elem = findInputElement(doc.getDefaultRootElement());
                view = new TestView(elem);
                view.test(RESET_PROPERTY);
            } finally {
                UIManager.put("FormView.resetButtonText", oldReset);
                UIManager.put("FormView.submitButtonText", oldSubmit);
            }
        });
    }

    private static Element findInputElement(Element root) {
        for (int i = 0; i < root.getElementCount(); i++) {
            Element elem = root.getElement(i);
            if (elem.getName().equals("input")) {
                return elem;
            } else {
                Element e = findInputElement(elem);
                if (e != null) return e;
            }
        }
        return null;
    }

    static class TestView extends FormView {
        public TestView(Element elem) {
            super(elem);
        }

        public void test(String caption) {
            JButton comp = (JButton) createComponent();
            if (!comp.getText().equals(caption)) {
                throw new RuntimeException("Failed: '" + comp.getText() +
                        "' instead of `" + caption + "'");
            }
        }
    }
}
