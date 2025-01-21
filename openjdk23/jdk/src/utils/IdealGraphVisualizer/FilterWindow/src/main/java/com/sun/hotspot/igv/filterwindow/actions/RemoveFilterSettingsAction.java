/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.filterwindow.actions;

import com.sun.hotspot.igv.filterwindow.FilterTopComponent;
import javax.swing.Action;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

/**
 *
 * @author Thomas Wuerthinger
 */
public final class RemoveFilterSettingsAction extends CallableSystemAction {

    @Override
    public void performAction() {
        FilterTopComponent.findInstance().removeFilterSetting();
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(RemoveFilterSettingsAction.class, "CTL_RemoveFilterSettingsAction");
    }

    public RemoveFilterSettingsAction() {
        putValue(Action.SHORT_DESCRIPTION, "Delete current filter profile");
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/filterwindow/images/delete.png";
    }
}
