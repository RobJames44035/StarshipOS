/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */


package sun.awt.X11;

public interface XModalityProtocol {
    /**
     * Sets modality mode on the dialog.
     * Returns true if the call to this function made dialog
     * visible, otherwise returns false
     */
    boolean setModal(XDialogPeer dialog, boolean modal);

    /**
     * Returns whether or not modal dialog blocks win.
     * If dialog is null checks whether or not this windows
     * is blocked by any dialog
     */
    boolean isBlocked(XDialogPeer dialog, XWindowPeer win);
}
