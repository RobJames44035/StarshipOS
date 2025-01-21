/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
  @test
  @key headful
  @summary unit test for getPointerInfo() from MouseInfo class
  @author prs@sparc.spb.su: area=
  @bug 4009555
  @run main MultiscreenPointerInfo
*/

import java.awt.*;

/**
 * Simply check the result on non-null and results are correct.
 */
public class MultiscreenPointerInfo
{
    private static final String successStage = "Test stage completed.Passed.";

    public static void main(String[] args) throws Exception {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();
        int gdslen = gds.length;
        System.out.println("There are " + gdslen + " Graphics Devices");
        if (gdslen < 2) {
            System.out.println("Nothing to be done. PASSED automatically.");
            return;
        }
        Rectangle rx = gds[1].getDefaultConfiguration().getBounds();
        Robot robot;

        if (rx.x == 0 && rx.y == 0) {
            // Assuming independent graphics devices
            robot = new Robot(gds[1]);
        } else {
            // Means we have a virtual device
            robot = new Robot(gds[0]);
        }
        robot.setAutoDelay(0);
        robot.setAutoWaitForIdle(true);
        robot.delay(10);
        robot.waitForIdle();
        Point p = new Point(rx.x + 101, rx.y + 99);
        robot.mouseMove(p.x, p.y);
        PointerInfo pi = MouseInfo.getPointerInfo();
        if (pi == null) {
            throw new RuntimeException("Test failed. getPointerInfo() returned null value.");
        } else {
            System.out.println(successStage);
        }

        Point piLocation = pi.getLocation();

        if (piLocation.x != p.x || piLocation.y != p.y) {
            throw new RuntimeException("Test failed.getPointerInfo() returned incorrect location.");
        } else {
            System.out.println(successStage);
        }

        GraphicsDevice dev = pi.getDevice();

        if (dev != gds[1]) {
            throw new RuntimeException("Test failed.getPointerInfo() returned incorrect device.");
        } else {
            System.out.println(successStage);
        }
        System.out.println("Test PASSED.");
    }
}
