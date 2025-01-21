/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.EditorTopComponent;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Thomas Wuerthinger
 */
public class PredSuccAction extends AbstractAction {


    public PredSuccAction(boolean selected) {
        putValue(Action.SELECTED_KEY, selected);
        putValue(AbstractAction.SMALL_ICON, new ImageIcon(ImageUtilities.loadImage(iconResource())));
        putValue(Action.SHORT_DESCRIPTION, "Show neighboring nodes of fully visible nodes semi-transparent");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        EditorTopComponent editor = EditorTopComponent.getActive();
        if (editor != null) {
            boolean selected = (boolean)getValue(SELECTED_KEY);
            editor.getModel().setShowNodeHull(selected);
        }
    }

    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/predsucc.gif";
    }
}
