/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6984643
 * @summary Unable to instantiate JFileChooser with a minimal BasicL&F descendant installed
 * @author Pavel Porvatov
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

public class Test6984643 {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new BasicLookAndFeel() {
            public String getName() {
                return "A name";
            }

            public String getID() {
                return "An id";
            }

            public String getDescription() {
                return "A description";
            }

            public boolean isNativeLookAndFeel() {
                return false;
            }

            public boolean isSupportedLookAndFeel() {
                return true;
            }
        });

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                new JFileChooser();
            }
        });
    }
}
