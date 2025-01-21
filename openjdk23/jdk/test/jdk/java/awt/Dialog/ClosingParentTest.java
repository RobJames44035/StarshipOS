/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

/*
 * @test
 * @bug 4336913
 * @summary On Windows, disable parent window controls while modal dialog is being created.
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ClosingParentTest
 */

public class ClosingParentTest {

    static String instructions = """
            When the test starts, you will see a Frame with a Button
            titled 'Show modal dialog with delay'. Press this button
            and before the modal Dialog is shown, try to close the
            Frame using X button or system menu for windowing systems
            which don't provide X button in Window decorations. The
            delay before Dialog showing is 5 seconds.
            If in test output you see message about WINDOW_CLOSING
            being dispatched, then test fails. If no such message
            is printed, the test passes.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("ClosingParentTest")
                .instructions(instructions)
                .testTimeOut(5)
                .rows(10)
                .columns(35)
                .testUI(ClosingParentTest::createGUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createGUI() {
        Frame frame = new Frame("Main Frame");
        Dialog dialog = new Dialog(frame, true);

        Button button = new Button("Show modal dialog with delay");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException x) {
                    x.printStackTrace();
                }

                dialog.setVisible(true);
            }
        });
        frame.add(button);
        frame.pack();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("WINDOW_CLOSING dispatched on the frame");
            }
        });

        dialog.setSize(100, 100);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });

        return frame;
    }
}
