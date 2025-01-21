/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 7154841
 * @requires (os.family == "mac")
 * @summary JPopupMenu is overlapped by a Dock on Mac OS X
 * @library /test/lib
 *          /lib/client
 * @build ExtendedRobot jdk.test.lib.Platform
 * @run main bug7154841
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.concurrent.atomic.AtomicReference;

import jdk.test.lib.Platform;

public class bug7154841 {

    private static final int STEP = 10;

    private static volatile boolean passed = false;
    private static JFrame frame;
    private static JPopupMenu popupMenu;
    private static AtomicReference<Rectangle> screenBounds = new AtomicReference<>();

    private static void initAndShowUI() {
        popupMenu = new JPopupMenu();
        for (int i = 0; i < 400; i++) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(" Test " + i);
            item.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    passed = true;
                }
            });
            popupMenu.add(item);
        }

        frame = new JFrame();
        screenBounds.set(getScreenBounds());
        frame.setBounds(screenBounds.get());
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        if (!Platform.isOSX()) {
            return; // Test only for Mac OS X
        }

        try {
            ExtendedRobot r = new ExtendedRobot();
            r.setAutoDelay(100);
            r.setAutoWaitForIdle(true);
            r.mouseMove(0, 0);

            SwingUtilities.invokeAndWait(bug7154841::initAndShowUI);

            r.waitForIdle(200);

            SwingUtilities.invokeAndWait(() -> {
                popupMenu.show(frame, frame.getX() + frame.getWidth() / 2, frame.getY() + frame.getHeight() / 2);
            });

            r.waitForIdle(200);

            int y = (int)screenBounds.get().getY() + (int)screenBounds.get().getHeight() - 10;
            int center = (int)(screenBounds.get().getX() + screenBounds.get().getWidth() / 2);
            for (int x = center - 10 * STEP; x < center + 10 * STEP; x += STEP) {
                r.mouseMove(x, y);
            }

            if (!passed) {
                throw new RuntimeException("Failed: no mouse events on the popup menu");
            }
        } finally {
            SwingUtilities.invokeLater(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    public static Rectangle getScreenBounds() {
        return GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .getBounds();
    }

}
