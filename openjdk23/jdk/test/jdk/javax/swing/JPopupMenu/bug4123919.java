/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4123919
 * @requires (os.family == "windows")
 * @summary JPopupMenu.isPopupTrigger() under a different L&F.
 * @key headful
 * @run main bug4123919
 */

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.util.Date;

public class bug4123919 {

    public static void main(String[] args) throws Exception {
        JPopupMenu popup = new JPopupMenu("Test");
        JLabel lb = new JLabel();
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        SwingUtilities.updateComponentTreeUI(lb);
        SwingUtilities.updateComponentTreeUI(popup);
        if (!popup.isPopupTrigger(new MouseEvent(lb, MouseEvent.MOUSE_PRESSED,
                (new Date()).getTime(), MouseEvent.BUTTON3_MASK, 10, 10, 1, true))) {
            throw new RuntimeException("JPopupMenu.isPopupTrigger() fails on" +
                    " MotifLookAndFeel when mouse pressed...");
        }
        if (popup.isPopupTrigger(new MouseEvent(lb, MouseEvent.MOUSE_RELEASED,
                (new Date()).getTime(), MouseEvent.BUTTON3_MASK, 10, 10, 1, true))) {
            throw new RuntimeException("JPopupMenu.isPopupTrigger() fails on" +
                    " MotifLookAndFeel when mouse released...");
        }

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(lb);
        SwingUtilities.updateComponentTreeUI(popup);

        if (popup.isPopupTrigger(new MouseEvent(lb, MouseEvent.MOUSE_PRESSED,
                (new Date()).getTime(), MouseEvent.BUTTON3_MASK, 10, 10, 1, true))) {
            throw new RuntimeException("JPopupMenu.isPopupTrigger() fails on" +
                    " WindowsLookAndFeel when mouse pressed...");
        }
        if (!popup.isPopupTrigger(new MouseEvent(lb, MouseEvent.MOUSE_RELEASED,
                (new Date()).getTime(), MouseEvent.BUTTON3_MASK, 10, 10, 1, true))) {
            throw new RuntimeException("JPopupMenu.isPopupTrigger() fails on" +
                    " WindowsLookAndFeel when mouse released...");
        }
    }
}
