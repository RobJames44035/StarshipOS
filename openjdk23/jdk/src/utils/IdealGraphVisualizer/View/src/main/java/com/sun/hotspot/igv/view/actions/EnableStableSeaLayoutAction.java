/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.EditorTopComponent;
import java.beans.PropertyChangeEvent;

public class EnableStableSeaLayoutAction extends EnableLayoutAction {

    public EnableStableSeaLayoutAction(EditorTopComponent etc) {
        super(etc);
    }

    @Override
    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/stable_sea.png";
    }

    @Override
    protected String getDescription() {
        return "Show stable sea of nodes";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        editor.getModel().setShowStableSea(this.isSelected());
    }
}
