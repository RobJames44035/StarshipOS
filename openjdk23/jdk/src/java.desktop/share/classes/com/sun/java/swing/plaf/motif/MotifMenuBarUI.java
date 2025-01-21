/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package com.sun.java.swing.plaf.motif;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

/**
 * A Windows {@literal L&F} implementation of MenuBarUI.  This implementation
 * is a "combined" view/controller.
 *
 * @author Georges Saab
 * @author Rich Schiavi
 */

public class MotifMenuBarUI extends BasicMenuBarUI
{

    public static ComponentUI createUI(JComponent x) {
        return new MotifMenuBarUI();
    }

} // end class
