/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 7199180
 * @summary [macosx] Dead keys handling for input methods
 * @author alexandr.scherbatiy area=awt.event
 * @library /test/lib
 * @build jdk.test.lib.Platform
 * @run main DeadKeyMacOSXInputText
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

import jdk.test.lib.Platform;

public class DeadKeyMacOSXInputText {

    private static volatile int state = 0;

    public static void main(String[] args) throws Exception {

        if (!Platform.isOSX()) {
            return;
        }

        Robot robot = new Robot();
        robot.setAutoDelay(50);

        createAndShowGUI(robot);

        // Pressed keys: Alt + E + A
        // Results:  ALT + VK_DEAD_ACUTE + a with accute accent
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_ALT);

        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.waitForIdle();

        if (state != 3) {
            throw new RuntimeException("Wrong number of key events.");
        }
    }

    static void createAndShowGUI(Robot robot) {
        Frame frame = new Frame();
        frame.setSize(300, 300);
        Panel panel = new Panel(new BorderLayout());
        JTextField textField = new JTextField();
        textField.addKeyListener(new DeadKeyListener());
        panel.add(textField, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
        robot.waitForIdle();

        textField.requestFocusInWindow();
        robot.waitForIdle();

    }

    static class DeadKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            char keyChar = e.getKeyChar();

            switch (state) {
                case 0:
                    if (keyCode != KeyEvent.VK_ALT) {
                        throw new RuntimeException("Alt is not pressed.");
                    }
                    state++;
                    break;
                case 1:
                    if (keyCode != KeyEvent.VK_DEAD_ACUTE) {
                        throw new RuntimeException("Dead ACUTE is not pressed.");
                    }
                    if (keyChar != 0xB4) {
                        throw new RuntimeException("Pressed char is not dead acute.");
                    }
                    state++;
                    break;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            int keyCode = e.getKeyCode();
            char keyChar = e.getKeyChar();

            if (state == 2) {
                if (keyCode != 0) {
                    throw new RuntimeException("Key code should be undefined.");
                }
                if (keyChar != 0xE1) {
                    throw new RuntimeException("A char does not have ACCUTE accent");
                }
                state++;
            } else {
                throw new RuntimeException("Wron number of keyTyped events.");
            }
        }
    }
}
