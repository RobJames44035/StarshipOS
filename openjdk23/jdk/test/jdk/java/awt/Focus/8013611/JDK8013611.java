/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug      8013611
  @summary  Tests showing a modal dialog with requesting focus in frame.
  @author   Anton.Tarasov: area=awt.focus
  @library  ../../regtesthelpers
  @build    Util
  @run      main JDK8013611
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import test.java.awt.regtesthelpers.Util;

import java.awt.*;

public class JDK8013611 extends JFrame {
    static JTextField textField = new JTextField("text");
    static JButton button1 = new JButton("button1");
    static JButton button2 = new JButton("button2");
    static Robot robot;

    static JDialog dialog;
    static JButton button3 = new JButton("button3");

    public static void main(String[] args) {
        robot = Util.createRobot();

        JDK8013611 frame = new JDK8013611();
        frame.setLayout(new FlowLayout());
        frame.add(textField);
        frame.add(button1);
        frame.add(button2);
        frame.pack();

        dialog = new JDialog(frame, true);
        dialog.add(button3);
        dialog.pack();

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                dialog.setVisible(true);
            }
        });

        button1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                button2.requestFocusInWindow();
            }
        });

        frame.setVisible(true);

        frame.test();
    }

    public void test() {
        if (!testFocused(textField)) {
            Util.clickOnComp(textField, robot);
            if (!testFocused(textField)) {
                throw new RuntimeException("Error: couldn't focus " + textField);
            }
        }

        robot.keyPress(KeyEvent.VK_TAB);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_TAB);

        if (!testFocused(button3)) {
            throw new RuntimeException("Test failed: dialog didn't get focus!");
        }

        System.out.println("Test passed.");
    }

    boolean testFocused(Component c) {
        for (int i=0; i<10; i++) {
            if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == c) {
                return true;
            }
            Util.waitForIdle(robot);
        }
        return false;
    }
}
