/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.awt;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;

import sun.awt.image.SurfaceManager;
import sun.java2d.SurfaceData;
import sun.lwawt.LWGraphicsConfig;
import sun.lwawt.macosx.CFRetainedResource;

public abstract class CGraphicsConfig extends GraphicsConfiguration
        implements LWGraphicsConfig, SurfaceManager.ProxiedGraphicsConfig {

    private final CGraphicsDevice device;
    private ColorModel colorModel;
    private final SurfaceManager.ProxyCache surfaceDataProxyCache = new SurfaceManager.ProxyCache();

    protected CGraphicsConfig(CGraphicsDevice device) {
        this.device = device;
    }

    @Override
    public SurfaceManager.ProxyCache getSurfaceDataProxyCache() {
        return surfaceDataProxyCache;
    }

    @Override
    public final Rectangle getBounds() {
        return device.getBounds();
    }

    @Override
    public ColorModel getColorModel() {
        if (colorModel == null) {
            colorModel = getColorModel(Transparency.OPAQUE);
        }
        return colorModel;
    }

    @Override
    public AffineTransform getDefaultTransform() {
        double scaleFactor = device.getScaleFactor();
        return AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
    }

    @Override
    public CGraphicsDevice getDevice() {
        return device;
    }

    @Override
    public AffineTransform getNormalizingTransform() {
        double xscale = device.getXResolution() / 72.0;
        double yscale = device.getYResolution() / 72.0;
        return new AffineTransform(xscale, 0.0, 0.0, yscale, 0.0, 0.0);
    }

    /**
     * Creates a new SurfaceData that will be associated with the given
     * layer (CGLLayer/MTLLayer).
     */
    public abstract SurfaceData createSurfaceData(CFRetainedResource layer);

    @Override
    public final boolean isTranslucencyCapable() {
        //we know for sure we have capable config :)
        return true;
    }
}
