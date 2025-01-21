/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.java2d.opengl;

import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.ColorModel;

import sun.awt.image.SunVolatileImage;
import sun.awt.image.VolatileSurfaceManager;
import sun.java2d.SurfaceData;

import static sun.java2d.opengl.OGLContext.OGLContextCaps.CAPS_EXT_FBOBJECT;

public class CGLVolatileSurfaceManager extends VolatileSurfaceManager {

    private final boolean accelerationEnabled;

    public CGLVolatileSurfaceManager(SunVolatileImage vImg, Object context) {
        super(vImg, context);

        /*
         * We will attempt to accelerate this image only under the
         * following conditions:
         *   - the image is not bitmask AND the GraphicsConfig supports the FBO
         *     extension
         */
        int transparency = vImg.getTransparency();
        CGLGraphicsConfig gc = (CGLGraphicsConfig) vImg.getGraphicsConfig();
        accelerationEnabled = gc.isCapPresent(CAPS_EXT_FBOBJECT)
                && transparency != Transparency.BITMASK;
    }

    protected boolean isAccelerationEnabled() {
        return accelerationEnabled;
    }

    /**
     * Create a FBO-based SurfaceData object (or init the backbuffer
     * of an existing window if this is a double buffered GraphicsConfig)
     */
    protected SurfaceData initAcceleratedSurface() {
        try {
            CGLGraphicsConfig gc = (CGLGraphicsConfig)vImg.getGraphicsConfig();
            ColorModel cm = gc.getColorModel(vImg.getTransparency());
            int type = vImg.getForcedAccelSurfaceType();
            // if acceleration type is forced (type != UNDEFINED) then
            // use the forced type, otherwise choose FBOBJECT
            if (type == OGLSurfaceData.UNDEFINED) {
                type = OGLSurfaceData.FBOBJECT;
            }
            return CGLSurfaceData.createData(gc, vImg.getWidth(),
                                             vImg.getHeight(), cm, vImg, type);
        } catch (NullPointerException | OutOfMemoryError ignored) {
            return null;
        }
    }

    @Override
    protected boolean isConfigValid(GraphicsConfiguration gc) {
        return ((gc == null) || (gc == vImg.getGraphicsConfig()));
    }

    @Override
    public void initContents() {
        if (vImg.getForcedAccelSurfaceType() != OGLSurfaceData.TEXTURE) {
            super.initContents();
        }
    }
}

