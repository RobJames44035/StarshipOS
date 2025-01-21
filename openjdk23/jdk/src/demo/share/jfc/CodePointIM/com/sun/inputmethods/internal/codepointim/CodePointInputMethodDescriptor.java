/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * This source code is provided to illustrate the usage of a given feature
 * or technique and has been deliberately simplified. Additional steps
 * required for a production-quality application, such as security checks,
 * input validation and proper error handling, might not be present in
 * this sample code.
 */

package com.sun.inputmethods.internal.codepointim;


import java.awt.Image;
import java.awt.im.spi.InputMethodDescriptor;
import java.awt.im.spi.InputMethod;
import java.util.Locale;


/**
 * The CodePointInputMethod is a simple input method that allows Unicode
 * characters to be entered via their hexadecimal code point values.
 *
 * The class, CodePointInputMethodDescriptor, provides information about the
 * CodePointInputMethod which allows it to be selected and loaded by the
 * Input Method Framework.
 */
public class CodePointInputMethodDescriptor implements InputMethodDescriptor {

    public CodePointInputMethodDescriptor() {
    }

    /**
     * Creates a new instance of the Code Point input method.
     *
     * @return a new instance of the Code Point input method
     * @exception Exception any exception that may occur while creating the
     * input method instance
     */
    public InputMethod createInputMethod() throws Exception {
        return new CodePointInputMethod();
    }

    /**
     * This input method can be used by any locale.
     */
    public Locale[] getAvailableLocales() {
        Locale[] locales = {
            Locale.ROOT, };
        return locales;
    }

    public synchronized String getInputMethodDisplayName(Locale inputLocale,
            Locale displayLanguage) {
        return "CodePoint Input Method";
    }

    public Image getInputMethodIcon(Locale inputLocale) {
        return null;
    }

    public boolean hasDynamicLocaleList() {
        return false;
    }
}
