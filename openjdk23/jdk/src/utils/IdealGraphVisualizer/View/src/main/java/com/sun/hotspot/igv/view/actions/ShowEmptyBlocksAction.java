/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.EditorTopComponent;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;

public class ShowEmptyBlocksAction extends AbstractAction implements PropertyChangeListener  {

    private boolean selected;
    private AbstractAction parentAction;

    public ShowEmptyBlocksAction(AbstractAction action, boolean select) {
        this.parentAction = action;
        this.selected = select;
        this.parentAction.addPropertyChangeListener(this);
        putValue(SELECTED_KEY, this.selected);
        putValue(SMALL_ICON, new ImageIcon(ImageUtilities.loadImage(iconResource())));
        putValue(SHORT_DESCRIPTION, "Show empty blocks in control-flow graph view");
        enableIfParentSelected();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        this.selected = isSelected();
        EditorTopComponent editor = EditorTopComponent.getActive();
        if (editor != null) {
            editor.getModel().setShowEmptyBlocks(this.selected);
        }
    }

    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/showEmptyBlocks.png";
    }

    private boolean isSelected() {
        return (Boolean)getValue(SELECTED_KEY);
    }

    private void enableIfParentSelected() {
        boolean enable = parentAction.isEnabled() && (Boolean)parentAction.getValue(SELECTED_KEY);
        if (enable != this.isEnabled()) {
            if (enable) {
                putValue(SELECTED_KEY, this.selected);
            } else {
                this.selected = isSelected();
                putValue(SELECTED_KEY, false);
            }
        }
        this.setEnabled(enable);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == this.parentAction) {
            enableIfParentSelected();
        }
    }
}
