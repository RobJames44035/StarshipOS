/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4845868
 * @summary REGRESSION: First keystroke after JDialog is closed is lost
 * @key headful
 * @run main KeyStrokeTest
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyStrokeTest {
    static boolean keyTyped;
    static Frame frame;

    public static void main(String[] args) throws Exception {
        try {
            KeyStrokeTest test = new KeyStrokeTest();
            test.doTest();
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
    }

    private static void doTest() throws Exception {
        final Object monitor = new Object();
        frame = new Frame();
        TextField textField = new TextField() {
                public void transferFocus() {
                    System.err.println("transferFocus()");
                    final Dialog dialog = new Dialog(frame, true);
                    Button btn = new Button("Close It");
                    btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                System.err.println("action performed");
                                dialog.setVisible(false);
                            }
                        });
                    dialog.add(btn);
                    dialog.setSize(200, 200);
                    dialog.setVisible(true);
                }
            };

        textField.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    System.err.println(e);
                    if (e.getKeyChar() == 'a') {
                        keyTyped = true;
                    }

                    synchronized (monitor) {
                        monitor.notifyAll();
                    }
                }
            });
        frame.add(textField);
        frame.setSize(400, 400);
        frame.setVisible(true);

        Robot robot = new Robot();
        robot.waitForIdle();
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);

        robot.delay(1000);
        synchronized (monitor) {
            robot.keyPress(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_A);
            monitor.wait(3000);
        }

        if (!keyTyped) {
            throw new RuntimeException("TEST FAILED");
        }

        System.out.println("Test passed");
    }

}
