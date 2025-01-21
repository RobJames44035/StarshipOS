/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.java2d.metal;

import sun.java2d.SurfaceData;
import sun.java2d.SurfaceDataProxy;
import sun.java2d.loops.CompositeType;

import java.awt.Color;
import java.awt.Transparency;

/**
 * The proxy class contains the logic for when to replace a
 * SurfaceData with a cached MTL Texture and the code to create
 * the accelerated surfaces.
 */
public class MTLSurfaceDataProxy extends SurfaceDataProxy {
    public static SurfaceDataProxy createProxy(SurfaceData srcData,
                                               MTLGraphicsConfig dstConfig)
    {
        if (srcData instanceof MTLSurfaceData) {
            // srcData must be a VolatileImage which either matches
            // our pixel format or not - either way we do not cache it...
            return UNCACHED;
        }

        return new MTLSurfaceDataProxy(dstConfig, srcData.getTransparency());
    }

    MTLGraphicsConfig mtlgc;
    int transparency;

    public MTLSurfaceDataProxy(MTLGraphicsConfig mtlgc, int transparency) {
        this.mtlgc = mtlgc;
        this.transparency = transparency;
    }

    @Override
    public SurfaceData validateSurfaceData(SurfaceData srcData,
                                           SurfaceData cachedData,
                                           int w, int h)
    {
        if (cachedData == null) {
            try {
                cachedData = mtlgc.createManagedSurface(w, h, transparency);
            } catch (OutOfMemoryError er) {
                return null;
            }
        }
        return cachedData;
    }

    @Override
    public boolean isSupportedOperation(SurfaceData srcData,
                                        int txtype,
                                        CompositeType comp,
                                        Color bgColor)
    {
        return comp.isDerivedFrom(CompositeType.AnyAlpha) &&
                (bgColor == null || transparency == Transparency.OPAQUE);
    }
}
