/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.accessibility;

import java.awt.Component;

import javax.accessibility.AccessibleContext;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;

import org.netbeans.jemmy.ComponentChooser;

public abstract class AccessibilityChooser implements ComponentChooser {

    @Override
    public final boolean checkComponent(Component comp) {
        if (comp instanceof JComponent) {
            return checkContext(comp.getAccessibleContext());
        } else if (comp instanceof JDialog) {
            return checkContext(comp.getAccessibleContext());
        } else if (comp instanceof JFrame) {
            return checkContext(comp.getAccessibleContext());
        } else if (comp instanceof JWindow) {
            return checkContext(comp.getAccessibleContext());
        } else {
            return false;
        }
    }

    public abstract boolean checkContext(AccessibleContext context);
}
