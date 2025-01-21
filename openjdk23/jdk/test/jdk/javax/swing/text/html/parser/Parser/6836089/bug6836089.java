/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6836089
 * @summary Tests correct parsing of characters outside Base Multilingual Plane
 * @author Vladislav Karnaukhov
 */

import javax.swing.*;
import javax.swing.text.html.*;

public class bug6836089 {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JTextPane htmlPane = new JTextPane();
                htmlPane.setEditorKit(new HTMLEditorKit());

                htmlPane.setText("<html><head></head><body>&#131072;</body></html>");
                String str = htmlPane.getText();
                if (str.contains("&#0;")) {
                    throw new RuntimeException("Test failed");
                }
            }
        });
    }
}
