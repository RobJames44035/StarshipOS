/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemTray;
import java.awt.TextArea;
import java.awt.TrayIcon;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

import jtreg.SkippedException;

/*
 * @test
 * @bug 6269309
 * @library /java/awt/regtesthelpers /test/lib
 * @build PassFailJFrame jtreg.SkippedException
 * @summary Tests that focus is transferred properly back
 *          to toplevel after clicking on a tray icon.
 * @run main/manual FocusLostAfterTrayTest
 */

public class FocusLostAfterTrayTest {
    private static SystemTray tray;
    private static TrayIcon icon;

    private static final String INSTRUCTIONS = """
            This test checks that focus is transferred properly back
            to top-level after clicking on a tray icon.

            When the test starts, a Frame with a text area & a RED tray icon
            are shown. If you don't see a tray icon please make sure that
            the tray area (also called Taskbar Status Area on MS Windows
            Notification Area on Gnome or System Tray on KDE) is visible.

            Click with a mouse inside a text area and make sure that it has
            received input focus. Then click on the tray icon and then back
            on the text area and verify that it has input focus again. Repeat
            the last step several times. Ensure that the text area always
            has the input focus and there are no "FOCUS LOST" event
            for text area in the log section.

            If above is true, Press PASS, else FAIL.
            """;

    public static void main(String[] args) throws Exception {
        if (!SystemTray.isSupported()) {
            throw new SkippedException("Test not applicable as"
                                       + " System Tray not supported");
        }

        try {
            PassFailJFrame.builder()
                          .title("FocusLostAfterTrayTest Instructions")
                          .instructions(INSTRUCTIONS)
                          .columns(40)
                          .testUI(FocusLostAfterTrayTest::createAndShowTrayIcon)
                          .logArea()
                          .build()
                          .awaitAndCheck();
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (tray != null) {
                    tray.remove(icon);
                }
            });
        }
    }

    private static Frame createAndShowTrayIcon() {
        Frame frame = new Frame("FocusLostAfterTrayTest");
        frame.setBounds(100, 300, 200, 200);
        frame.setLayout(new BorderLayout());
        TextArea ta = new TextArea();
        ta.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                PassFailJFrame.log("FOCUS GAINED: "
                                   + e.getComponent().getClass().toString());
            }
            @Override
            public void focusLost(FocusEvent e) {
                PassFailJFrame.log("FOCUS LOST !! "
                                   + e.getComponent().getClass().toString());
            }
        });
        frame.add(ta);

        BufferedImage image = new BufferedImage(16, 16,
                                                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                           RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.RED);
        g.fillRect(0, 0, 16, 16);
        g.dispose();
        tray = SystemTray.getSystemTray();
        icon = new TrayIcon(image);
        icon.setImageAutoSize(true);

        try {
            tray.add(icon);
        } catch (AWTException e) {
            throw new RuntimeException("Error while adding"
                                       + " icon to system tray", e);
        }
        return frame;
    }
}
