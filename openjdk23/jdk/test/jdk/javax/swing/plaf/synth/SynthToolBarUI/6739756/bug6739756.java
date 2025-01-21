/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test
 * @bug 6739756
 * @author Alexander Potochkin
 * @summary JToolBar leaves space for non-visible items under Nimbus L&F
 * @run main bug6739756
 */

import javax.swing.*;
import java.awt.*;

public class bug6739756 {

    public static void main(String[] args) throws Exception {
        try {
           UIManager.setLookAndFeel(
                   "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                JToolBar tb = new JToolBar();
                Dimension preferredSize = tb.getPreferredSize();
                JButton button = new JButton("Test");
                button.setVisible(false);
                tb.add(button);
                if (!preferredSize.equals(tb.getPreferredSize())) {
                    throw new RuntimeException("Toolbar's preferredSize is wrong");
                }
            }
        });
    }
}
