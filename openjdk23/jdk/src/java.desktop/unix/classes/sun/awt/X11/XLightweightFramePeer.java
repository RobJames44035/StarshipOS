/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package sun.awt.X11;

import java.awt.Graphics;
import java.awt.dnd.DropTarget;

import sun.awt.LightweightFrame;
import sun.awt.OverrideNativeWindowHandle;
import sun.swing.JLightweightFrame;
import sun.swing.SwingAccessor;

public class XLightweightFramePeer extends XFramePeer implements OverrideNativeWindowHandle {

    XLightweightFramePeer(LightweightFrame target) {
        super(target);
    }

    private LightweightFrame getLwTarget() {
        return (LightweightFrame)target;
    }

    @Override
    public Graphics getGraphics() {
        return getLwTarget().getGraphics();
    }

    @Override
    public void xSetVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    protected void requestXFocus(long time, boolean timeProvided) {
        // not sending native focus events to the proxy
    }

    @Override
    public void setGrab(boolean grab) {
        if (grab) {
            getLwTarget().grabFocus();
        } else {
            getLwTarget().ungrabFocus();
        }
    }

    @Override
    public void updateCursorImmediately() {
        SwingAccessor.getJLightweightFrameAccessor().updateCursor((JLightweightFrame)getLwTarget());
    }

    @Override
    public void addDropTarget(DropTarget dt) {
        getLwTarget().addDropTarget(dt);
    }

    @Override
    public void removeDropTarget(DropTarget dt) {
        getLwTarget().removeDropTarget(dt);
    }

    private volatile long overriddenWindowHandle;

    @Override
    public void overrideWindowHandle(final long handle) {
        overriddenWindowHandle = handle;
    }

    public long getOverriddenWindowHandle() {
        return overriddenWindowHandle;
    }
}
