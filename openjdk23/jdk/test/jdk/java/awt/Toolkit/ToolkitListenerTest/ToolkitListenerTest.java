/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
  @test
  @bug 4460376
  @summary we should create Component-, Container- and HierarchyEvents if
  appropriate AWTEventListener added on Toolkit
  @key headful
*/

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.HierarchyEvent;
import java.lang.reflect.InvocationTargetException;

public class ToolkitListenerTest implements AWTEventListener
{
    public static Frame frame;
    static boolean containerEventReceived = false;
    static boolean componentEventReceived = false;
    static boolean hierarchyEventReceived = false;
    static boolean hierarchyBoundsEventReceived = false;

    public static void main(String[] args) throws Exception {
        ToolkitListenerTest test = new ToolkitListenerTest();
        test.start();
    }
    public void start() throws Exception {
        Toolkit.getDefaultToolkit().
            addAWTEventListener(this,
                AWTEvent.COMPONENT_EVENT_MASK |
                    AWTEvent.CONTAINER_EVENT_MASK |
                    AWTEvent.HIERARCHY_EVENT_MASK |
                    AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK);
        EventQueue.invokeAndWait(() -> {
            frame = new Frame("ToolkitListenerTest");
            frame.setSize(200, 200);
            frame.add(new Button());
            frame.setBounds(100, 100, 100, 100);
        });
        try {
            Toolkit.getDefaultToolkit().getSystemEventQueue().
                invokeAndWait(new Runnable() {
                    public void run() {}
                });

            EventQueue.invokeAndWait(() -> {
                if (!componentEventReceived) {
                    throw new RuntimeException("Test Failed: ComponentEvent " +
                        "was not dispatched");
                }
                if (!containerEventReceived) {
                    throw new RuntimeException("Test Failed: ContainerEvent " +
                        "was not dispatched");
                }
                if (!hierarchyEventReceived) {
                    throw new RuntimeException("Test Failed: " +
                        "HierarchyEvent(HIERARCHY_CHANGED) was not dispatched");
                }
                if (!hierarchyBoundsEventReceived) {
                    throw new RuntimeException("Test Failed: " +
                        "HierarchyEvent(ANCESTOR_MOVED or ANCESTOR_RESIZED) " +
                        "was not dispatched");
                }
            });
        } catch (InterruptedException ie) {
            throw new RuntimeException("Test Failed: InterruptedException " +
                "accured.");
        } catch (InvocationTargetException ite) {
            throw new RuntimeException("Test Failed: " +
                "InvocationTargetException accured.");
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    public void eventDispatched(AWTEvent e) {
        System.err.println(e);
        if (e instanceof ContainerEvent) {
            containerEventReceived = true;
        } else if (e instanceof ComponentEvent) {
            componentEventReceived = true;
        } else if (e instanceof HierarchyEvent) {
            switch (e.getID()) {
                case HierarchyEvent.HIERARCHY_CHANGED:
                    hierarchyEventReceived = true;
                    break;
                case HierarchyEvent.ANCESTOR_MOVED:
                case HierarchyEvent.ANCESTOR_RESIZED:
                    hierarchyBoundsEventReceived = true;
                    break;
            }
        }
    }
}
