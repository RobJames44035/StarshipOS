/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package com.apple.laf;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MenuBar;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

import sun.lwawt.macosx.LWCToolkit;

// MenuBar implementation for Mac L&F
@SuppressWarnings("restricted")
public class AquaMenuBarUI extends BasicMenuBarUI implements ScreenMenuBarProvider {

    static {
        System.loadLibrary("osxui");
    }

    // Utilities
    public void uninstallUI(final JComponent c) {
        if (fScreenMenuBar != null) {
            final JFrame frame = (JFrame)(c.getTopLevelAncestor());
            if (frame != null && frame.getMenuBar() == fScreenMenuBar) {
                frame.setMenuBar((MenuBar)null);
            }
            fScreenMenuBar = null;
        }
        super.uninstallUI(c);
    }

    // Create PLAF
    public static ComponentUI createUI(final JComponent c) {
        return new AquaMenuBarUI();
    }

    // [3320390] -- If the screen menu bar is in use, don't register keyboard actions that
    // show the menus when F10 is pressed.
    protected void installKeyboardActions() {
        if (!useScreenMenuBar) {
            super.installKeyboardActions();
        }
    }

    protected void uninstallKeyboardActions() {
        if (!useScreenMenuBar) {
            super.uninstallKeyboardActions();
        }
    }

    // Paint Methods
    public void paint(final Graphics g, final JComponent c) {
        AquaMenuPainter.instance().paintMenuBarBackground(g, c.getWidth(), c.getHeight(), c);
    }

    public Dimension getPreferredSize(final JComponent c) {
        if (isScreenMenuBar((JMenuBar)c)) {
            if (setScreenMenuBar((JFrame)(c.getTopLevelAncestor()))) {
                return new Dimension(0, 0);
            }
        }
        return null;
    }

    void clearScreenMenuBar(final JFrame frame) {
        if (useScreenMenuBar) {
            frame.setMenuBar(null);
        }
    }

    boolean setScreenMenuBar(final JFrame frame) {
        if (useScreenMenuBar) {
            try {
                getScreenMenuBar();
            } catch(final Throwable t) {
                return false;
            }

            frame.setMenuBar(fScreenMenuBar);
        }

        return true;
    }

    public ScreenMenuBar getScreenMenuBar() {
        // Lazy init of member variables means we should use a synchronized block.
        synchronized(this) {
            if (fScreenMenuBar == null) fScreenMenuBar = new ScreenMenuBar(this.menuBar);
        }
        return fScreenMenuBar;
    }

    // JMenuBars are in frame unless we're using ScreenMenuBars *and* it's
    //   been set by JFrame.setJMenuBar
    // unless the JFrame has a normal java.awt.MenuBar (it's possible!)
    // Other JMenuBars appear where the programmer puts them - top of window or elsewhere
    public static final boolean isScreenMenuBar(final JMenuBar c) {
        final javax.swing.plaf.ComponentUI ui = c.getUI();
        if (ui instanceof AquaMenuBarUI) {
            if (!((AquaMenuBarUI)ui).useScreenMenuBar) return false;

            final Component parent = c.getTopLevelAncestor();
            if (parent instanceof JFrame) {
                final MenuBar mb = ((JFrame)parent).getMenuBar();
                final boolean thisIsTheJMenuBar = (((JFrame)parent).getJMenuBar() == c);
                if (mb == null) return thisIsTheJMenuBar;
                return (mb instanceof ScreenMenuBar && thisIsTheJMenuBar);
            }
        }
        return false;
    }

    ScreenMenuBar fScreenMenuBar;
    boolean useScreenMenuBar = getScreenMenuBarProperty();

    public static boolean getScreenMenuBarProperty() {
        // Do not allow AWT to set the screen menu bar if it's embedded in another UI toolkit
        if (LWCToolkit.isEmbedded()) return false;
        return Boolean.getBoolean(AquaLookAndFeel.sPropertyPrefix + "useScreenMenuBar");
    }
}
