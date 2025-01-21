/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8191428
 * @summary  Verifies if text view is not borken into multiple lines
 * @key headful
 * @run main/othervm -Dsun.java2d.uiScale=1.2 TestGlyphBreak
 */

import java.awt.FontMetrics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestGlyphBreak {

    static JFrame f;
    static int btnHeight;
    static FontMetrics fm;

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeAndWait(() -> {

            String str = "<html><font size=2 color=red><bold>Three!</font></html>";
            JButton b = new JButton();
            b.setText(str);

            f = new JFrame();
            f.add(b);
            f.pack();
            f.setVisible(true);
            btnHeight = b.getHeight();
            fm = b.getFontMetrics(b.getFont());

        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
        }
        SwingUtilities.invokeAndWait(() -> f.dispose());
        System.out.println("metrics getHeight " + fm.getHeight() +
                             " button height " + btnHeight);

        // Check if text is broken into 2 lines, in which case button height
        // will be twice the string height
        if (btnHeight > 2*fm.getHeight()) {
            throw new RuntimeException("TextView is broken into different lines");
        }
    }
}
