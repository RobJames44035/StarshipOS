/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
  @test
  @bug 4924516
  @summary Verifies that SHOWING_CHANGED event is propagated to \
           HierarchyListeners then toolkit enabled
  @key headful
*/


import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShowingChangedEventTest
        implements AWTEventListener, HierarchyListener{
    private boolean eventRegisteredOnButton = false;

    private final JFrame frame = new JFrame("ShowingChangedEventTest");
    private final JPanel panel = new JPanel();
    private final JButton button = new JButton();


    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            ShowingChangedEventTest showingChangedEventTest
                    = new ShowingChangedEventTest();

            try {
                showingChangedEventTest.start();
            } finally {
                showingChangedEventTest.frame.dispose();
            }
        });
    }

    public void start ()  {
        frame.getContentPane().add(panel);
        panel.add(button);

        frame.pack();
        frame.setVisible(true);

        Toolkit.getDefaultToolkit()
                .addAWTEventListener(this, AWTEvent.HIERARCHY_EVENT_MASK);

        button.addHierarchyListener(this);
        panel.setVisible(false);

        if (!eventRegisteredOnButton){
            throw new RuntimeException("Event wasn't registered on Button.");
        }
    }

    @Override
    public void eventDispatched(AWTEvent awtevt) {
        if (awtevt instanceof HierarchyEvent) {
            HierarchyEvent hevt = (HierarchyEvent) awtevt;
            if (hevt != null && (hevt.getChangeFlags()
                    & HierarchyEvent.SHOWING_CHANGED) != 0) {
                System.out.println("Hierarchy event was received on Toolkit. "
                        + "SHOWING_CHANGED for "
                        + hevt.getChanged().getClass().getName());
            }
        }
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        if ((HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0) {
            System.out.println("Hierarchy event was received on Button. "
                    + "SHOWING_CHANGED for "
                    + e.getChanged().getClass().getName());
        }
        eventRegisteredOnButton = true;
    }
}
