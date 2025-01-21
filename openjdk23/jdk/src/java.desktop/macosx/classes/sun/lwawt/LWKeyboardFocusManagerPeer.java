/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.lwawt;

import java.awt.Component;
import java.awt.Window;
import sun.awt.KeyboardFocusManagerPeerImpl;

public class LWKeyboardFocusManagerPeer extends KeyboardFocusManagerPeerImpl {
    private static final LWKeyboardFocusManagerPeer inst = new LWKeyboardFocusManagerPeer();

    private Window focusedWindow;
    private Component focusOwner;

    public static LWKeyboardFocusManagerPeer getInstance() {
        return inst;
    }

    private LWKeyboardFocusManagerPeer() {
    }

    @Override
    public void setCurrentFocusedWindow(Window win) {
        LWWindowPeer from, to;

        synchronized (this) {
            if (focusedWindow == win) {
                return;
            }

            from = (LWWindowPeer)LWToolkit.targetToPeer(focusedWindow);
            to = (LWWindowPeer)LWToolkit.targetToPeer(win);

            focusedWindow = win;
        }

    }

    @Override
    public Window getCurrentFocusedWindow() {
        synchronized (this) {
            return focusedWindow;
        }
    }

    @Override
    public Component getCurrentFocusOwner() {
        synchronized (this) {
            return focusOwner;
        }
    }

    @Override
    public void setCurrentFocusOwner(Component comp) {
        synchronized (this) {
            focusOwner = comp;
        }
    }
}
