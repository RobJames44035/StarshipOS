/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

/*
 * @test
 * @bug 6977726
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Checks if JColorChooser.setPreviewPanel(JLabel) doesn't remove the preview panel but
 *          removes the content of the default preview panel
 * @run main/manual Test6977726
 */

public class Test6977726 {

    public static void main(String[] args) throws Exception {
        String instructions = """
                Check that there is a panel with "Text Preview Panel" text
                and with title "Preview" in the JColorChooser.
                Test passes if the panel is as described, test fails otherwise.

                Note: "Preview" title is not applicable for GTK Look and Feel.""";

        // In case this test is run with GTK L&F, the preview panel title
        // is missing due to the "ColorChooser.showPreviewPanelText" property
        // which is set to "Boolean.FALSE" for GTK L&F. Test instructions are
        // modified to reflect that "Preview" title is not applicable for GTK L&F.

        PassFailJFrame.builder()
                .title("Test6977726")
                .instructions(instructions)
                .rows(5)
                .columns(40)
                .testTimeOut(2)
                .testUI(Test6977726::createColorChooser)
                .build()
                .awaitAndCheck();
    }

    private static JColorChooser createColorChooser() {
        JColorChooser chooser = new JColorChooser(Color.BLUE);
        chooser.setPreviewPanel(new JLabel("Text Preview Panel"));
        return chooser;
    }
}
