/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
  @test
  @bug 4394789
  @summary KeyboardFocusManager.upFocusCycle is not working for Swing properly
  @key headful
  @run main UpFocusCycleTest
*/
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Robot;
import javax.swing.DefaultFocusManager;
import javax.swing.JButton;
import javax.swing.JFrame;

public class UpFocusCycleTest {
    static boolean isFailed = true;
    static Object sema = new Object();
    static JFrame frame;

    public static void main(String[] args) throws Exception {
        try {
            Robot robot = new Robot();
            EventQueue.invokeAndWait(() -> {

                frame = new JFrame("Test frame");

                Container container1 = frame.getContentPane();
                container1.setBackground(Color.yellow);

                JButton button = new JButton("Button");
                button.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent fe) {
                        DefaultKeyboardFocusManager manager = new DefaultFocusManager();
                        manager.upFocusCycle(button);
                        System.out.println("Button receive focus");
                        frame.addFocusListener(new FocusAdapter() {
                            public void focusGained(FocusEvent fe) {
                                System.out.println("Frame receive focus");
                                synchronized (sema) {
                                    isFailed = false;
                                    sema.notifyAll();
                                }
                            }
                        });
                    }
                });
                container1.add(button,BorderLayout.WEST);
                button.requestFocus();
                frame.setSize(300,300);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
            robot.waitForIdle();
            robot.delay(1000);
            if (isFailed) {
                System.out.println("Test FAILED");
                throw new RuntimeException("Test FAILED");
            } else {
                System.out.println("Test PASSED");
            }
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
    }
 }// class UpFocusCycleTest
