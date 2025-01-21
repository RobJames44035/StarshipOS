/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.bytecodes;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * @author Thomas Wuerthinger
 */
public class BytecodeViewAction extends AbstractAction {

    public BytecodeViewAction() {
        super(NbBundle.getMessage(BytecodeViewAction.class, "CTL_BytecodeViewAction"));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = BytecodeViewTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
