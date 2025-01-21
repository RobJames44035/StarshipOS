/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */
/*
  @test
  @bug 4390019
  @summary REGRESSION: Alt-F4 keybinding no longer shuts down java application on Windows
  @key headful
  @requires (os.family == "windows")
  @run main NoFocusOwnerAWTTest
*/
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.MenuBar;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class NoFocusOwnerAWTTest {

    static boolean actionFired = false;
    static boolean closingWindowCalled = false;
    static Frame frame;

    public static void main(String[] args) throws Exception {
        try {
            if (!System.getProperty("os.name").startsWith("Windows")) {
                // this test is Win32 test only
                return;
            }
            EventQueue.invokeAndWait(() -> {

                frame = new Frame("No Focus Owner AWT Test");
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.out.println("windowClosing() is called.");
                        closingWindowCalled = true;
                    }
                });
                frame.addFocusListener(new FocusListener() {
                    public void focusGained(FocusEvent fe) {
                        System.out.println("focus gained on frame");
                    }
                    public void focusLost(FocusEvent fe) {
                        System.out.println("focus lost on frame");
                    }
                });
                MenuBar mb = new MenuBar();
                Menu m = new Menu("This is Menu");
                MenuItem mi = new MenuItem("Menu Item");
                mi.setShortcut(new MenuShortcut(KeyEvent.VK_A));
                mi.addActionListener( new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        System.out.println("action");
                        actionFired = true;
                    }
                });
                m.add(mi);
                mb.add(m);
                frame.setMenuBar(mb);
                Label lb;
                frame.add(lb = new Label("press"));
                lb.addFocusListener(new FocusListener() {
                    public void focusGained(FocusEvent fe) {
                        System.out.println("focus gained on label");
                    }
                    public void focusLost(FocusEvent fe) {
                        System.out.println("focus lost on label");
                    }
                });
                frame.pack();
                frame.toFront();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });

            Robot robot = new Robot();
            robot.setAutoDelay(100);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_A);
            robot.waitForIdle();
            robot.keyRelease(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.waitForIdle();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_F4);
            robot.waitForIdle();
            robot.keyRelease(KeyEvent.VK_F4);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.waitForIdle();
            robot.delay(1000);

            if (!actionFired || !closingWindowCalled) {
                throw new RuntimeException("Test FAILED(actionFired="+actionFired+
                                       ";closingWindowCalled="+closingWindowCalled+")");
            }
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
    }
 }// class NoFocusOwnerAWTTest
