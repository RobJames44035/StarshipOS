/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filterwindow.actions;

import com.sun.hotspot.igv.filter.CustomFilter;
import com.sun.hotspot.igv.filter.Filter;
import com.sun.hotspot.igv.filterwindow.FilterTopComponent;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.windows.WindowManager;

/**
 *
 * @author Thomas Wuerthinger
 */
public final class RemoveFilterAction extends CookieAction {

    @Override
    protected void performAction(Node[] activatedNodes) {
        Object[] options = {"Yes",
            "No",
            "Cancel"
        };
        int n = JOptionPane.showOptionDialog(WindowManager.getDefault().getMainWindow(),
                "Do you really want to delete " + activatedNodes.length + " filter(s)?", "Delete Filters",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        if (n == JOptionPane.YES_OPTION) {
            for (Node activatedNode : activatedNodes) {
                FilterTopComponent.findInstance().removeFilter(activatedNode.getLookup().lookup(CustomFilter.class));
            }
        }
    }

    @Override
    protected int mode() {
        return CookieAction.MODE_ALL;
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(RemoveFilterAction.class, "CTL_RemoveFilterAction");
    }

    public RemoveFilterAction() {
        putValue(Action.SHORT_DESCRIPTION, "Remove selected filter");
    }

    @Override
    protected Class[] cookieClasses() {
        return new Class[]{
            Filter.class
        };
    }

    @Override
    protected void initialize() {
        super.initialize();
        putValue("noIconInMenu", Boolean.TRUE);
    }

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/filterwindow/images/minus.png";
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
