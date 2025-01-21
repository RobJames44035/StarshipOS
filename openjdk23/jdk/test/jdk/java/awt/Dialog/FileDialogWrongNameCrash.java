/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Frame;

/*
 * @test
 * @bug 4779118
 * @summary Tests that FileDialog with wrong initial file name
 *          doesn't crash when Open button is pressed.
 * @requires (os.family == "windows")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FileDialogWrongNameCrash
 */

public class FileDialogWrongNameCrash {

    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                (This is Windows only test)
                1. You should see a frame 'Frame' with button 'Load'. Press button.",
                2. You should see 'Load file' dialog, select any file and press 'Open'",
                   (not 'Cancel'!!!). If Java doesn't crash - press PASS, else FAIL
                    """;
        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(initialize())
                .build()
                .awaitAndCheck();
    }

    private static Frame initialize() {
        Frame frame = new Frame("File Dialog Wrong Name Crash Test");
        Button fileButton = new Button("Load");
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                final java.awt.FileDialog selector =
                        new java.awt.FileDialog(frame);
                selector.setFile("Z:\\O2 XDA\\LogiTest\\\\Testcase.xml");
                selector.setVisible(true);
            }
        });
        frame.add(fileButton);
        frame.setSize(100, 60);
        return frame;
    }
}
