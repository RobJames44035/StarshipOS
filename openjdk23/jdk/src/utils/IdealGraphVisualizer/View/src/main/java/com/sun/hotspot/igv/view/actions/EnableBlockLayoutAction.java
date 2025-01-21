/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.EditorTopComponent;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author Thomas Wuerthinger
 */
public class EnableBlockLayoutAction extends EnableLayoutAction {

    public EnableBlockLayoutAction(EditorTopComponent etc) {
        super(etc);
    }

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/blocks.png";
    }

    @Override
    protected String getDescription() {
        return "Cluster nodes into blocks";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        editor.getModel().setShowBlocks(this.isSelected());
    }
}
