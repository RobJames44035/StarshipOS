/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import javax.swing.JButton;
import javax.swing.JSpinner;

/*
 * @test
 * @bug 4862257
 * @summary Class cast Exception occurred when JButton is set to JSpinner
 * @run main bug4862257
 */

public class bug4862257 {
    public static void main(String[] argv) {
        JSpinner spinner = new JSpinner();
        spinner.setEditor(new JButton("JButton"));
        System.out.println("Test Passed!");
    }
}
