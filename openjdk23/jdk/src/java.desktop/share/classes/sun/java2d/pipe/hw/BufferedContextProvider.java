/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package sun.java2d.pipe.hw;

import sun.java2d.pipe.BufferedContext;

/**
 * Classes implementing this interface can provide the {@code BufferedContext}
 * associated with or used by them.
 *
 * @see sun.java2d.pipe.BufferedContext
 */
public interface BufferedContextProvider {
    /**
     * Retrieves a context associated with object implementing this
     * interface.
     *
     * @return associated context
     * @see sun.java2d.pipe.BufferedContext
     */
    public BufferedContext getContext();
}
