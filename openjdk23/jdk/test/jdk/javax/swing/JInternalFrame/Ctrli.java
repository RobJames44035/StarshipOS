/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/*
 * @test
 * @bug 4199401
 * @summary DefaultFocusManager interferes with comps that
 *          return true to isManagingFocus().
 * @key headful
 * @run main Ctrli
 */

public class Ctrli {
    private static JFrame frame;
    private static JComponent keyecho;
    private static volatile boolean iPressed = false;
    private static volatile Point compLoc;
    private static volatile int compWidth;
    private static volatile int compHeight;

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(50);
        robot.setAutoWaitForIdle(true);
        try {
            SwingUtilities.invokeAndWait(Ctrli::createAndShowUI);
            robot.waitForIdle();
            robot.delay(1000);

            SwingUtilities.invokeAndWait(() -> {
                compLoc = keyecho.getLocationOnScreen();
                compWidth = keyecho.getWidth();
                compHeight = keyecho.getHeight();
            });

            robot.mouseMove(compLoc.x + compWidth / 2, compLoc.y + compHeight / 2);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.waitForIdle();

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_I);
            robot.waitForIdle();
            robot.keyRelease(KeyEvent.VK_I);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.waitForIdle();

            if (!iPressed) {
                throw new RuntimeException("Test failed: CTRL+I not pressed.");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    private static void createAndShowUI() {
        frame = new JFrame("Test Ctrl+I operation");
        keyecho = new JComponent() {
            public boolean isManagingFocus() {
                return true;
            }
        };
        KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK)
                        && (e.getKeyCode() == 73))
                    iPressed = true;
            }

            public void keyTyped(KeyEvent e) {
                if (!iPressed) {
                    throw new RuntimeException("Test failed: CTRL+I not pressed.");
                }
            }
        };

        MouseListener mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                keyecho.requestFocus();
            }
        };

        keyecho.addKeyListener(keyListener);
        keyecho.addMouseListener(mouseListener);
        frame.setLayout(new BorderLayout());
        frame.add(keyecho);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
