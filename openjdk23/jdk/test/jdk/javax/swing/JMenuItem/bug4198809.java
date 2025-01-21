/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4198809
   @key headful
   @summary If JMenuItem is disabled and disabled icon is null, throws NPE.
   @run main bug4198809
*/

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class bug4198809 {
    static JFrame frame;
    public static void main(String args[]) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

        SwingUtilities.invokeAndWait(() -> {
            try {
                frame = new JFrame("bug4198809");
                JMenuItem mi = new JMenuItem("test");
                mi.setDisabledIcon(null);
                mi.setEnabled(false);
                frame.getContentPane().add(mi);
            } finally {
                if (frame != null) {
                    frame.dispose();
                }
            }
        });
    }
}
