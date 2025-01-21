/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;

/*
  @test
  @bug 4462285
  @summary tests that DropTarget.addNotify doesn't throw NPE if peer hierarchy
           is incomplete
  @key headful
  @run main DropTargetAddNotifyNPETest
*/

public class DropTargetAddNotifyNPETest {

    volatile Component component1;
    volatile Component component2;
    volatile Frame frame;
    volatile DropTargetListener dtListener;
    volatile DropTarget dropTarget1;
    volatile DropTarget dropTarget2;

    public static void main(String[] args) throws Exception {
        DropTargetAddNotifyNPETest test = new DropTargetAddNotifyNPETest();
        EventQueue.invokeAndWait(() -> {
            test.init();
            if (test.frame != null) {
                test.frame.dispose();
            }
        });
    }

    public void init() {
        component1 = new LWComponent();
        component2 = new LWComponent();
        frame = new Frame("DropTargetAddNotifyNPETest");
        dtListener = new DropTargetAdapter() {
            public void drop(DropTargetDropEvent dtde) {
                dtde.rejectDrop();
            }
        };
        dropTarget1 = new DropTarget(component1, dtListener);
        dropTarget2 = new DropTarget(component2, dtListener);

        frame.add(component2);
        component1.addNotify();
        component2.addNotify();
    }
}

class LWComponent extends Component {}
