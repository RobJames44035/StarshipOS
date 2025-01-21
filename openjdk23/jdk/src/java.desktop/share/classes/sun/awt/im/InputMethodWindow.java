/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package sun.awt.im;

/**
 * Interface for input method windows that need special handling
 * by input method window.
 *
 */
public interface InputMethodWindow {

    /**
     * Sets the input context that this input method window is attached to,
     * null to unattach the window.
     * @see java.awt.im.spi.InputMethodContext#createInputMethodWindow
     */
    public void setInputContext(InputContext inputContext);

}
