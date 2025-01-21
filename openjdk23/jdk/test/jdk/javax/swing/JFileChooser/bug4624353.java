/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4624353
 * @summary Tests that Motif FileChooser is not able to show control buttons
 * @key headful
 * @run main bug4624353
 */

import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class bug4624353 {
    static volatile boolean passed = true;
    static JFrame fr;
    static JFileChooser fc;

    public static void main(String args[]) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

        try {
            SwingUtilities.invokeAndWait(() -> {
                fr = new JFrame("bug4624353");
                fc = new JFileChooser();
                fc.setControlButtonsAreShown(false);
                fr.getContentPane().add(fc);
                fr.pack();
                fr.setVisible(true);

                passAround(fc);
            });
            if (!passed) {
                throw new RuntimeException("Test failed");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (fr != null) {
                    fr.dispose();
                }
            });
        }
    }

    public static void passAround(Container c) {
        Component[] list = c.getComponents();
        if (list.length == 0) {
            return;
        }
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                if ((list[i] instanceof JButton) &&
                        "OK".equals(((JButton)list[i]).getText())) {
                    passed = false;
                    return;
                }
                passAround((Container)list[i]);
            }
        }
    }
}
