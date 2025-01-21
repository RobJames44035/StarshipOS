/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.lang.Exception;
import java.lang.InterruptedException;
import java.lang.Object;
import java.lang.String;
import java.lang.Thread;

/*
 * @test
 * @bug 4097226
 * @summary Frame.setCursor() sometimes doesn't update the cursor until user moves the mouse
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FrameSetCursorTest
 */

public class FrameSetCursorTest {
    private static final String INSTRUCTIONS = """
            1. Keep the instruction dialog and TestFrame side by side so that
               you can read the instructions while doing the test
            2. Click on the 'Start Busy' button on the frame titled 'TestFrame'
               and DO NOT MOVE THE MOUSE ANYWHERE till you complete the steps below
            3. The cursor on the TestFrame changes to busy cursor
            4. If you don't see the busy cursor press 'Fail' after
               the `done sleeping` message
            5. If the busy cursor is seen, after 5 seconds the message
               'done sleeping' is displayed in the message window
            6. Check for the cursor type after the display of 'done sleeping'
            7. If the cursor on the TestFrame has changed back to default cursor
               (without you touching or moving the mouse), then press 'Pass'
               else if the frame still shows the busy cursor press 'Fail'
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("FrameSetCursorTest Instructions")
                .instructions(INSTRUCTIONS)
                .columns(45)
                .testUI(FrameSetCursorTest::createAndShowUI)
                .logArea(5)
                .build()
                .awaitAndCheck();

    }

    static Frame createAndShowUI() {
        Frame frame = new Frame("TestFrame");
        Panel panel = new Panel();
        Button busyButton = new Button("Start Busy");

        ActionListener actionListener = event -> {
            Object source = event.getSource();
            if (source == busyButton) {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {}
                PassFailJFrame.log("done sleeping");
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };

        busyButton.addActionListener(actionListener);
        panel.setLayout(new BorderLayout());
        panel.add("North", busyButton);

        frame.add(panel);
        frame.pack();
        frame.setSize(200, 200);
        return frame;
    }
}