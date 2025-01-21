/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4246045
   @summary AbstractButton fires accessible PropertyChangeEvent incorrectly
   @key headful
   @run main bug4246045
*/

import java.awt.Container;
import java.awt.Robot;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleState;

public class bug4246045 {

    class Listener implements PropertyChangeListener {
        boolean state = false; // focused or not

        public void propertyChange(PropertyChangeEvent e) {
            if (e.getPropertyName().equals(
                  AccessibleContext.ACCESSIBLE_STATE_PROPERTY)) {

                boolean reported = false;
                if (e.getNewValue() == null) {
                    reported = false;
                } else if (e.getNewValue().equals(AccessibleState.FOCUSED)) {
                    reported = true;
                } else {
                    throw new RuntimeException("Unknown value of ACCESSIBLE_STATE_PROPERTY");
                }

                if (!state == reported) {
                    state = reported;
                } else {
                    throw new RuntimeException("Bad value of ACCESSIBLE_STATE_PROPERTY");
                }
            }
        }
    }

    static JFrame frame;
    static JButton btn;
    static JToggleButton tb;
    static JTextField dummy;

    public void init() {
        btn = new JButton("JButton");
        tb = new JToggleButton("JToggleButton");
        dummy = new JTextField();
        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(btn);
        pane.add(tb);
        pane.add(dummy);

        Listener bl = new Listener();
        btn.getAccessibleContext().addPropertyChangeListener(bl);
        Listener tbl = new Listener();
        tb.getAccessibleContext().addPropertyChangeListener(tbl);
    }

    public void start() {
        btn.requestFocus();
        btn.transferFocus();
        tb.transferFocus();
    }

    public static void main(String[] argv) throws Exception {
        Robot robot = new Robot();
        bug4246045 bug = new bug4246045();
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame("4246045 Test");
                bug.init();
                frame.setSize(200, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                bug.start();
            });
            robot.waitForIdle();
            robot.delay(1000);
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}
