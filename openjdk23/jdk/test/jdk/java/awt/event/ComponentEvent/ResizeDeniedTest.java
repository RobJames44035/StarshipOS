/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
  @test
  @bug 4523758
  @requires (os.family == "windows")
  @summary Checks denied setBounds doesn't generate ComponentEvent
  @key headful
  @run main ResizeDeniedTest
*/

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.InvocationTargetException;

public class ResizeDeniedTest implements ComponentListener {
    static int runs = 0;
    static Frame frame;

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {

        ResizeDeniedTest resizeDeniedTest = new ResizeDeniedTest();
        EventQueue.invokeAndWait(() -> {
            frame = new Frame("ResizeDeniedTest");
            frame.addComponentListener(resizeDeniedTest);
            frame.setSize(1, 1);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        synchronized(resizeDeniedTest) {
            resizeDeniedTest.wait(2000);
        }

        if (frame != null) {
            EventQueue.invokeAndWait(() -> frame.dispose());
        }

        if (runs > 10) {
            System.out.println("Infinite loop");
            throw new RuntimeException("Infinite loop");
        }
    }

    public void componentHidden(ComponentEvent e) {}

    public void componentMoved(ComponentEvent e) {}

    public void componentResized(ComponentEvent e) {
        frame.setSize(1, 1);
        System.out.println("Size " + frame.getSize());
        ++runs;
        if (runs > 10) {
            System.out.println("Infinite loop");
            synchronized(this) {
                this.notify();
            }
            throw new RuntimeException("Infinite loop");
        }
    }

    public void componentShown(ComponentEvent e) {}
}
