/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package com.sun.hotspot.igv.coordinator.actions;

import com.sun.hotspot.igv.coordinator.OutlineTopComponent;
import javax.swing.Action;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.CallableSystemAction;

public final class OpenAction extends CallableSystemAction {

    public OpenAction() {
        putValue(Action.SHORT_DESCRIPTION, "Open...");
        // D is the Control key on most platforms, the Command (meta) key on Macintosh
        putValue(Action.ACCELERATOR_KEY, Utilities.stringToKey("D-O"));
        putValue(Action.SMALL_ICON, ImageUtilities.loadImageIcon(iconResource(), true));
    }

    @Override
    public void performAction() {
        OutlineTopComponent.findInstance().openFile();
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(OpenAction.class, "CTL_OpenAction");
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
        return "com/sun/hotspot/igv/coordinator/images/open.png";
    }
}
