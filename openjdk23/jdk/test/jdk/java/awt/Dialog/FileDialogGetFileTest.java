/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4414105
 * @summary Tests that FileDialog returns null when cancelled
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FileDialogGetFileTest
 */

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;

public class FileDialogGetFileTest {
    static FileDialog fd;
    static Frame frame;

    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                1. Open FileDialog from "Show File Dialog" button.
                2. Click cancel button without selecting any file/folder.
                3. If FileDialog.getFile return null then test PASSES,
                   else test FAILS automatically.
                   """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(initialize())
                .logArea(4)
                .build()
                .awaitAndCheck();
    }

    public static Frame initialize() {
        frame = new Frame("FileDialog GetFile test");
        fd = new FileDialog(frame);
        fd.setFile("FileDialogGetFileTest.html");
        fd.setBounds(100, 100, 400, 400);
        Button showBtn = new Button("Show File Dialog");
        frame.add(showBtn);
        frame.pack();
        showBtn.addActionListener(e -> {
            fd.setVisible(true);
            if (fd.getFile() != null) {
                PassFailJFrame.forceFail("Test failed: FileDialog returned non-null value");
            } else {
                PassFailJFrame.log("Test Passed!");
            }
        });
        return frame;
    }
}
