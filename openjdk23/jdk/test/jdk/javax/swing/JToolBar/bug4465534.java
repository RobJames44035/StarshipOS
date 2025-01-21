/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4465534
 * @summary Tests adding borderless button to a toolbar
 * @run main bug4465534
 */

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class bug4465534 {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JToolBar toolbar = new JToolBar();
            JButton button = new JButton("text");
            button.setBorder(null);
            toolbar.add(button);
        });
    }
}
