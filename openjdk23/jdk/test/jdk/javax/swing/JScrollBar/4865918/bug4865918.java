/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 4865918
 * @requires (os.family != "mac")
 * @summary REGRESSION:JCK1.4a-runtime api/javax_swing/interactive/JScrollBarTests.html#JScrollBar
 * @run main bug4865918
 */

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import java.util.Date;

public class bug4865918 {

    private static TestScrollBar sbar;
    private static final CountDownLatch mousePressLatch = new CountDownLatch(1);

    public static void main(String[] argv) throws Exception {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("os x")) {
            System.out.println("This test is not for MacOS, considered passed.");
            return;
        }
        SwingUtilities.invokeAndWait(() -> setupTest());

        SwingUtilities.invokeAndWait(() -> sbar.pressMouse());
        if (!mousePressLatch.await(2, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timed out waiting for mouse press");
        }

        if (getValue() != 9) {
            throw new RuntimeException("The scrollbar block increment is incorrect");
        }
    }

    private static int getValue() throws Exception {
        final int[] result = new int[1];

        SwingUtilities.invokeAndWait(() -> {
            result[0] = sbar.getValue();
        });

        System.out.println("value " + result[0]);
        return result[0];
    }

    private static void setupTest() {

        sbar = new TestScrollBar(JScrollBar.HORIZONTAL, -1, 10, -100, 100);
        sbar.setPreferredSize(new Dimension(200, 20));
        sbar.setBlockIncrement(10);
        sbar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mousePressLatch.countDown();
            }
        });

    }

    static class TestScrollBar extends JScrollBar {

        public TestScrollBar(int orientation, int value, int extent,
                int min, int max) {
            super(orientation, value, extent, min, max);

        }

        public void pressMouse() {
            MouseEvent me = new MouseEvent(sbar,
                    MouseEvent.MOUSE_PRESSED,
                    (new Date()).getTime(),
                    MouseEvent.BUTTON1_DOWN_MASK,
                    3 * getWidth() / 4, getHeight() / 2,
                    1, true);
            processMouseEvent(me);
        }
    }
}
