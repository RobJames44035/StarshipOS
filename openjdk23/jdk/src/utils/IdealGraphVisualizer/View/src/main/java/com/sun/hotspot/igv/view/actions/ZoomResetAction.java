/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.EditorTopComponent;
import javax.swing.Action;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

@ActionID(category = "View", id = "com.sun.hotspot.igv.view.actions.ZoomResetAction")
@ActionRegistration(displayName = "#CTL_ZoomResetAction")
@ActionReferences({
        @ActionReference(path = "Menu/View", position = 550),
        @ActionReference(path = "Shortcuts", name = "D-0")
})
@NbBundle.Messages({
        "CTL_ZoomResetAction=Reset zoom (100%)",
        "HINT_ZoomResetAction=Reset the zoom level to 100%"
})
public final class ZoomResetAction extends CallableSystemAction {

    public ZoomResetAction() {
        putValue(Action.SHORT_DESCRIPTION, getDescription());
        putValue(Action.SMALL_ICON , ImageUtilities.loadImageIcon(iconResource(), true));
    }

    @Override
    public void performAction() {
        EditorTopComponent editor = EditorTopComponent.getActive();
        if (editor != null) {
            editor.setZoomLevel(100);
        }
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ZoomResetAction.class, "CTL_ZoomResetAction");
    }

    private String getDescription() {
        return NbBundle.getMessage(ZoomResetAction.class, "HINT_ZoomResetAction");
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
        return "com/sun/hotspot/igv/view/images/zoomReset.svg"; // NOI18N
    }
}
