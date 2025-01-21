/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * @test
 * @bug 6227750
 * @summary Tests that FileDialog can be closed by clicking the 'close' (X) button
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual DoubleActionCloseX
 */

public class DoubleActionCloseX {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                NOTE: On Linux and Mac, there is no 'close'(X) button
                      when file dialog is visible, press Pass.

                Click the 'Open File Dialog' button to open FileDialog.
                A file dialog will appear on the screen.
                Click on the 'close'(X) button.
                The dialog should be closed.
                If not, the test failed, press Fail otherwise press Pass.
                """;

        PassFailJFrame.builder()
                .title("DoubleActionCloseX Instruction")
                .instructions(INSTRUCTIONS)
                .columns(40)
                .testUI(DoubleActionCloseX::createUI)
                .build()
                .awaitAndCheck();
    }
    public static Frame createUI() {
        Frame f = new Frame("DoubleActionCloseX Test");
        Button b = new Button("Open File Dialog");
        FileDialog fd = new FileDialog(f);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fd.setVisible(true);
            }
        });
        f.add(b);
        f.setSize(300, 200);
        return f;
    }
}
