/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
 * @test
 * @key headful
 * @bug 7153700
 * @summary Check for mouseMoved event for java.awt.TrayIcon
 * @author Dmitriy Ermashov (dmitriy.ermashov@oracle.com)
 * @library /lib/client ../
 * @build ExtendedRobot SystemTrayIconHelper
 * @run main MouseMovedTest
 */

public class MouseMovedTest {
    static volatile boolean moved;

    public static void main(String[] args) throws Exception {
        if (!SystemTray.isSupported()) {
            return;
        }

        if (SystemTrayIconHelper.isOel7orLater()) {
            return;
        }

        moved = false;

        TrayIcon icon = new TrayIcon(new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB), "Test icon");
        icon.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent event) {
                moved = true;
                System.out.println("Mouse moved");
            }
        });
        SystemTray.getSystemTray().add(icon);

        ExtendedRobot robot = new ExtendedRobot();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        if (System.getProperty("os.name").toLowerCase().startsWith("win"))
            robot.glide(size.width / 2, size.height-15, size.width, size.height-15, 1, 3);
        else
            robot.glide(size.width / 2, 13, size.width, 13, 1, 3);
        robot.mouseMove(size.width/2, size.height/2);

        if (!moved)
            throw new RuntimeException("Mouse moved action did not trigger");
    }
}
