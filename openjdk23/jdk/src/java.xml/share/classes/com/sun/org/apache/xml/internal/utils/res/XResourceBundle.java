/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.utils.res;

import java.util.ListResourceBundle;

/**
 * The default (english) resource bundle.
 *
 * @xsl.usage internal
 */
public class XResourceBundle extends ListResourceBundle {

    /**
     * Error resource constants
     */
    public static final String ERROR_RESOURCES =
            "com.sun.org.apache.xalan.internal.res.XSLTErrorResources", XSLT_RESOURCE =
            "com.sun.org.apache.xml.internal.utils.res.XResourceBundle", LANG_BUNDLE_NAME =
            "com.sun.org.apache.xml.internal.utils.res.XResources", MULT_ORDER =
            "multiplierOrder", MULT_PRECEDES = "precedes", MULT_FOLLOWS =
            "follows", LANG_ORIENTATION = "orientation", LANG_RIGHTTOLEFT =
            "rightToLeft", LANG_LEFTTORIGHT = "leftToRight", LANG_NUMBERING =
            "numbering", LANG_ADDITIVE = "additive", LANG_MULT_ADD =
            "multiplicative-additive", LANG_MULTIPLIER =
            "multiplier", LANG_MULTIPLIER_CHAR =
            "multiplierChar", LANG_NUMBERGROUPS = "numberGroups", LANG_NUM_TABLES =
            "tables", LANG_ALPHABET = "alphabet", LANG_TRAD_ALPHABET = "tradAlphabet";


    /**
     * Get the association list.
     *
     * @return The association list.
     */
    public Object[][] getContents() {
        return new Object[][]{
                    {"ui_language", "en"}, {"help_language", "en"}, {"language", "en"},
                    {"alphabet", new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G',
                            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                            'V', 'W', 'X', 'Y', 'Z'})},
                    {"tradAlphabet", new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F',
                            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                            'U', 'V', 'W', 'X', 'Y', 'Z'})},
                    //language orientation
                    {"orientation", "LeftToRight"},
                    //language numbering
                    {"numbering", "additive"},};
    }
}
