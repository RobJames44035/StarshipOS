/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package com.sun.java.swing.plaf.windows;

import javax.swing.UIManager;

import sun.swing.plaf.DesktopProperty;

/**
 * Wrapper for a value from the desktop. The value is lazily looked up, and
 * can be accessed using the <code>UIManager.ActiveValue</code> method
 * <code>createValue</code>. If the underlying desktop property changes this
 * will force the UIs to update all known Frames. You can invoke
 * <code>invalidate</code> to force the value to be fetched again.
 */
public class WindowsDesktopProperty extends DesktopProperty {

    /**
     * Updates the UIs of all the known Frames.
     */
    @Override
    protected final void updateAllUIs() {
        // Check if the current UI is WindowsLookAndFeel and flush the XP style map.
        // Note: Change the package test if this class is moved to a different package.
        Class<?> uiClass = UIManager.getLookAndFeel().getClass();
        if (uiClass.getPackage().equals(WindowsDesktopProperty.class.getPackage())) {
            XPStyle.invalidateStyle();
        }
        super.updateAllUIs();
    }

    /**
     * Creates a WindowsDesktopProperty.
     *
     * @param key Key used in looking up desktop value.
     * @param fallback Value used if desktop property is null.
     */
    public WindowsDesktopProperty(String key, Object fallback) {
        super(key,fallback);
    }
}
