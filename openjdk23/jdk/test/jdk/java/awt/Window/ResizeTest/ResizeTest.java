/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4225955
 * @summary Tests that focus lost is delivered to a lightweight component
 * in a disposed window
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ResizeTest
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;

public class ResizeTest
{
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
            1) Push button A to create modal dialog 2.
            2) Resize dialog 2, then click button B to hide it.
            3) Push button A again. Dialog B should be packed to its original
            size.
            4) Push button B again to hide, and A to reshow.
            Dialog B should still be the same size, then test is passed,
            otherwise failed.
            5) Push button B to hide the modal dialog and then select pass/fail.
            """;

        PassFailJFrame.builder()
            .title("Test Instructions")
            .instructions(INSTRUCTIONS)
            .columns(40)
            .testUI(ResizeTest::createUI)
            .build()
            .awaitAndCheck();
    }

    private static Frame createUI() {
        Frame f = new Frame("1");
        Dialog d = new Dialog(f, "2", true);
        d.setLocationRelativeTo(null);
        Button b2 = new Button("B");
        b2.addActionListener(e -> d.setVisible(false));
        d.setLayout(new BorderLayout());
        d.add(b2, BorderLayout.CENTER);

        Button b = new Button("A");
        f.add(b, BorderLayout.CENTER);
        b.addActionListener(e -> {
            d.pack();
            d.setVisible(true);
        });
        f.pack();
        return f;
    }
}
