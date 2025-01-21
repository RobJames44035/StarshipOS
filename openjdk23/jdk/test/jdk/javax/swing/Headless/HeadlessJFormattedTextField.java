/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;

/*
 * @test
 * @summary Check that JFormattedTextField constructor and methods do not throw unexpected
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessJFormattedTextField
 */

public class HeadlessJFormattedTextField {
    public static void main(String args[]) {
        JTextField f = new JTextField("field");
        f.selectAll();
        f.getSelectionStart();
        f.getSelectionEnd();
        f.selectAll();
    }
}
