/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.ComponentOrientation;

/*
 * @test
 * @bug 4467063
 * @summary JScrollPane.setCorner() causes IllegalArgumentException. (invalid corner key)
 * @run main bug4467063
 */

public class bug4467063 {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JScrollPane sp = new JScrollPane();

            //Test corners for left-to-right orientation
            sp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            sp.setCorner(JScrollPane.LOWER_LEADING_CORNER, new JButton("0"));
            sp.setCorner(JScrollPane.LOWER_TRAILING_CORNER, new JButton("1"));
            sp.setCorner(JScrollPane.UPPER_LEADING_CORNER, new JButton("2"));
            sp.setCorner(JScrollPane.UPPER_TRAILING_CORNER, new JButton("3"));

            if (!sp.getCorner(JScrollPane.LOWER_LEADING_CORNER).equals(
                    sp.getCorner(JScrollPane.LOWER_LEFT_CORNER))) {
                throw new RuntimeException("Incorrect LOWER_LEADING_CORNER value");
            }

            if (!sp.getCorner(JScrollPane.LOWER_TRAILING_CORNER).equals(
                    sp.getCorner(JScrollPane.LOWER_RIGHT_CORNER))) {
                throw new RuntimeException("Incorrect LOWER_TRAILING_CORNER value");
            }

            if (!sp.getCorner(JScrollPane.UPPER_LEADING_CORNER).equals(
                    sp.getCorner(JScrollPane.UPPER_LEFT_CORNER))) {
                throw new RuntimeException("Incorrect UPPER_LEADING_CORNER value");
            }

            if (!sp.getCorner(JScrollPane.UPPER_TRAILING_CORNER).equals(
                    sp.getCorner(JScrollPane.UPPER_RIGHT_CORNER))) {
                throw new RuntimeException("Incorrect UPPER_TRAILING_CORNER value");
            }

            //Test corners for right-to-left orientation
            sp.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            sp.setCorner(JScrollPane.LOWER_LEADING_CORNER, new JButton("0"));
            sp.setCorner(JScrollPane.LOWER_TRAILING_CORNER, new JButton("1"));
            sp.setCorner(JScrollPane.UPPER_LEADING_CORNER, new JButton("2"));
            sp.setCorner(JScrollPane.UPPER_TRAILING_CORNER, new JButton("3"));

            if (!sp.getCorner(JScrollPane.LOWER_LEADING_CORNER).equals(
                    sp.getCorner(JScrollPane.LOWER_RIGHT_CORNER))) {
                throw new RuntimeException("Incorrect LOWER_LEADING_CORNER value");
            }

            if (!sp.getCorner(JScrollPane.LOWER_TRAILING_CORNER).equals(
                    sp.getCorner(JScrollPane.LOWER_LEFT_CORNER))) {
                throw new RuntimeException("Incorrect LOWER_TRAILING_CORNER value");
            }

            if (!sp.getCorner(JScrollPane.UPPER_LEADING_CORNER).equals(
                    sp.getCorner(JScrollPane.UPPER_RIGHT_CORNER))) {
                throw new RuntimeException("Incorrect UPPER_LEADING_CORNER value");
            }

            if (!sp.getCorner(JScrollPane.UPPER_TRAILING_CORNER).equals(
                    sp.getCorner(JScrollPane.UPPER_LEFT_CORNER))) {
                throw new RuntimeException("Incorrect UPPER_TRAILING_CORNER value");
            }
        });
        System.out.println("Test Passed!");
    }
}
