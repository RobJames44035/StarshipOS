/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 4173714
  @summary java.awt.button behaves differently under Win32/Solaris
  @author tdv@sparc.spb.su
  @library ../../../regtesthelpers
  @build Util
  @run main DisabledComponentsTest
*/

/**
 * DisabledComponentsTest.java
 *
 * summary: java.awt.button behaves differently under Win32/Solaris
 * Disabled component should not receive events. This is what this
 * test checks out.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

import test.java.awt.regtesthelpers.Util;

import javax.swing.*;

public class DisabledComponentsTest {

    private static Frame frame;
    private static Button b = new Button("Button");
    private static final AtomicBoolean pressed = new AtomicBoolean(false);
    private static final AtomicBoolean entered = new AtomicBoolean(false);

    private static void init() {
        frame = new Frame("Test");
        frame.setBounds(100, 100, 100, 100);
        b = new Button("Test");
        b.setEnabled(false);
        b.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.err.println("Mouse pressed. target=" + e.getSource());
                if (!b.isEnabled()) {
                    System.err.println("TEST FAILED: BUTTON RECEIVED AN EVENT WHEN DISABLED!");
                    pressed.set(true);
                }
            }
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse entered. target=" + e.getSource());
                if (!b.isEnabled())
                    System.err.println("TEST FAILED: BUTTON RECEIVED AN EVENT WHEN DISABLED!");
                entered.set(true);
            }
        });
        frame.add(b);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        try {
            Robot r = Util.createRobot();
            r.setAutoDelay(200);
            r.setAutoWaitForIdle(true);
            r.mouseMove(0, 0);
            SwingUtilities.invokeAndWait(DisabledComponentsTest::init);
            Util.waitForIdle(r);
            Util.pointOnComp(b, r);
            if (entered.get()) {
                throw new RuntimeException("TEST FAILED: disabled button received MouseEntered event");
            }
            Util.clickOnComp(b, r);
            if (pressed.get()) {
                throw new RuntimeException("TEST FAILED: disabled button received MousePressed event");
            }
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
    }
}
