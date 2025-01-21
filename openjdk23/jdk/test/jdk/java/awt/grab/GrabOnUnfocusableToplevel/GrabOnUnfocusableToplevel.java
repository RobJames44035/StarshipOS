/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6539458
  @summary JPopupMenu does not display if invoker is instance of JWindow
  @author oleg.sukhodolsky area=awt.grab
  @library ../../regtesthelpers
  @build Util
  @run main GrabOnUnfocusableToplevel
*/

/**
 * GrabOnUnfocusableToplevel.java
 *
 * summary: JPopupMenu does not display if invoker is instance of JWindow
 */

import java.awt.Robot;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

import test.java.awt.regtesthelpers.Util;

public class GrabOnUnfocusableToplevel {
    public static void main(String[] args) {
        Robot r = Util.createRobot();
        JWindow w = new JWindow();
        w.setSize(100, 100);
        w.setVisible(true);
        Util.waitForIdle(r);

        final JPopupMenu menu = new JPopupMenu();
        JButton item = new JButton("A button in popup");

        menu.add(item);

        w.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                menu.show(me.getComponent(), me.getX(), me.getY());

                System.out.println("Showing menu at " + menu.getLocationOnScreen() +
                                   " isVisible: " + menu.isVisible() +
                                   " isValid: " + menu.isValid());
                }
            });

        Util.clickOnComp(w, r);
        Util.waitForIdle(r);

        if (!menu.isVisible()) {
            throw new RuntimeException("menu was not shown");
        }

        menu.hide();
        System.out.println("Test passed.");
    }
}
