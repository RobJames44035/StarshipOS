/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

package sun.java2d.xr;

import sun.awt.X11ComponentPeer;
import sun.awt.X11GraphicsConfig;
import sun.awt.X11GraphicsDevice;
import sun.awt.X11GraphicsEnvironment;
import sun.awt.image.SurfaceManager;
import sun.java2d.SurfaceData;

public class XRGraphicsConfig extends X11GraphicsConfig implements
        SurfaceManager.ProxiedGraphicsConfig {
    private final SurfaceManager.ProxyCache surfaceDataProxyCache = new SurfaceManager.ProxyCache();

    private XRGraphicsConfig(X11GraphicsDevice device, int visualnum,
            int depth, int colormap, boolean doubleBuffer) {
        super(device, visualnum, depth, colormap, doubleBuffer);
    }

    public SurfaceData createSurfaceData(X11ComponentPeer peer) {
        return XRSurfaceData.createData(peer);
    }

    public static XRGraphicsConfig getConfig(X11GraphicsDevice device,
            int visualnum, int depth, int colormap, boolean doubleBuffer) {
        if (!X11GraphicsEnvironment.isXRenderAvailable()) {
            return null;
        }

        return new XRGraphicsConfig(device, visualnum, depth, colormap,
                doubleBuffer);
    }

    @Override
    public SurfaceManager.ProxyCache getSurfaceDataProxyCache() {
        return surfaceDataProxyCache;
    }
}
