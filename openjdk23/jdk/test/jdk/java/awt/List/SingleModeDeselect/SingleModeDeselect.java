/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6248040
  @summary List.deselect() de-selects the currently selected item regardless of the index, win32
  @run main SingleModeDeselect
*/

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.List;
import java.awt.Robot;

public class SingleModeDeselect {
    public static final void main(String args[]) {
        final Frame frame = new Frame();
        final List list = new List();

        list.add(" item 0 ");
        list.add(" item 1 ");

        frame.add(list);
        frame.setLayout(new FlowLayout());
        frame.setBounds(100,100,300,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        list.select(0);
        list.deselect(1);

        try {
            Robot robot = new Robot();
            robot.waitForIdle();
            if (list.getSelectedIndex() != 0) {
                throw new RuntimeException("Test failed: List.getSelectedIndex() returns "
                                           + list.getSelectedIndex());
            }
        } catch(AWTException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unexpected failure");
        } finally {
            frame.setVisible(false);
            frame.dispose();
        }
    }
}
