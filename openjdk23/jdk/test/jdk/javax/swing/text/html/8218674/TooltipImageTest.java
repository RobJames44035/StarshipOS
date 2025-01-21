/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 8218674
 * @summary Tests if Images are rendered and scaled correctly in JToolTip.
 * @run main TooltipImageTest
 */
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;

public class TooltipImageTest {

    private static void checkSize(JToolTip tip, int width, int height) {
        Dimension d = tip.getPreferredSize();
        Insets insets = tip.getInsets();
        //6 seems to be the extra width being allocated for some reason
        //for a tooltip window.
        if (!((d.width - insets.right - insets.left - 6) == width) &&
            !((d.height - insets.top - insets.bottom) == height)) {
            throw new RuntimeException("Test case fails: Expected width, height is " + width + ", " + height +
                    " whereas actual width, height are " + (d.width - insets.right - insets.left - 6) + " " +
                    (d.height - insets.top - insets.bottom));
        }
     }

    public static void main(String[] args) throws Exception {
        String PATH = TooltipImageTest.class.getResource("circle.png").getPath();
        SwingUtilities.invokeAndWait(() -> {
            JToolTip tip = new JToolTip();
            tip.setTipText("<html><img width=\"100\" src=\"file:///" + PATH + "\"></html>");
            checkSize(tip, 100, 100);

            tip.setTipText("<html><img height=\"100\" src=\"file:///" + PATH + "\"></html>");
            checkSize(tip, 100, 100);

            tip.setTipText("<html><img src=\"file:///" + PATH + "\"></html>");
            checkSize(tip, 200, 200);

            tip.setTipText("<html><img width=\"50\" src=\"file:///" + PATH + "\"></html>");
            checkSize(tip, 50, 50);

            tip.setTipText("<html><img height=\"50\" src=\"file:///" + PATH + "\"></html>");
            checkSize(tip, 50, 50);

            tip.setTipText("<html><img width=\"100\" height=\"50\" src=\"file:///" + PATH + "\"></html>");
            checkSize(tip, 100, 50);
        });

        System.out.println("Test case passed.");
    }
}
