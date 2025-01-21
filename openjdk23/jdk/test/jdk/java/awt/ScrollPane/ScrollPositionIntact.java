/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
/*
 @test
 @bug 6404832
 @summary Tests that scroll position is not changed by validate() for mode SCROLLBARS_NEVER
 @key headful
 @run main ScrollPositionIntact
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Robot;
import java.awt.ScrollPane;

public class ScrollPositionIntact {
    Frame frame;
    ScrollPane sp;
    Panel pa;
    public static final int X_POS = 100;

    public static void main(String[] args) throws Exception {
        ScrollPositionIntact test = new ScrollPositionIntact();
        test.init();
        test.start();
    }

    public void init() throws Exception {
        EventQueue.invokeAndWait(() -> {
            pa = new Panel();
            pa.setSize(600, 50);
            pa.setPreferredSize(new Dimension(600, 50));
            pa.setBackground(Color.red);
            sp = new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
            sp.setSize(200, 50);
            pa.setLayout(new GridLayout(1, 3));
            pa.add("West", new Label("west", Label.LEFT));
            pa.add("West", new Label());
            pa.add("East", new Label("East", Label.RIGHT));
            sp.add(pa);
            frame = new Frame("ScrollPositionIntact");
            frame.setSize(200, 100);
            frame.add(sp);
            frame.setVisible(true);
        });
    }

    public void start() throws Exception {
        try {
            Robot robot = new Robot();
            robot.waitForIdle();
            robot.delay(1000);

            EventQueue.invokeAndWait(() -> {
                frame.toFront();
                frame.requestFocus();

                sp.setScrollPosition(X_POS, sp.getScrollPosition().y);
                pa.validate();
                // Now, before the fix, in Windows XP, Windows XP theme and on Vista,
                // scrollposition would be reset to zero..
                sp.validate();

                int i = (int) (sp.getScrollPosition().getX());
                if (i <= 0) {
                    // actual position MAY be not equal to X_POS; still, it must be > 0.
                    throw new RuntimeException("Test failure: zero scroll position.\n\n");
                }
            });
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}
