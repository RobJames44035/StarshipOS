/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug        6396785
  @summary    MenuItem activated with space should swallow this space.
  @library    ../../../regtesthelpers
  @build      Util
  @run        main MenuItemActivatedTest
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import test.java.awt.regtesthelpers.Util;

public class MenuItemActivatedTest {
    Robot robot;
    JFrame frame = new JFrame("Test Frame");
    JDialog dialog = new JDialog((Window)null, "Test Dialog", Dialog.ModalityType.DOCUMENT_MODAL);
    JTextField text = new JTextField();
    JMenuBar bar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenuItem item = new JMenuItem("item");
    AtomicBoolean gotEvent = new AtomicBoolean(false);

    public static void main(String[] args) {
        MenuItemActivatedTest app = new MenuItemActivatedTest();
        app.init();
        app.start();
    }

    public void init() {
        robot = Util.createRobot();
    }

    public void start() {
        menu.setMnemonic('f');
        menu.add(item);
        bar.add(menu);
        frame.setJMenuBar(bar);
        frame.pack();
        frame.setLocationRelativeTo(null);

        item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    dialog.add(text);
                    dialog.pack();
                dialog.setVisible(true);
                }
            });

        text.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == ' ') {
                        System.out.println(e.toString());
                        synchronized (gotEvent) {
                            gotEvent.set(true);
                            gotEvent.notifyAll();
                        }
                    }
                }
            });

        frame.setVisible(true);
        Util.waitForIdle(robot);

        robot.keyPress(KeyEvent.VK_ALT);
        robot.delay(20);
        robot.keyPress(KeyEvent.VK_F);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_F);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_ALT);
        Util.waitForIdle(robot);

        item.setSelected(true);
        Util.waitForIdle(robot);

        robot.keyPress(KeyEvent.VK_SPACE);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_SPACE);

        if (Util.waitForCondition(gotEvent, 2000)) {
            throw new TestFailedException("a space went into the dialog's text field!");
        }

        System.out.println("Test passed.");
    }
}

class TestFailedException extends RuntimeException {
    TestFailedException(String msg) {
        super("Test failed: " + msg);
    }
}
