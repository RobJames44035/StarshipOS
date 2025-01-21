/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;

/*
 * @test
 * @summary Check that JPasswordField constructor and methods do not throw unexpected
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessJPasswordField
 */

public class HeadlessJPasswordField {
    public static void main(String args[]) {
        JPasswordField f = new JPasswordField("field");
        f.selectAll();
        f.getSelectionStart();
        f.getSelectionEnd();
    }
}
