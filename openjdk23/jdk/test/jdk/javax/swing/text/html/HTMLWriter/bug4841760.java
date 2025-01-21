/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4841760
 * @summary  Tests if HTML tags are correctly shown for
             StyleEditorKit.ForegroundAction() in JTextPane output.
 * @run main bug4841760
 */

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLEditorKit;

public class bug4841760 {

    public static void main(String[] args) throws Exception {
        JTextPane jep = new JTextPane();
        jep.setEditorKit(new HTMLEditorKit());
        jep.setText("<html><head></head><body><font size=3>hellojavaworld</font></body></html>");

        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, java.awt.Color.BLUE);
        jep.getStyledDocument().setCharacterAttributes(3, 5, set, false);

        String gotText = jep.getText();
        System.out.println("gotText: " + gotText);
        // there should be color attribute set
        // and 3 font tags
        int i = gotText.indexOf("color");
        if (i > 0) {
            i = gotText.indexOf("<font");
            if (i > 0) {
                i = gotText.indexOf("<font", i + 1);
                if (i > 0) {
                    i = gotText.indexOf("<font", i + 1);
                    if (i <= 0) {
                        throw new RuntimeException("Test failed.");
                    }
                }
            }
        }

    }
}
