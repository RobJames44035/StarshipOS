/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.awt.windows;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;
import java.awt.peer.MouseInfoPeer;

public final class WMouseInfoPeer implements MouseInfoPeer {

    static {
        // initialize screen devices for the mouse coordinates scaling
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    /**
     * Package-private constructor to prevent instantiation.
     */
    WMouseInfoPeer() {
    }

    public native int fillPointWithCoords(Point point);

    public native boolean isWindowUnderMouse(Window w);

}
