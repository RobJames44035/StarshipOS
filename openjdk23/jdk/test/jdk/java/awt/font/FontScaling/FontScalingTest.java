/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

/*
 * @test
 * @bug 8076545
 * @summary Text size is twice bigger under Windows L&F on Win 8.1 with
 *          HiDPI display
 */
public class FontScalingTest {

    public static void main(String[] args) throws Exception {
        int metalFontSize = getFontSize(MetalLookAndFeel.class.getName());
        int systemFontSize = getFontSize(UIManager.getSystemLookAndFeelClassName());

        if (Math.abs(systemFontSize - metalFontSize) > 8) {
            throw new RuntimeException("System L&F is too big!");
        }
    }

    private static int getFontSize(String laf) throws Exception {

        UIManager.setLookAndFeel(laf);
        final int[] sizes = new int[1];

        SwingUtilities.invokeAndWait(() -> {
            JButton button = new JButton("Test");
            sizes[0] = button.getFont().getSize();
        });

        return sizes[0];
    }
}