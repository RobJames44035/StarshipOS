/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package sun.awt.X11;

import java.awt.*;
import java.awt.peer.*;

import sun.awt.SunToolkit;

import sun.awt.X11GraphicsConfig;
import sun.awt.X11GraphicsDevice;

class XCanvasPeer extends XComponentPeer implements CanvasPeer {

    private boolean eraseBackgroundDisabled;

    XCanvasPeer() {}

    XCanvasPeer(XCreateWindowParams params) {
        super(params);
    }

    XCanvasPeer(Component target) {
        super(target);
    }

    void preInit(XCreateWindowParams params) {
        super.preInit(params);
        if (SunToolkit.getSunAwtNoerasebackground()) {
            disableBackgroundErase();
        }
    }

    /* Get a GraphicsConfig with the same visual on the new
     * screen, which should be easy in Xinerama mode.
     */
    public GraphicsConfiguration getAppropriateGraphicsConfiguration(
                                    GraphicsConfiguration gc)
    {
        if (graphicsConfig == null || gc == null) {
            return gc;
        }

        final X11GraphicsDevice newDev = getSameScreenDevice(gc);
        final int visualToLookFor = graphicsConfig.getVisual();

        final GraphicsConfiguration[] configurations = newDev.getConfigurations();
        for (final GraphicsConfiguration config : configurations) {
            final X11GraphicsConfig x11gc = (X11GraphicsConfig) config;
            if (visualToLookFor == x11gc.getVisual()) {
                graphicsConfig = x11gc;
            }
        }

        return graphicsConfig;
    }

    private X11GraphicsDevice getSameScreenDevice(GraphicsConfiguration gc) {
        XToolkit.awtLock(); // so that the number of screens doesn't change during
        try {
            final int screenNum = ((X11GraphicsDevice) gc.getDevice()).getScreen();
            return (X11GraphicsDevice) GraphicsEnvironment.
                    getLocalGraphicsEnvironment().
                    getScreenDevices()[screenNum];
        } finally {
            XToolkit.awtUnlock();
        }
    }

    protected boolean shouldFocusOnClick() {
        // Canvas should always be able to be focused by mouse clicks.
        return true;
    }

    public void disableBackgroundErase() {
        eraseBackgroundDisabled = true;
    }
    protected boolean doEraseBackground() {
        return !eraseBackgroundDisabled;
    }
}
