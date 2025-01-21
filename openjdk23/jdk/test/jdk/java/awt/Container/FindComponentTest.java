/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
  @test
  @bug 4196100
  @summary Make sure findComponentAt() only returns visible components.
  @key headful
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class FindComponentTest {

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            FindComponentFrame findComponentAtTest = new FindComponentFrame();

            try {
                if (!findComponentAtTest.didItWork()) {
                    throw new RuntimeException(
                            "findComponentAt() returned non-visible component");
                }
            } finally {
                findComponentAtTest.dispose();
            }
        });
    }
}


class FindComponentFrame extends JFrame {
        public FindComponentFrame() {
            super("FindComponentFrame");
        }

        public boolean didItWork() {
            setTitle("FindComponentTest");
            setSize(new Dimension(200, 200));

            JTabbedPane tabbedpane = new JTabbedPane();
            setContentPane(tabbedpane);

            JPanel panel1 = new JPanel();
            panel1.setName("Panel 1");
            panel1.setLayout(new BorderLayout());
            tabbedpane.add(panel1);
            JPanel subPanel = new JPanel();
            subPanel.setName("Sub-Panel");
            subPanel.setBackground(Color.green);
            panel1.add(subPanel); // add sub panel to 1st tab

            JPanel panel2 = new JPanel();
            panel2.setName("Panel 2");
            tabbedpane.add(panel2);

            tabbedpane.setSelectedIndex(1); // display 2nd tab
            setVisible(true);

            boolean success = tabbedpane.findComponentAt(50,50)
                                        .getName().equals("Panel 2");
            return success;
        }
}
