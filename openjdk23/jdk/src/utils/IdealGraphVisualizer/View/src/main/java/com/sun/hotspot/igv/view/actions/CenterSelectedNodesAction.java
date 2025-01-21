/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.DiagramViewModel;
import com.sun.hotspot.igv.view.EditorTopComponent;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

@ActionID(category = "View", id = "com.sun.hotspot.igv.view.actions.CenterSelectedNodesAction")
@ActionRegistration(displayName = "#CTL_CenterSelectedNodesAction")
@ActionReferences({
        @ActionReference(path = "Menu/View", position = 600),
        @ActionReference(path = "Shortcuts", name = "D-9")
})
@NbBundle.Messages({
        "CTL_CenterSelectedNodesAction=Center selected nodes",
        "HINT_CenterSelectedNodesAction=Center the selected nodes"
})
public final class CenterSelectedNodesAction extends ModelAwareAction {

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/centerSelectedNodes.svg"; // NOI18N
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(NextDiagramAction.class, "CTL_CenterSelectedNodesAction");
    }

    @Override
    protected String getDescription() {
        return NbBundle.getMessage(NextDiagramAction.class, "HINT_CenterSelectedNodesAction");
    }

    @Override
    public void performAction(DiagramViewModel model) {
        EditorTopComponent editor = EditorTopComponent.findEditorForGraph(model.getGraph());
        if (editor != null) {
            editor.centerSelectedNodes();
        }
    }

    @Override
    public boolean isEnabled(DiagramViewModel model) {
        return !model.getSelectedNodes().isEmpty();
    }
}
