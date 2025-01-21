/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
import java.awt.TrayIcon;
import java.awt.SystemTray;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.AWTException;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

/*
 * @test
 * @key headful
 * @summary Check if a action performed event is received when TrayIcon display
 *          message is clicked on.
 * @author Shashidhara Veerabhadraiah (shashidhara.veerabhadraiah@oracle.com)
 * @modules java.desktop/java.awt:open
 * @library /java/awt/patchlib
 * @library /lib/client ../
 * @build java.desktop/java.awt.Helper
 * @build ExtendedRobot SystemTrayIconHelper
 * @run main TrayIconPopupClickTest
 */

public class TrayIconPopupClickTest {

    TrayIcon icon;
    ExtendedRobot robot;
    boolean actionPerformed = false;

    public static void main(String[] args) throws Exception {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray not supported on the platform under test. " +
                    "Marking the test passed");
        } else {
            if (System.getProperty("os.name").toLowerCase().startsWith("win"))
                System.err.println("Test can fail if the icon hides to a tray icons pool " +
                        "in Windows 7/10, which is behavior by default.\n" +
                        "Set \"Right mouse click\" -> \"Customize notification icons\" -> " +
                        "\"Always show all icons and notifications on the taskbar\" true " +
                        "to avoid this problem. Or change behavior only for Java SE " +
                        "tray icon.");
            new TrayIconPopupClickTest().doTest();
        }
    }

    TrayIconPopupClickTest() throws Exception {
        robot = new ExtendedRobot();
        EventQueue.invokeAndWait(this::initializeGUI);
        robot.waitForIdle(1000);
    }

    private void initializeGUI() {
        SystemTray tray = SystemTray.getSystemTray();
        icon = new TrayIcon(new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB), "Sample Icon");
        icon.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                icon.displayMessage("Sample Icon", "This is a test message for the tray icon", TrayIcon.MessageType.INFO);
            }
        });

        try {
            tray.add(icon);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        icon.getActionCommand();
        icon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed = true;
            }
        });
    }

    void doTest() throws Exception {

        Point iconPosition = SystemTrayIconHelper.getTrayIconLocation(icon);
        if (iconPosition == null)
            throw new RuntimeException("Unable to find the icon location!");

        robot.mouseMove(iconPosition.x, iconPosition.y);
        robot.waitForIdle();
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(50);

        robot.mouseMove(iconPosition.x, iconPosition.y + 10);
        robot.waitForIdle();
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(50);

        if (!actionPerformed)
            throw new RuntimeException("FAIL: ActionEvent not triggered when " +
                    "tray icon message was clicked on");
    }
}
