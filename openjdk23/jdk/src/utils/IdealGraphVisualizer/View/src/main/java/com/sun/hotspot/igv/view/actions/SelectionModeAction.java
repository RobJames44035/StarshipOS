/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.EditorTopComponent;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;

public class SelectionModeAction extends AbstractAction {

    public SelectionModeAction() {
        putValue(AbstractAction.SMALL_ICON, new ImageIcon(ImageUtilities.loadImage(iconResource())));
        putValue(Action.SHORT_DESCRIPTION, "Selection mode");
        putValue(SELECTED_KEY, false);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        EditorTopComponent editor = EditorTopComponent.getActive();
        if (editor != null) {
            boolean selected = (boolean)getValue(SELECTED_KEY);
            editor.setSelectionMode(selected);
        }
    }

    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/selection_mode.png";
    }
}
