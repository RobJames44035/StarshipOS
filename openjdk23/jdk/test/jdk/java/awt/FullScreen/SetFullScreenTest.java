/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import jtreg.SkippedException;

import static java.awt.EventQueue.invokeAndWait;

/*
 * @test
 * @key headful
 * @bug 8312518
 * @library /test/lib
 * @summary Setting fullscreen window using setFullScreenWindow() shows up
 *          as black screen on newer macOS versions (13 & 14).
 */

public class SetFullScreenTest {
    private static Frame frame;
    private static GraphicsDevice gd;
    private static Robot robot;
    private static volatile int width;
    private static volatile int height;

    public static void main(String[] args) throws Exception {
        try {
            robot = new Robot();
            invokeAndWait(() -> {
                gd = GraphicsEnvironment.getLocalGraphicsEnvironment().
                        getDefaultScreenDevice();
                if (!gd.isFullScreenSupported()) {
                    throw new SkippedException("Full Screen mode not supported");
                }
            });

            invokeAndWait(() -> {
                frame = new Frame("Test FullScreen mode");
                frame.setBackground(Color.RED);
                frame.setSize(100, 100);
                frame.setLocation(10, 10);
                frame.setVisible(true);
            });
            robot.delay(1000);

            invokeAndWait(() -> gd.setFullScreenWindow(frame));
            robot.waitForIdle();
            robot.delay(300);

            invokeAndWait(() -> {
                width = gd.getFullScreenWindow().getWidth();
                height = gd.getFullScreenWindow().getHeight();
            });

            if (!robot.getPixelColor(width / 2, height / 2).equals(Color.RED)) {
                System.err.println("Actual color: " + robot.getPixelColor(width / 2, height / 2)
                                    + " Expected color: " + Color.RED);
                throw new RuntimeException("Test Failed! Window not in full screen mode");
            }
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
    }
}
