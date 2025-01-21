/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4313052
 * @summary Test cursor changes after mouse dragging ends
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ListDragCursor
 */

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;

public class ListDragCursor {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                1. Move mouse to the TextArea.
                2. Press the left mouse button.
                3. Drag mouse to the list.
                4. Release the left mouse button.

                If the mouse cursor starts as a Text Line Cursor and changes
                to a regular Pointer Cursor, then Hand Cursor when hovering
                the list, pass the test. This test fails if the cursor does
                not update at all when pointing over the different components.
                """;

        PassFailJFrame.builder()
                .title("Test Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(ListDragCursor::createUI)
                .build()
                .awaitAndCheck();
    }

    public static Frame createUI() {
        Frame frame = new Frame("Cursor change after drag");
        Panel panel = new Panel();

        List list = new List(2);
        list.add("List1");
        list.add("List2");
        list.add("List3");
        list.add("List4");
        list.setCursor(new Cursor(Cursor.HAND_CURSOR));

        TextArea textArea = new TextArea(3, 5);
        textArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        panel.add(textArea);
        panel.add(list);

        frame.add(panel);
        frame.setBounds(300, 100, 300, 150);
        return frame;
    }
}
