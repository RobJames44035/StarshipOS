/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import javax.swing.JColorChooser;
import javax.swing.JPanel;

/*
 * @test
 * @bug 4759306
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Checks if JColorChooser.setPreviewPanel removes the old one
 * @run main/manual Test4759306
 */
public class Test4759306 {

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("Test4759306")
                .instructions("Check that there is no panel titled \"Preview\" in the JColorChooser.")
                .rows(5)
                .columns(40)
                .testTimeOut(10)
                .splitUIRight(Test4759306::createColorChooser)
                .build()
                .awaitAndCheck();
    }

    private static JColorChooser createColorChooser() {
        JColorChooser chooser = new JColorChooser();
        chooser.setPreviewPanel(new JPanel());
        return chooser;
    }
}
