/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/*
 * @test
 * @bug 8322754
 * @summary Verifies clicking JComboBox during frame closure causes Exception
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ComboPopupBug
 */

public class ComboPopupBug {
    private static final String instructionsText = """
            This test is used to verify that clicking on JComboBox
            when frame containing it is about to close should not
            cause IllegalStateException.

            A JComboBox is shown with Close button at the bottom.
            Click on Close and then click on JComboBox arrow button
            to try to show combobox popup.
            If IllegalStateException is thrown, test will automatically Fail
            otherwise click Pass.""";

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("ComboPopup Instructions")
                .instructions(instructionsText)
                .testTimeOut(5)
                .rows(10)
                .columns(35)
                .testUI(ComboPopupBug::createUI)
                .build()
                .awaitAndCheck();
    }

    private static JFrame createUI() {
        JFrame frame = new JFrame("ComboPopup");

        JComboBox<String> cb = new JComboBox<>();
        cb.setEditable(true);
        cb.addItem("test");
        cb.addItem("test2");
        cb.addItem("test3");

        JButton b = new JButton("Close");
        b.addActionListener(
                (e)->{
                    try {
                        Thread.sleep(3000);
                    } catch (Exception ignored) {
                    }
                    frame.setVisible(false);
                });

        frame.getContentPane().add(cb, "North");
        frame.getContentPane().add(b, "South");
        frame.setSize(200, 200);

        return frame;
    }
}
