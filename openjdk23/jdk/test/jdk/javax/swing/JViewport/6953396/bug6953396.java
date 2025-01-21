/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
 * @bug 6953396
 * @summary javax.swing.plaf.basic.BasicViewportUI.uninstallDefaults() is not called when UI is uninstalled
 * @author Alexander Potochkin
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicViewportUI;

public class bug6953396 {
    static volatile boolean flag;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                BasicViewportUI ui = new BasicViewportUI() {

                    @Override
                    protected void installDefaults(JComponent c) {
                        super.installDefaults(c);
                        flag = true;
                    }

                    @Override
                    protected void uninstallDefaults(JComponent c) {
                        super.uninstallDefaults(c);
                        flag = false;
                    }
                };

                JViewport viewport = new JViewport();
                viewport.setUI(ui);
                viewport.setUI(null);
            }
        });
        if (flag) {
            throw new RuntimeException("uninstallDefaults() hasn't been called");
        }
    }
}
