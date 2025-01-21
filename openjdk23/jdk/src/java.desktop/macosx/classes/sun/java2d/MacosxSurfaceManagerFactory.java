/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.java2d;

import sun.awt.image.SunVolatileImage;
import sun.awt.image.VolatileSurfaceManager;
import sun.awt.CGraphicsDevice;
import sun.java2d.metal.MTLVolatileSurfaceManager;
import sun.java2d.opengl.CGLVolatileSurfaceManager;

/**
 * This is a factory class with static methods for creating a
 * platform-specific instance of a particular SurfaceManager.  Each platform
 * (Windows, Unix, etc.) has its own specialized SurfaceManagerFactory.
 */
public class MacosxSurfaceManagerFactory extends SurfaceManagerFactory {

    /**
     * Creates a new instance of a VolatileSurfaceManager given any
     * arbitrary SunVolatileImage.  An optional context Object can be supplied
     * as a way for the caller to pass pipeline-specific context data to
     * the VolatileSurfaceManager (such as a backbuffer handle, for example).
     *
     * For Mac OS X, this method returns either an CGL/MTL-specific
     * VolatileSurfaceManager based on the GraphicsConfiguration
     * under which the SunVolatileImage was created.
     */
    public VolatileSurfaceManager createVolatileManager(SunVolatileImage vImg,
                                                        Object context)
    {
        return CGraphicsDevice.usingMetalPipeline() ? new MTLVolatileSurfaceManager(vImg, context) :
                new CGLVolatileSurfaceManager(vImg, context);
    }
}
