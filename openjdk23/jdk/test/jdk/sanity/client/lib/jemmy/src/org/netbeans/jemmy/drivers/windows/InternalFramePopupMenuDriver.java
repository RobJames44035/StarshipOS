/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package org.netbeans.jemmy.drivers.windows;

import javax.swing.UIManager;

import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JInternalFrameOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;

/**
 * InternalFrameDriver to do Close, Minimize, Maximize, and Restore actions
 * using popup menus.
 */
public class InternalFramePopupMenuDriver extends DefaultInternalFrameDriver {

    @Override
    public void requestClose(ComponentOperator oper) {
        checkSupported(oper);
        pushMenuItem(oper, UIManager.getString(
                "InternalFrameTitlePane.closeButtonText"));
    }

    @Override
    public void iconify(ComponentOperator oper) {
        checkSupported(oper);
        pushMenuItem(oper, UIManager.getString(
                "InternalFrameTitlePane.minimizeButtonText"));
    }

    @Override
    public void maximize(ComponentOperator oper) {
        checkSupported(oper);
        if (!((JInternalFrameOperator) oper).isMaximum()) {
            if (!((JInternalFrameOperator) oper).isSelected()) {
                activate(oper);
            }
            pushMenuItem(oper, UIManager.getString(
                    "InternalFrameTitlePane.maximizeButtonText"));
        }
    }

    @Override
    public void demaximize(ComponentOperator oper) {
        checkSupported(oper);
        if (((JInternalFrameOperator) oper).isMaximum()) {
            if (!((JInternalFrameOperator) oper).isSelected()) {
                activate(oper);
            }
            pushMenuItem(oper, UIManager.getString(
                    "InternalFrameTitlePane.restoreButtonText"));
        }
    }

    private void pushMenuItem(ComponentOperator oper,
            String menuText) {
        ((JInternalFrameOperator) oper).getPopupButton().push();
        JPopupMenuOperator popupMenu = new JPopupMenuOperator();
        JMenuItemOperator menuItem =
                new JMenuItemOperator(popupMenu, menuText);
        menuItem.push();
    }
}