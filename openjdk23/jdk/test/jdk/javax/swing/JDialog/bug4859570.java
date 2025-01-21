/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4859570
 * @summary SwingUtilities.sharedOwnerFrame is never disposed
 * @key headful
 */

import java.awt.Robot;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class bug4859570 {
    static Window owner;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("bug4859570");
            dialog.setBounds(100, 100, 100, 100);
            dialog.setVisible(true);

            owner = dialog.getOwner();
            dialog.dispose();
        });

        Robot r = new Robot();
        r.waitForIdle();
        r.delay(1000);

        SwingUtilities.invokeAndWait(() -> {
            if (owner.isDisplayable()) {
                throw new RuntimeException("The shared owner frame should be disposed.");
            }
        });
    }
}
