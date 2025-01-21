/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug        6396785
  @summary    Action key pressed on a button should be swallowed.
  @library    ../../../regtesthelpers
  @build      Util
  @run        main ButtonActionKeyTest
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;
import test.java.awt.regtesthelpers.Util;

public class ButtonActionKeyTest {
    Robot robot;
    JFrame frame = new JFrame("Frame");
    JButton button = new JButton("button");
    JTextField text = new JTextField("text");
    AtomicBoolean gotEvent = new AtomicBoolean(false);

    public static void main(String[] args) {
        ButtonActionKeyTest app = new ButtonActionKeyTest();
        app.init();
        app.start();
    }

    public void init() {
        robot = Util.createRobot();
    }

    public void start() {
        frame.setLayout(new FlowLayout());
        frame.add(button);
        frame.add(text);
        frame.pack();

        button.getInputMap().put(KeyStroke.getKeyStroke("A"), "GO!");
        button.getActionMap().put("GO!", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action performed!");
                text.requestFocusInWindow();
            }
        });

        text.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == 'a') {
                        System.out.println(e.toString());
                        synchronized (gotEvent) {
                            gotEvent.set(true);
                            gotEvent.notifyAll();
                        }
                    }
                }
            });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Util.waitForIdle(robot);

        Util.clickOnComp(button, robot);
        Util.waitForIdle(robot);

        if (!button.isFocusOwner()) {
            throw new Error("Test error: a button didn't gain focus.");
        }

        robot.keyPress(KeyEvent.VK_A);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_A);

        if (Util.waitForCondition(gotEvent, 2000)) {
            throw new TestFailedException("an action key went into the text field!");
        }

        System.out.println("Test passed.");
    }
}

class TestFailedException extends RuntimeException {
    TestFailedException(String msg) {
        super("Test failed: " + msg);
    }
}
