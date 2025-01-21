/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8081507
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @requires (os.family == "linux")
 * @summary Verifies if Open/Save button shows text as Open/Save.
 * @run main/manual TestFileChooserOpenSaveButtonText
 */

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TestFileChooserOpenSaveButtonText {
    private static JFrame frame;
    private static final String INSTRUCTIONS =
                    "Instructions: \n\n" +
                      "1. Check the text on button in bottom panel is Open.\n" +
                      "2. Press Cancel button.\n" +
                      "3. Check the text on button in bottom panel is Save.\n" +
                      "4. Press Cancel button.\n" +
                      "5. If above instructions are confirmed, " +
                            "Press Pass else Fail.";

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        PassFailJFrame passFailJFrame = new PassFailJFrame(
                "JFileChooser Test Instructions", INSTRUCTIONS, 5, 8, 35);
        try {
            SwingUtilities.invokeAndWait(
                    TestFileChooserOpenSaveButtonText::createAndShowUI);
            passFailJFrame.awaitAndCheck();
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    private static void createAndShowUI() {
        frame = new JFrame("Test File Chooser Open/Save button text");
        PassFailJFrame.addTestWindow(frame);
        PassFailJFrame.positionTestWindow(
                frame, PassFailJFrame.Position.HORIZONTAL);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        fileChooser.showSaveDialog(null);
    }
}
