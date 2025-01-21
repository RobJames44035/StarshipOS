/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;

import java.awt.event.MouseEvent;

/*
 * @test
 * @bug 4186663 4265525
 * @summary Tests that multiple PopupMenus cannot appear at the same time
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual MultiplePopupMenusTest
 */

public class MultiplePopupMenusTest {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                Click the right mouse button on the button
                If multiple popups appear at the same time the
                test fails else passes.
                """;

        PassFailJFrame.builder()
                .title("MultiplePopupMenusTest Instruction")
                .instructions(INSTRUCTIONS)
                .columns(30)
                .testUI(MultiplePopupMenusTest::createUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createUI() {
        Frame fr = new Frame("MultiplePopupMenusTest Test");
        TestButton button = new TestButton("button");
        fr.add(button);
        fr.setSize(200, 200);
        return fr;
    }

    static class TestButton extends Button {
        public TestButton(String title) {
            super(title);
            enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        }

        @Override
        public void processMouseEvent(MouseEvent e) {
            if (e.isPopupTrigger()) {
                for (int i = 0; i < 10; i++) {
                    PopupMenu pm = new PopupMenu("Popup " + i);
                    pm.add(new MenuItem("item 1"));
                    pm.add(new MenuItem("item 2"));
                    add(pm);
                    pm.show(this, e.getX() + i * 5, e.getY() + i * 5);
                }
            }
            super.processMouseEvent(e);
        }
    }
}
