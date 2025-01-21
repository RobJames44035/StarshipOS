/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import jtreg.SkippedException;

/*
 * @test
 * @bug 6267980
 * @summary PIT:Spurious MouseMoved events are triggered by Tray Icon
 * @library /java/awt/regtesthelpers /test/lib
 * @build PassFailJFrame jtreg.SkippedException
 * @run main/manual MouseMoveTest
 */

public class MouseMoveTest {
    private static SystemTray tray;
    private static TrayIcon icon;
    private static final String INSTRUCTIONS = """
            1) You will see a tray icon (white square) in notification area,
            2) Move mouse pointer to the icon and leave it somewhere inside the icon,
            3) Verify that MOUSE_MOVE events are NOT triggered after you have STOPPED
               moving mouse.
            4) If events are still triggered Press FAIL else PASS.
            """;

    public static void main(String[] args) throws Exception {
        if (!SystemTray.isSupported()) {
            throw new SkippedException("Test not applicable as"
                                       + " System Tray not supported");
        }

        PassFailJFrame passFailJFrame
                = PassFailJFrame.builder()
                                .title("TrayIcon Change Test Instructions")
                                .instructions(INSTRUCTIONS)
                                .columns(45)
                                .logArea()
                                .build();

        try {
            EventQueue.invokeAndWait(MouseMoveTest::createAndShowTrayIcon);
            passFailJFrame.awaitAndCheck();
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (tray != null) {
                    tray.remove(icon);
                }
            });
        }
    }

    private static void createAndShowTrayIcon() {
        BufferedImage img = new BufferedImage(32, 32,
                                              BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 32, 32);
        g.dispose();

        tray = SystemTray.getSystemTray();
        icon = new TrayIcon(img);
        icon.setImageAutoSize(true);

        icon.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent me){
                    PassFailJFrame.log(me.toString());
                }
        });

        try {
            tray.add(icon);
        } catch (AWTException e) {
            throw new RuntimeException("Error while adding"
                                       + " icon to system tray", e);
        }
    }
}
