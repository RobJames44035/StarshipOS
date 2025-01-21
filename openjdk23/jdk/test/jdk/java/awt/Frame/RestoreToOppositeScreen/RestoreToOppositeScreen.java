/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * @test
 * @bug 8256373
 * @key headful
 * @summary setBounds() should work if the frame is minimized
 */
public final class RestoreToOppositeScreen {

    public static void main(String[] args) throws Exception {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if (!toolkit.isFrameStateSupported(Frame.ICONIFIED)) {
            return;
        }

        var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        for (GraphicsDevice gd1 : gds) {
            Rectangle screen1 = gd1.getDefaultConfiguration().getBounds();
            int x1 = (int) screen1.getCenterX();
            int y1 = (int) screen1.getCenterY();
            for (GraphicsDevice gd2 : gds) {
                Rectangle screen2 = gd2.getDefaultConfiguration().getBounds();
                // tweak the (x2, y2) point so even if the screen1 and screen2
                // are the same, we will use different bounds, otherwise
                // setBounds() will be ignored
                int x2 = (int) screen2.getCenterX() - 50;
                int y2 = (int) screen2.getCenterY() - 50;
                Frame frame = new Frame();
                try {
                    // show the frame on one monitor, and then move it to
                    // another while the frame minimized
                    frame.setBounds(x1, y1, 400, 400);
                    frame.setVisible(true);
                    Thread.sleep(2000);
                    frame.setExtendedState(Frame.ICONIFIED);
                    Thread.sleep(2000);
                    Rectangle before = new Rectangle(x2, y2, 380, 380);
                    frame.setBounds(before);
                    Thread.sleep(2000);
                    frame.setExtendedState(Frame.NORMAL);
                    Thread.sleep(2000);
                    Rectangle after = frame.getBounds();
                    checkSize(after.x, before.x, "x");
                    checkSize(after.y, before.y, "y");
                    checkSize(after.width, before.width, "width");
                    checkSize(after.height, before.height, "height");
                } finally {
                    frame.dispose();
                }
            }
        }
    }

    private static void checkSize(int actual, int expected, String prop) {
        if (Math.abs(actual - expected) > 10) { // let's allow size variation,
                                                // the bug is reproduced anyway
            System.err.println("Expected: " + expected);
            System.err.println("Actual: " + actual);
            throw new RuntimeException(prop + " is wrong");
        }
    }
}
