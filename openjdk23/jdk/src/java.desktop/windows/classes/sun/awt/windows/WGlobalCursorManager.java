/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package sun.awt.windows;

import java.awt.*;
import sun.awt.GlobalCursorManager;

final class WGlobalCursorManager extends GlobalCursorManager {
    private static WGlobalCursorManager manager;

    public static GlobalCursorManager getCursorManager() {
        if (manager == null) {
            manager = new WGlobalCursorManager();
        }
        return manager;
    }

    /**
     * Should be called in response to a native mouse enter or native mouse
     * button released message. Should not be called during a mouse drag.
     */
    public static void nativeUpdateCursor(Component heavy) {
        WGlobalCursorManager.getCursorManager().updateCursorLater(heavy);
    }

    @Override
    protected native void setCursor(Component comp, Cursor cursor, boolean u);
    @Override
    protected native void getCursorPos(Point p);
    /*
     * two native methods to call corresponding methods in Container and
     * Component
     */
    @Override
    protected native Component findHeavyweightUnderCursor(boolean useCache);
    @Override
    protected native Point getLocationOnScreen(Component com);
}
