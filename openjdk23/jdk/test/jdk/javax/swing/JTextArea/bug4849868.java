/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4849868
 * @summary Tests if JTextArea.getSelectionEnd works correctly
 * @key headful
 * @run main bug4849868
 */

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class bug4849868 {

    private static volatile boolean passed = false;

    private static JTextArea textArea;
    private static JFrame f;
    private static Point p;

    private static int end;
    private static int len;

    public static void main(String[] args) throws Exception {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(100);
            robot.setAutoWaitForIdle(true);

            SwingUtilities.invokeAndWait(() -> {
                f = new JFrame("bug4849868");
                textArea = new JTextArea("1234");
                textArea.setLineWrap(true);
                JScrollPane pane = new JScrollPane(textArea,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                f.getContentPane().add(pane);
                f.setSize(300, 300);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            });

            robot.waitForIdle();
            robot.delay(1000);

            SwingUtilities.invokeAndWait(() ->
                    p = textArea.getLocationOnScreen());

            robot.mouseMove(p.x, p.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseMove(p.x + 350, p.y);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            robot.delay(1000);

            SwingUtilities.invokeAndWait(() -> {
                end = textArea.getSelectionEnd();
                len = textArea.getDocument().getLength();
            });
            passed = (end <= len);

            System.out.println("end: " + end);
            System.out.println("len: " + len);
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (f != null) {
                    f.dispose();
                }
            });
        }

        if (!passed) {
            throw new RuntimeException("Test failed.");
        }
    }
}
