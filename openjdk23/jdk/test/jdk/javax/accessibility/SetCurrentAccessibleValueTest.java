/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4422535
 * @summary setCurrentAccessibleValue returns true only for an Integer
 * @run main SetCurrentAccessibleValueTest
 */

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class SetCurrentAccessibleValueTest {

    public static void doTest() {
        JComponent[] jComponents =
        { new JButton(), new JInternalFrame(), new JSplitPane(),
            new JScrollBar(), new JProgressBar(), new JSlider() };

        for (JComponent jComponent : jComponents) {
            testIt(jComponent, (Float.valueOf(5)));
            testIt(jComponent, (Double.valueOf(37.266)));
            testIt(jComponent, (Integer.valueOf(10)));
            testIt(jComponent, (Long.valueOf(123L)));
            testIt(jComponent, (Short.valueOf((short) 123)));
            testIt(jComponent, (BigInteger.ONE));
            testIt(jComponent, (new BigDecimal(BigInteger.ONE)));
        }

    }

    static void testIt(JComponent jComponent, Number number) {
        if (!jComponent.getAccessibleContext().getAccessibleValue()
            .setCurrentAccessibleValue(number)) {
            throw new RuntimeException(jComponent.getClass().getName()
                + " Accessible Value implementation doesn't accept "
                + number.getClass().getName());
        }
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> doTest());
        System.out.println("Test Passed");
    }
}

