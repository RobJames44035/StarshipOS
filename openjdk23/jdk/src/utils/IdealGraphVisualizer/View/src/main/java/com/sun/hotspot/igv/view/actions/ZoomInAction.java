/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
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
import org.openide.util.NbBundle.Messages;
import org.openide.util.actions.CallableSystemAction;

/**
 * @author Thomas Wuerthinger
 */
@ActionID(category = "View", id = "com.sun.hotspot.igv.view.actions.ZoomInAction")
@ActionRegistration(displayName = "#CTL_ZoomInAction")
@ActionReferences({
        @ActionReference(path = "Menu/View", position = 450),
        @ActionReference(path = "Shortcuts", name = "D-EQUALS"),
        @ActionReference(path = "Shortcuts", name = "DS-MINUS")
})
@Messages({
        "CTL_ZoomInAction=Zoom in",
        "HINT_ZoomInAction=Zoom into the graph"
})
public final class ZoomInAction extends CallableSystemAction {

    public ZoomInAction() {
        putValue(Action.SHORT_DESCRIPTION, getDescription());
        putValue(Action.SMALL_ICON , ImageUtilities.loadImageIcon(iconResource(), true));
    }

    @Override
    public void performAction() {
        EditorTopComponent editor = EditorTopComponent.getActive();
        if (editor != null) {
            editor.zoomIn();
        }
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ZoomInAction.class, "CTL_ZoomInAction");
    }

    private String getDescription() {
        return NbBundle.getMessage(ZoomInAction.class, "HINT_ZoomInAction");
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
        return "com/sun/hotspot/igv/view/images/zoomIn.svg"; // NOI18N
    }
}
