/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.util.Dictionary;
import java.util.Hashtable;

/*
 * @test
 * @bug 4203754
 * @key headful
 * @summary Labels in a JSlider don't disable or enable with the slider
 * @run main bug4203754
 */

public class bug4203754 {
    private static JFrame frame;
    private static Robot robot;
    private static JLabel label;

    public static void main(String[] argv) throws Exception {
        try {
            robot = new Robot();
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame("Test");
                frame.getContentPane().setLayout(new FlowLayout());
                JSlider slider = new JSlider(0, 100, 25);
                frame.getContentPane().add(slider);

                label = new JLabel("0", JLabel.CENTER) {
                    public void setEnabled(boolean b) {
                        super.setEnabled(b);
                    }
                };

                Dictionary labels = new Hashtable();
                labels.put(Integer.valueOf(0), label);
                slider.setLabelTable(labels);
                slider.setPaintLabels(true);
                slider.setEnabled(false);
                frame.setSize(250, 150);
                frame.setVisible(true);
            });
            robot.waitForIdle();
            robot.delay(1000);
            if (label.isEnabled()) {
                throw new RuntimeException("Label should be disabled");
            }
            System.out.println("Test Passed!");
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}
