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


@ActionID(category = "View", id = "com.sun.hotspot.igv.view.actions.ReduceDiffAction")
@ActionRegistration(displayName = "#CTL_ReduceDiffAction")
@ActionReferences({
        @ActionReference(path = "Menu/View", position = 200),
        @ActionReference(path = "Shortcuts", name = "DS-LEFT"),
        @ActionReference(path = "Shortcuts", name = "D-DOWN")
})
@Messages({
        "CTL_ReduceDiffAction=Reduce difference selection",
        "HINT_ReduceDiffAction=Reduce the difference selection"
})
public final class ReduceDiffAction extends ModelAwareAction {

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/shrink_right.png"; // NOI18N
    }

    @Override
    protected String getDescription() {
        return NbBundle.getMessage(ReduceDiffAction.class, "HINT_ReduceDiffAction");
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ReduceDiffAction.class, "CTL_ReduceDiffAction");
    }

    @Override
    public void performAction(DiagramViewModel model) {
        int firstPos = model.getFirstPosition();
        int secondPos = model.getSecondPosition();
        if (firstPos < secondPos) {
            model.setPositions(firstPos, secondPos - 1);
        }
    }

    @Override
    public boolean isEnabled(DiagramViewModel model) {
        return model.getFirstPosition() != model.getSecondPosition();
    }
}
