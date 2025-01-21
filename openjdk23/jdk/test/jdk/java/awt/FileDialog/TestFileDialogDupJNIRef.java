/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * @test
 * @bug 4906972
 * @summary Tests using of JNI reference to peer object.
 * @requires (os.family == "windows")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual TestFileDialogDupJNIRef
 */

public class TestFileDialogDupJNIRef {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                This is a crash test.
                After test started you will see 'Test Frame' with one button.
                1. Click the button to open FileDialog.
                2. Go to the dialog and choose any directory with some files in it..
                3. Click on any file to highlight it.
                4. Click on the file again to rename.
                5. Leave the file in edit mode and click Open button

                If there was no crash the test passed, Press Pass.
                """;

        PassFailJFrame.builder()
                .title("TestFileDialogDupJNIRef Instruction")
                .instructions(INSTRUCTIONS)
                .columns(40)
                .testUI(TestFileDialogDupJNIRef::createUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createUI() {
        Frame frame = new Frame("TestFileDialogDupJNIRef Test Frame");
        Button open = new Button("Open File Dialog");

        open.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FileDialog fd = new FileDialog(frame);
                    fd.setVisible(true);
                }
            });

        frame.setLayout(new FlowLayout());
        frame.add(open);
        frame.setSize(250, 70);
        return frame;
    }
}
