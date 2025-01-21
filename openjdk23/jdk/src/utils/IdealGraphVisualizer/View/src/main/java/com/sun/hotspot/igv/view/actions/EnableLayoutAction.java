/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.EditorTopComponent;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;


public abstract class EnableLayoutAction extends AbstractAction implements PropertyChangeListener {

    protected final EditorTopComponent editor;

    public EnableLayoutAction(EditorTopComponent etc) {
        editor = etc;
        putValue(AbstractAction.SMALL_ICON, new ImageIcon(ImageUtilities.loadImage(iconResource())));
        putValue(SELECTED_KEY, false);
        putValue(Action.SHORT_DESCRIPTION, getDescription());
        this.addPropertyChangeListener(this);
    }

    protected abstract String getDescription();

    protected abstract String iconResource();

    public boolean isSelected() {
        return (Boolean)getValue(SELECTED_KEY);
    }

    @Override
    public void actionPerformed(ActionEvent e) { }
}
