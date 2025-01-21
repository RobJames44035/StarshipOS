/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */
package sun.awt;

import java.awt.Component;
import java.awt.event.FocusEvent.Cause;

public interface RequestFocusController
{
    public boolean acceptRequestFocus(Component from, Component to,
                                      boolean temporary, boolean focusedWindowChangeAllowed,
                                      Cause cause);
}
