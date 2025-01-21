/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4942457
 * @key headful
 * @summary Verifies that filtering of resize events on native level works.
 * I.E.after Frame is shown no additional resize events are generated.
 * @library /java/awt/patchlib ../../regtesthelpers
 * @build java.desktop/java.awt.Helper
 * @build Util
 * @run main NoResizeEvent
 */

import test.java.awt.regtesthelpers.Util;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class NoResizeEvent {
    //Mutter can send window insets too late, causing additional resize events.
    private static final boolean IS_MUTTER = Util.getWMID() == Util.MUTTER_WM;
    private static final int RESIZE_COUNT_LIMIT = IS_MUTTER ? 5 : 3;
    private static Frame frame;
    static int resize_count = 0;

    public static void main(String[] args) throws Exception {
        try {
            EventQueue.invokeAndWait(() -> createUI());
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
            if (resize_count > RESIZE_COUNT_LIMIT) {
                throw new RuntimeException("Resize event arrived: "
                    + resize_count + " times.");
            }
        }
    }

    private static void createUI() {
        frame = new Frame("NoResizeEvent");
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                System.out.println(e);
                resize_count++;
            }
        });
        frame.setVisible(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
        }
        System.out.println("Resize count: " + resize_count);
    }
}
