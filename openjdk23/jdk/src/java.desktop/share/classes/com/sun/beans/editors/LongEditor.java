/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package com.sun.beans.editors;

/**
 * Property editor for a java builtin "long" type.
 *
 */

import java.beans.*;

public class LongEditor extends NumberEditor {

    public String getJavaInitializationString() {
        Object value = getValue();
        return (value != null)
                ? value + "L"
                : "null";
    }

    public void setAsText(String text) throws IllegalArgumentException {
        setValue((text == null) ? null : Long.decode(text));
    }

}
