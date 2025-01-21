/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4140421
 * @summary Tests JToolBar set title correctly
 * @run main bug4140421
 */

import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class bug4140421 {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JToolBar tb = new JToolBar("MyToolBar");
            if (!tb.getName().equals("MyToolBar")) {
                throw new RuntimeException("Title of JToolBar set incorrectly...");
            }
        });
    }
}
