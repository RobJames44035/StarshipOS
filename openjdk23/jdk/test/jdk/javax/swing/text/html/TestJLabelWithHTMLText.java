/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8230235 8235744
 * @summary Tests if JLabel with HTML text having empty img tag and
 *      documentBaseKey set renders properly without NPE
 * @run main TestJLabelWithHTMLText
 */
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicHTML;

public class TestJLabelWithHTMLText {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JLabel label = new JLabel();
            try {
                label.putClientProperty(BasicHTML.documentBaseKey,
                        new URL("http://localhost"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            label.setText("<html><img src=''></html>");
        });
    }
}
