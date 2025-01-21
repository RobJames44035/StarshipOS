/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */


package sun.awt.X11;

public interface XLayerProtocol {

    static final int LAYER_NORMAL = 0,
        LAYER_ALWAYS_ON_TOP = 1;

    boolean supportsLayer(int layer);
    void setLayer(XWindowPeer window, int layer);
}
