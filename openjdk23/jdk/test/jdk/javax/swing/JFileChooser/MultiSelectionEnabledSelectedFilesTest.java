/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/*
 * @test
 * @bug 4834298
 * @key headful
 * @requires (os.family == "windows" | os.family == "linux")
 * @summary Test to check if the getSelectedFiles of JFilesChooser
 *          returns selectedFiles when Multi-Selection is enabled.
 * @run main/manual MultiSelectionEnabledSelectedFilesTest
 */
public class MultiSelectionEnabledSelectedFilesTest {
    private static JFrame frame;
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                try {
                    runTest();
                } finally {
                    frame.dispose();
                }
            }
        });
        System.out.println("Test Pass!");
    }

    static void runTest() {
        //Initialize the components
        String INSTRUCTIONS
                = "Instructions to Test:"+
                "\n1. Select a valid file using mouse double-click on first "+
                "dialog."+
                "\n2. After Selection, first dialog will close and second " +
                "dialog opens with multi-Selection enabled."+
                "\n3. Select the same file using mouse double-click without " +
                "moving mouse position or selection."+
                "\n4. If the selected file is updated then getSelectedFiles "+
                "returns the file selected and test will PASS otherwise test "+
                "will FAIL";

        JFileChooser chooser = new JFileChooser();
        JTextArea textArea = new JTextArea();
        frame = new JFrame("Test Instructions");

        textArea.setText(INSTRUCTIONS);
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(Color.black);
        textArea.setBackground(Color.white);

        frame.add(textArea);
        frame.pack();
        frame.setVisible(true);

        chooser.showOpenDialog(null);
        chooser.setMultiSelectionEnabled(true);
        chooser.showOpenDialog(null);
        File[] files = chooser.getSelectedFiles();

        if (files.length == 0) {
            throw new RuntimeException("Test Failed since selected files are empty!!");
        }
    }
}
