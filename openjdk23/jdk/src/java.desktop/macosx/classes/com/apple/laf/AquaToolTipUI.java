/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.apple.laf;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

import com.apple.laf.AquaUtils.RecyclableSingletonFromDefaultConstructor;

public class AquaToolTipUI extends BasicToolTipUI {
    private static final RecyclableSingletonFromDefaultConstructor<AquaToolTipUI> sharedAquaInstance = new RecyclableSingletonFromDefaultConstructor<AquaToolTipUI>(AquaToolTipUI.class);

    public static ComponentUI createUI(final JComponent c) {
        return sharedAquaInstance.get();
    }

    public AquaToolTipUI() {
        super();
    }
}
