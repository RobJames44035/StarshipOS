/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.DiagramViewModel;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;


@ActionID(category = "View", id = "com.sun.hotspot.igv.view.actions.ExpandDiffAction")
@ActionRegistration(displayName = "#CTL_ExpandDiffAction")
@ActionReferences({
        @ActionReference(path = "Menu/View", position = 250),
        @ActionReference(path = "Shortcuts", name = "DS-RIGHT"),
        @ActionReference(path = "Shortcuts", name = "D-UP")
})
@Messages({
        "CTL_ExpandDiffAction=Expand difference selection",
        "HINT_ExpandDiffAction=Expand the difference selection"
})
public final class ExpandDiffAction extends ModelAwareAction {

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/expand_right.png"; // NOI18N
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ExpandDiffAction.class, "CTL_ExpandDiffAction");
    }

    @Override
    protected String getDescription() {
        return NbBundle.getMessage(ExpandDiffAction.class, "HINT_ExpandDiffAction");
    }

    @Override
    public void performAction(DiagramViewModel model) {
        if (model.getSecondPosition() != model.getPositions().size() - 1) {
            model.setPositions(model.getFirstPosition(), model.getSecondPosition() + 1);
        }
    }

    @Override
    public boolean isEnabled(DiagramViewModel model) {
        return model.getSecondPosition() != model.getPositions().size() - 1;
    }
}
