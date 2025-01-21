/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package com.sun.hotspot.igv.coordinator.actions;

import com.sun.hotspot.igv.coordinator.OutlineTopComponent;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 *
 * @author Thomas Wuerthinger
 */
public class OutlineAction extends AbstractAction {

    public OutlineAction() {
        super(NbBundle.getMessage(OutlineAction.class, "CTL_OutlineAction"));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = OutlineTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
