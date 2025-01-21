/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 6191390 8154328
 * @key headful
 * @summary Verify that ActionEvent is received with correct modifiers set.
 * @modules java.desktop/java.awt:open
 * @modules java.desktop/java.awt.peer
 * @library /lib/client ../
 * @library /java/awt/patchlib
 * @build java.desktop/java.awt.Helper
 * @build ExtendedRobot SystemTrayIconHelper
 * @run main ActionEventTest
 */

import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.SystemTray;
import java.awt.Robot;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ActionEventTest {

    Image image;
    TrayIcon icon;
    Robot robot;
    boolean actionPerformed;

    public static void main(String[] args) throws Exception {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray not supported on the platform." +
                " Marking the test passed.");
        } else {
            if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
                System.err.println(
                    "Test can fail on Windows platform\n"+
                    "On Windows 7, by default icon hides behind icon pool\n" +
                    "Due to which test might fail\n" +
                    "Set \"Right mouse click\" -> " +
                    "\"Customize notification icons\" -> \"Always show " +
                    "all icons and notifications on the taskbar\" true " +
                    "to avoid this problem.\nOR change behavior only for " +
                    "Java SE tray icon and rerun test.");
            }

            ActionEventTest test = new ActionEventTest();
            test.doTest();
            test.clear();
        }
    }

    public ActionEventTest() throws Exception {
        robot = new Robot();
        robot.setAutoDelay(25);
        EventQueue.invokeAndWait(this::initializeGUI);
        robot.waitForIdle();
        robot.delay(500);
    }

    private void initializeGUI() {

        icon = new TrayIcon(
            new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB), "ti");
        icon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionPerformed = true;
                int md = ae.getModifiers();
                int expectedMask = ActionEvent.ALT_MASK | ActionEvent.CTRL_MASK
                        | ActionEvent.SHIFT_MASK;

                if ((md & expectedMask) != expectedMask) {
                    clear();
                    throw new RuntimeException("Action Event modifiers are not"
                        + " set correctly.");
                }
            }
        });

        try {
            SystemTray.getSystemTray().add(icon);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        SystemTray.getSystemTray().remove(icon);
    }

    void doTest() throws Exception {
        Point iconPosition = SystemTrayIconHelper.getTrayIconLocation(icon);
        if (iconPosition == null) {
            throw new RuntimeException("Unable to find the icon location!");
        }

        robot.mouseMove(iconPosition.x, iconPosition.y);
        robot.waitForIdle();

        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_CONTROL);

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.waitForIdle();
        if (!actionPerformed) {
            robot.delay(500);
        }
    }
}
