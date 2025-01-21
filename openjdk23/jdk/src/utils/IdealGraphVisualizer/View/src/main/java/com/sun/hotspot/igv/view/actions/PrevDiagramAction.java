/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.DiagramViewModel;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;


/**
 * @author Thomas Wuerthinger
 */
@ActionID(category = "View", id = "com.sun.hotspot.igv.view.actions.PrevDiagramAction")
@ActionRegistration(displayName = "#CTL_PrevDiagramAction")
@ActionReferences({
        @ActionReference(path = "Menu/View", position = 100),
        @ActionReference(path = "Shortcuts", name = "D-LEFT")
})
@Messages({
        "CTL_PrevDiagramAction=Show previous graph",
        "HINT_PrevDiagramAction=Show previous graph of current group"
})
public final class PrevDiagramAction extends ModelAwareAction {

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/prev_diagram.png"; // NOI18N
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(PrevDiagramAction.class, "CTL_PrevDiagramAction");
    }

    @Override
    protected String getDescription() {
        return NbBundle.getMessage(PrevDiagramAction.class, "HINT_PrevDiagramAction");
    }

    @Override
    public void performAction(DiagramViewModel model) {
        if (model.getFirstPosition() != 0) {
            model.setPositions(model.getFirstPosition() - 1, model.getSecondPosition() - 1);
        }
    }

    @Override
    public boolean isEnabled(DiagramViewModel model) {
        return model.getFirstPosition() != 0;
    }
}
