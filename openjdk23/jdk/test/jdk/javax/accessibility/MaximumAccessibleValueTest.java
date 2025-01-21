/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4422362
 * @summary Wrong Max Accessible Value with BoundedRangeModel components
 * @run main MaximumAccessibleValueTest
 */

import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;

public class MaximumAccessibleValueTest {

    public static void doTest() {

        JScrollBar jScrollBar = new JScrollBar();
        JProgressBar jProgressBar = new JProgressBar();
        JSlider jSlider = new JSlider();

        if (((Integer) jScrollBar.getAccessibleContext().getAccessibleValue()
            .getMaximumAccessibleValue()).intValue() != jScrollBar.getMaximum()
            - jScrollBar.getVisibleAmount()) {
            throw new RuntimeException(
                "Wrong MaximumAccessibleValue returned by JScrollBar");
        }

        if (((Integer) jProgressBar.getAccessibleContext().getAccessibleValue()
            .getMaximumAccessibleValue().intValue()) != (jProgressBar
                .getMaximum() - jProgressBar.getModel().getExtent())) {
            throw new RuntimeException(
                "Wrong MaximumAccessibleValue returned by JProgressBar");
        }

        if (((Integer) jSlider.getAccessibleContext().getAccessibleValue()
            .getMaximumAccessibleValue()).intValue() != jSlider.getMaximum()
            - jSlider.getModel().getExtent()) {
            throw new RuntimeException(
                "Wrong MaximumAccessibleValue returned by JSlider");
        }
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> doTest());
        System.out.println("Test Passed");
    }
}

