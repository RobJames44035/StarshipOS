/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @bug 5100950 8199723
  @summary textarea.getSelectedText() returns the de-selected text, on XToolkit
  @key headful
  @requires (os.family == "mac") | (os.family == "windows")
*/

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.TextField;

public class DeselectionDuringDoSelectionNonVisibleTest {
    static TextField tf1;
    static TextField tf2;
    static Frame frame;

    public static void main(String[] args) throws Exception {
        try {
            EventQueue.invokeAndWait(() -> {
                frame = new Frame("Deselection test");
                tf1 = new TextField("Text Field 1");
                tf2 = new TextField("Text Field 2");
                frame.add(tf1);
                frame.add(tf2);
                frame.setLayout(new FlowLayout());
                frame.setSize(200, 200);
                frame.setLocationRelativeTo(null);
                frame.pack();
                frame.setVisible(true);
            });

            boolean isWin = System.getProperty("os.name").startsWith("Win");
            System.out.println("is Windows OS? " + isWin);

            Thread.sleep(500);
            tf1.requestFocus();

            Thread.sleep(500);
            if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != tf1) {
                throw new RuntimeException("Test failed (TextField1 isn't focus owner).");
            }
            tf1.selectAll();
            Thread.sleep(500);

            tf2.requestFocus();
            Thread.sleep(500);

            if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != tf2) {
                throw new RuntimeException("Test failed (TextField2 isn't focus owner).");
            }
            tf2.selectAll();
            Thread.sleep(500);

            String selectedText = tf1.getSelectedText();
            String text = tf1.getText();

            System.out.println("tf1.getText()=" + text);
            System.out.println("tf1.getSelectedText()=" + selectedText);

            // Motif behaviour: After the selection of the second text, the first selected
            // text is unselected
            if (!selectedText.equals("") && !isWin) {
                throw new RuntimeException("Test failed (TextField1 isn't deselected).");
            }

            // Windows behaviour: After the selection of the second text, the first selected
            // text is only not highlighted
            if (!selectedText.equals(text) && isWin) {
                throw new RuntimeException("Test failed (TextField1 is deselected).");
            }
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}
