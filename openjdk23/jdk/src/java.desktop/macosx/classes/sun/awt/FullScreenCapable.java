/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.awt;

/**
 * A class implements the FullScreenCapable interface to
 * indicate that it's capable to enter the full-screen mode.
 */
public interface FullScreenCapable {

    /**
     * Enters full-screen mode.
     */
    public void enterFullScreenMode();

    /**
     * Returns to windowed mode.
     */
    public void exitFullScreenMode();

}
