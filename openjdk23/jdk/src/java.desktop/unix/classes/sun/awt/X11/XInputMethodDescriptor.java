/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */


package sun.awt.X11;

import java.awt.im.spi.InputMethod;
import sun.awt.X11InputMethodDescriptor;

class XInputMethodDescriptor extends X11InputMethodDescriptor {

    /**
     * @see java.awt.im.spi.InputMethodDescriptor#createInputMethod
     */
    public InputMethod createInputMethod() throws Exception {
        return new XInputMethod();
    }
}
