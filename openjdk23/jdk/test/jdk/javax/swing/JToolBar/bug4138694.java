/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4138694
 * @summary When adding an Action object to a toolbar, the Action object's
 * SHORT_DESCRIPTION property (if present) should be automatically used
 * for toolTip text.
 * @run main bug4138694
 */

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class bug4138694 {
    public static final String actionName = "Action";

    private static class MyAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {}
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JToolBar jtb = new JToolBar();
            MyAction aa = new MyAction();
            aa.putValue(Action.SHORT_DESCRIPTION, actionName);
            jtb.add(aa);
            JComponent c = (JComponent)jtb.getComponentAtIndex(0);
            if (!c.getToolTipText().equals(actionName)) {
                throw new RuntimeException("ToolTip not set automatically...");
            }
        });
    }
}
