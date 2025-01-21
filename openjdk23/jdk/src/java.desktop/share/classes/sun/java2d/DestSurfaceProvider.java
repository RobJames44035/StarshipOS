/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package sun.java2d;

import sun.java2d.pipe.hw.*;

/**
 * Classes implementing this interface provide access to their
 * destination surfaces.
 */
public interface DestSurfaceProvider {
    /**
     * Returns a surface currently used as a destination surface for rendering.
     *
     * @return destination surface
     */
    public Surface getDestSurface();
}
