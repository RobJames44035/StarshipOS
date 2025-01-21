/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6895647
  @summary X11 Frame locations should be what we set them to
  @author anthony.petrov@oracle.com: area=awt.toplevel
  @run main FrameLocation
 */

import java.awt.*;

public class FrameLocation {
    private static final int X = 250;
    private static final int Y = 250;

    public static void main(String[] args) {
        Frame f = new Frame("test");
        f.setBounds(X, Y, 250, 250); // the size doesn't matter
        f.setVisible(true);

        for (int i = 0; i < 10; i++) {
            // 2 seconds must be enough for the WM to show the window
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }

            // Check the location
            int x = f.getX();
            int y = f.getY();

            if (x != X || y != Y) {
                throw new RuntimeException("The frame location is wrong! Current: " + x + ", " + y + ";  expected: " + X + ", " + Y);
            }

            // Emulate what happens when setGraphicsConfiguration() is called
            synchronized (f.getTreeLock()) {
                f.removeNotify();
                f.addNotify();
            }
        }

        f.dispose();
    }
}

