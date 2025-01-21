/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package sun.awt.windows;

import java.awt.*;
import java.awt.peer.*;
import java.awt.event.TextEvent;

abstract
class WTextComponentPeer extends WComponentPeer implements TextComponentPeer {

    // TextComponentPeer implementation

    @Override
    public void setEditable(boolean editable) {
        enableEditing(editable);
        setBackground(((TextComponent)target).getBackground());
    }
    @Override
    public native String getText();
    @Override
    public native void setText(String text);
    @Override
    public native int getSelectionStart();
    @Override
    public native int getSelectionEnd();
    @Override
    public native void select(int selStart, int selEnd);

    // Toolkit & peer internals

    WTextComponentPeer(TextComponent target) {
        super(target);
    }

    @Override
    void initialize() {
        TextComponent tc = (TextComponent)target;
        String text = tc.getText();

        if (text != null) {
            setText(text);
        }
        select(tc.getSelectionStart(), tc.getSelectionEnd());
        setEditable(tc.isEditable());

        super.initialize();
    }

    native void enableEditing(boolean e);

    @Override
    public boolean isFocusable() {
        return true;
    }

    /*
     * Set the caret position by doing an empty selection. This
     * unfortunately resets the selection, but seems to be the
     * only way to get this to work.
     */
    @Override
    public void setCaretPosition(int pos) {
        select(pos,pos);
    }

    /*
     * Get the caret position by looking up the end of the current
     * selection.
     */
    @Override
    public int getCaretPosition() {
        return getSelectionStart();
    }

    /*
     * Post a new TextEvent when the value of a text component changes.
     */
    public void valueChanged() {
        postEvent(new TextEvent(target, TextEvent.TEXT_VALUE_CHANGED));
    }

    @Override
    public boolean shouldClearRectBeforePaint() {
        return false;
    }
}
