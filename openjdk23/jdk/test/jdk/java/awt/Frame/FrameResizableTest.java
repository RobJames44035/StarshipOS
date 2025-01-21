/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionListener;

/*
 * @test
 * @bug 1231233
 * @summary  Tests whether the resizable property of a Frame is
 *           respected after it is set.
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FrameResizableTest
 */

public class FrameResizableTest {
    private static final String INSTRUCTIONS = """
            There is a frame with two buttons and a label.  The label
            reads 'true' or 'false' to indicate whether the frame can be
            resized or not.

            When the first button, 'Set Resizable', is
            clicked, you should be able to resize the frame.
            When the second button, 'UnSet Resizable', is clicked, you should
            not be able to resize the frame.

            A frame is resized in a way which depends upon the window manager (WM) running.
            You may resize the frame by dragging the corner resize handles or the borders,
            or you may use the title bar's resize menu items and buttons.

            Upon test completion, click Pass or Fail appropriately.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("FrameResizableTest Instructions")
                .instructions(INSTRUCTIONS)
                .columns(50)
                .testUI(FrameResizable::new)
                .build()
                .awaitAndCheck();
    }

    private static class FrameResizable extends Frame {
        Label label;
        Button buttonResizable;
        Button buttonNotResizable;

        public FrameResizable() {
            super("FrameResizable");
            setResizable(false);
            Panel panel = new Panel();

            add("North", panel);
            ActionListener actionListener = (e) -> {
                if (e.getSource() == buttonResizable) {
                    setResizable(true);
                } else if (e.getSource() == buttonNotResizable) {
                    setResizable(false);
                }
                label.setText("Resizable: " + isResizable());
            };

            panel.add(buttonResizable = new Button("Set Resizable"));
            panel.add(buttonNotResizable = new Button("UnSet Resizable"));
            panel.add(label = new Label("Resizable: " + isResizable()));
            buttonResizable.addActionListener(actionListener);
            buttonNotResizable.addActionListener(actionListener);

            setSize(400, 200);
        }
    }
}
