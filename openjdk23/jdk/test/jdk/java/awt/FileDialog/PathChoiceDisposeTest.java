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
 * @bug 6240084
 * @summary Test that disposing unfurled list by the pressing ESC
 *          in FileDialog is working properly on XToolkit
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual PathChoiceDisposeTest
 */

public class PathChoiceDisposeTest {
    public static void main(String[] args) throws Exception {
        System.setProperty("sun.awt.disableGtkFileDialogs", "true");
        String INSTRUCTIONS = """
                1) Click on 'Show File Dialog' button to bring up the FileDialog window.
                2) Open the directory selection choice by clicking button next to
                   'Enter Path or Folder Name'. A drop-down will appear.
                3) Press 'ESC'.
                4) If you see that the dialog gets disposed and the popup
                   still remains on the screen, the test failed, otherwise passed.
                """;

        PassFailJFrame.builder()
                .title("PathChoiceDisposeTest Instruction")
                .instructions(INSTRUCTIONS)
                .columns(40)
                .testUI(PathChoiceDisposeTest::createUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createUI() {
        Frame f = new Frame("PathChoiceDisposeTest Test");
        Button b = new Button("Show File Dialog");
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
