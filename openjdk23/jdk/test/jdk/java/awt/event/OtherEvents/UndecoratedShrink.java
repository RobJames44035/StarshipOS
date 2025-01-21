/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Robot;

/*
 * @test
 * @bug 4418155
 * @key headful
 * @summary Checks Undecorated Frame repaints when shrinking
 */

public class UndecoratedShrink extends Frame {
    private static boolean passed = false;
    private static UndecoratedShrink frame;

    public static void main(String[] args) throws Exception {
        try {
            Robot robot = new Robot();
            EventQueue.invokeAndWait(() -> {
                frame = new UndecoratedShrink();
                frame.setUndecorated(true);
                frame.setSize(100, 100);
                frame.setVisible(true);
            });
            robot.waitForIdle();
            robot.delay(1000);

            EventQueue.invokeAndWait(() -> {
                frame.setSize(50, 50);
                frame.repaint();
            });
            robot.waitForIdle();
            robot.delay(500);

            if (!passed) {
                throw new RuntimeException("Test Fails." +
                        " Frame does not get repainted");
            }
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    @Override
    public void paint(Graphics g) {
        passed = true;
    }
}
