/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * @test
 * @key headful
 * @bug 8034955
 * @author Alexander Scherbatiy
 * @summary JLabel/JToolTip throw ClassCastException for "<html>a<title>"
 * @run main bug8034955
 */
public class bug8034955 {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.getContentPane().add(new JLabel("<html>a<title>"));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
