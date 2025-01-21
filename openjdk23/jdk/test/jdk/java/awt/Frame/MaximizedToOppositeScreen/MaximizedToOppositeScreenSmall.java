/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

/**
 * @test
 * @bug 8176359 8231564 8211999
 * @key headful
 * @requires (os.family == "windows" | os.family == "mac")
 * @summary setMaximizedBounds() should work if set to the screen other than
 *          current screen of the Frame, the size of the frame is intentionally
 *          small
 */
public final class MaximizedToOppositeScreenSmall {

    public static void main(String[] args) throws Exception {
        //Supported platforms are Windows and OS X.
        String os = System.getProperty("os.name").toLowerCase();
        if (!os.contains("windows") && !os.contains("os x")) {
            return;
        }

        if (!Toolkit.getDefaultToolkit().
                isFrameStateSupported(Frame.MAXIMIZED_BOTH)) {
            return;
        }

        var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        Robot robot = new Robot();
        for (GraphicsDevice gd1 : gds) {
            Rectangle framAt = gd1.getDefaultConfiguration().getBounds();
            framAt.grow(-framAt.width / 2 + 100, -framAt.height / 2 + 100);
            for (GraphicsDevice gd2 : gds) {
                Frame frame = new Frame(gd1.getDefaultConfiguration());
                try {
                    frame.setLayout(null); // trigger use the minimum size of
                                           // the peer
                    frame.setBounds(framAt);

                    frame.setVisible(true);
                    robot.waitForIdle();
                    robot.delay(1000);

                    Dimension minimumSize = frame.getMinimumSize();
                    minimumSize.width = Math.max(minimumSize.width, 120);
                    minimumSize.height = Math.max(minimumSize.height, 120);
                    Rectangle maxTo = gd2.getDefaultConfiguration().getBounds();
                    maxTo.grow(-maxTo.width / 2 + minimumSize.width,
                               -maxTo.height / 2 + minimumSize.height);

                    frame.setMaximizedBounds(maxTo);
                    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                    robot.waitForIdle();
                    robot.delay(1000);

                    Rectangle actual = frame.getBounds();
                    if (!actual.equals(maxTo)) {
                        System.err.println("Actual: " + actual);
                        System.err.println("Expected: " + maxTo);
                        throw new RuntimeException("Wrong bounds");
                    }
                } finally {
                    frame.dispose();
                }
            }
        }
    }
}

