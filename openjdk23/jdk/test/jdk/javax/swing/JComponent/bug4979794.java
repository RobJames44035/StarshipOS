/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4979794
 * @summary A component is sometimes the next component for itself in focus policy.
 * @key headful
 */

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class bug4979794 {
    static JFrame fr;
    static JButton btn1;
    static JButton btn2;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> {
                fr = new JFrame("bug4979794");
                fr.getContentPane().setLayout(null);

                JPanel p = new JPanel();
                p.setLayout(null);
                fr.getContentPane().add(p);

                btn1 = new JButton("Button 1");
                btn1.setBounds(0, 0, 200, 200);

                btn2 = new JButton("Button 2");
                btn2.setBounds(0, 0, 200, 200);

                p.add(btn1);
                p.add(btn2);
                p.setSize(200, 200);

                fr.setLocationRelativeTo(null);
                fr.setSize(300, 300);
                fr.setVisible(true);
            });

            Robot r = new Robot();
            r.waitForIdle();
            r.delay(1000);

            SwingUtilities.invokeAndWait(() -> {
                Container root = btn1.getFocusCycleRootAncestor();
                FocusTraversalPolicy policy = root.getFocusTraversalPolicy();
                Component next1 = policy.getComponentAfter(fr, btn1);
                Component next2 = policy.getComponentAfter(fr, btn2);
                if (next1 == next2) {
                    throw new RuntimeException("btn1 and btn2 have the same next Component.");
                }
            });
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (fr != null) {
                    fr.dispose();
                }
            });
        }
    }
}
