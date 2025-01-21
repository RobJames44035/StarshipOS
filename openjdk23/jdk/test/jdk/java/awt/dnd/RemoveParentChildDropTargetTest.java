/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
  @test
  @bug 4411368
  @summary tests the app doesn't crash if the child drop target is removed
           after the parent drop target is removed
  @key headful
  @run main RemoveParentChildDropTargetTest
*/

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.lang.reflect.InvocationTargetException;


public class RemoveParentChildDropTargetTest {

    static Frame frame;
    static Panel panel;
    static Label label;

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        EventQueue.invokeAndWait(() -> {
            frame = new Frame("RemoveParentChildDropTargetTest");
            panel = new Panel();
            label = new Label("Label");
            panel.add(label);
            frame.add(panel);
            frame.pack();

            panel.setDropTarget(new DropTarget(panel, new DropTargetAdapter() {
                public void drop(DropTargetDropEvent dtde) {}
            }));
            label.setDropTarget(new DropTarget(label, new DropTargetAdapter() {
                public void drop(DropTargetDropEvent dtde) {}
            }));
            panel.setDropTarget(null);
            frame.setVisible(true);

            label.setDropTarget(null);
        });

        EventQueue.invokeAndWait(() -> {
            if (frame != null) {
                frame.dispose();
            }
        });
    }
}
