/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4700351
 * @summary Checks if JToolBar keeps orientation when dragged off
 * @key headful
 * @run main bug4700351
 */

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicToolBarUI;

public class bug4700351 {
    static JFrame fr;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> {
                fr = new JFrame("bug4700351");
                JToolBar tb = new JToolBar();
                tb.setOrientation(JToolBar.VERTICAL);
                fr.add(tb);
                BasicToolBarUI ui = (javax.swing.plaf.basic.BasicToolBarUI) tb.getUI();
                if (!ui.isFloating()) {
                    ui.setFloatingLocation(100, 100);
                    ui.setFloating(true, tb.getLocation());
                }
                if (tb.getOrientation() != JToolBar.VERTICAL) {
                    throw new RuntimeException("Error: toolbar's orientation " +
                            "has changed");
                }
            });
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (fr != null) {
                    fr.dispose();
                }
            });
        }
    }
}
