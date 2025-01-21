/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.concurrent.atomic.AtomicInteger;

import jdk.test.lib.Platform;

/*
 * @test 1.2 98/08/05
 * @key headful
 * @bug 4373478 8079255
 * @summary Test mouse wheel functionality of Robot
 * @author bchristi: area=Robot
 * @library /test/lib
 * @build jdk.test.lib.Platform
 * @run main RobotWheelTest
 */
public class RobotWheelTest {

    private static final int NUMTESTS = 20;

    private static AtomicInteger wheelRotation = new AtomicInteger();
    private static int wheelSign = Platform.isOSX() ? -1 : 1;

    static Robot robot;

    static void waitTillSuccess(int i) {
        boolean success = false;

        for (int t = 0; t < 5; t++) {
            if (i == wheelSign * wheelRotation.get()) {
                success = true;
                break;
            }
            System.out.printf(
                    "attempt #%d failed. wheelRotation = %d, expected value = %d\n",
                    t, wheelRotation.get(), i
            );
            robot.delay(100);
        }

        if (!success) {
            throw new RuntimeException("wheelRotation = " + wheelRotation.get()
                    + ", expected value = " + i);
        }
    }

    public static void main(String[] args) throws Exception {
        robot = new Robot();

        Frame frame = null;
        try {
            frame = new Frame();
            frame.setSize(200, 200);
            Button button = new Button("WheelButton");
            button.addMouseWheelListener(e -> wheelRotation.addAndGet(e.getWheelRotation()));
            frame.add(button);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            robot.setAutoDelay(100);
            robot.waitForIdle();

            Rectangle bounds = frame.getBounds();
            int centerX = bounds.x + bounds.width / 2;
            int centerY = bounds.y + bounds.height / 2;
            robot.mouseMove(centerX, centerY);
            robot.waitForIdle();

            for (int i = -NUMTESTS; i <= NUMTESTS; i++) {
                if (i == 0) {
                    continue;
                }

                wheelRotation.set(0);

                robot.mouseWheel(i);
                robot.waitForIdle();

                waitTillSuccess(i);
            }
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
    }
}
