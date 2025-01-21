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
 * @bug 6240074
 * @summary Test that file drop-down field in FileDialog is working properly on XToolkit
 * @requires (os.family == "linux")
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual PathChoiceWorkArrowsTest
 */

public class PathChoiceWorkArrowsTest {
    public static void main(String[] args) throws Exception {
        System.setProperty("sun.awt.disableGtkFileDialogs", "true");
        String INSTRUCTIONS = """
                This is only XAWT test.

                1) Click on 'Show File Dialog' to bring up the FileDialog window.
                   A file dialog would come up.
                2) Click on the button next to 'Enter folder name' field.
                   A drop-down will appear. After this, there are 2 scenarios.
                3) Press the down arrow one by one. You will see a '/' being
                   appended as soon as the current entry is removed.
                   Keep pressing till the last entry is reached. Now the drop-down
                   will stop responding to arrow keys. If yes, the test failed.
                4) Press the up arrow. The cursor will directly go to the last
                   entry ('/') and navigation will stop there. You will see 2
                   entries being selected at the same time.
                   If yes, the test failed.
                """;

        PassFailJFrame.builder()
                .title("PathChoiceWorkArrowsTest Instruction")
                .instructions(INSTRUCTIONS)
                .columns(40)
                .testUI(PathChoiceWorkArrowsTest::createUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createUI() {
        Frame f = new Frame("PathChoiceWorkArrowsTest Test");
        Button b = new Button("Show File Dialog");
        FileDialog fd = new FileDialog(f);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fd.setSize(200, 200);
                fd.setLocation(200, 200);
                fd.setVisible(true);
            }
        });
        f.add(b);
        f.setSize(300, 200);
        return f;
    }
}
