/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.awt.Color;
import java.awt.Font;
import javax.swing.JColorChooser;
import javax.swing.UIManager;

import jtreg.SkippedException;

/*
 * @test
 * @bug 4887836
 * @library /java/awt/regtesthelpers /test/lib
 * @build PassFailJFrame
 * @summary Checks for white area under the JColorChooser Swatch tab
 * @run main/manual Test4887836
 */

public class Test4887836 {

    public static void main(String[] args) throws Exception {

        // ColorChooser UI design is different for GTK L&F.
        // There is no Swatches tab available for GTK L&F, skip the testing.
        if (UIManager.getLookAndFeel().getName().contains("GTK")) {
            throw new SkippedException("Test not applicable for GTK L&F");
        }

        String instructions = """
                                If you do not see white area under the \"Swatches\" tab,
                                then test passed, otherwise it failed.""";

        PassFailJFrame.builder()
                .title("Test4759306")
                .instructions(instructions)
                .columns(40)
                .testUI(Test4887836::createColorChooser)
                .build()
                .awaitAndCheck();
    }

    private static JColorChooser createColorChooser() {
        JColorChooser chooser = new JColorChooser(Color.LIGHT_GRAY);

        UIManager.put("Label.font", new Font("Font.DIALOG", 0, 36));
        return chooser;
    }
}
