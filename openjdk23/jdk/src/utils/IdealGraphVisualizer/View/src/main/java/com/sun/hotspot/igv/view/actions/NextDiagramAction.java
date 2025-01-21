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
@ActionID(category = "View", id = "com.sun.hotspot.igv.view.actions.NextDiagramAction")
@ActionRegistration(displayName = "#CTL_NextDiagramAction")
@ActionReferences({
        @ActionReference(path = "Menu/View", position = 150),
        @ActionReference(path = "Shortcuts", name = "D-RIGHT")
})
@Messages({
        "CTL_NextDiagramAction=Show next graph",
        "HINT_NextDiagramAction=Show next graph of current group"
})
public final class NextDiagramAction extends ModelAwareAction {

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/next_diagram.png"; // NOI18N
    }

    @Override
    protected String getDescription() {
        return NbBundle.getMessage(NextDiagramAction.class, "HINT_NextDiagramAction");
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(NextDiagramAction.class, "CTL_NextDiagramAction");
    }

    @Override
    public void performAction(DiagramViewModel model) {
        if (model.getSecondPosition() != model.getPositions().size() - 1) {
            model.setPositions(model.getFirstPosition() + 1, model.getSecondPosition() + 1);
        }
    }

    @Override
    public boolean isEnabled(DiagramViewModel model) {
        return model.getSecondPosition() != model.getPositions().size() - 1;
    }
}
