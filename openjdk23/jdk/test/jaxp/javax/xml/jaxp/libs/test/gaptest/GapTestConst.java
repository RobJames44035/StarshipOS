/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package test.gaptest;

import static jaxp.library.JAXPTestUtilities.FILE_SEP;
import static jaxp.library.JAXPTestUtilities.getPathByClassName;

/**
 * This class defines the path constant
 */
public class GapTestConst {
    /**
     * XML source file directory.
     */
    public static final String XML_DIR = getPathByClassName(GapTestConst.class, "xmlfiles");

    /**
     * Golden validation files directory.
     */
    public static final String GOLDEN_DIR = getPathByClassName(GapTestConst.class, "xmlfiles" + FILE_SEP + "out");
}
