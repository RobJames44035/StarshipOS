/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4907772
 * @summary 1.4 REGRESSION: JPanel responds to mouse clicks on overlapping JPanel
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

public class bug4907772 {
    static JFrame fr;
    static JButton btn1;
    static JButton btn2;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> {
                fr = new JFrame("bug4907772");
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

                fr.setLocationRelativeTo(null);
                fr.pack();
                fr.setVisible(true);
            });

            Robot r = new Robot();
            r.waitForIdle();
            r.delay(1000);

            SwingUtilities.invokeAndWait(() -> {
                Container root = btn1.getFocusCycleRootAncestor();
                FocusTraversalPolicy policy = root.getFocusTraversalPolicy();
                Component initial = policy.getInitialComponent(fr);
                if (initial == btn2) {
                    throw new RuntimeException("The underlying button shouldn't be the initial component of FCR");
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
