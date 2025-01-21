/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package javax.xml.parsers.ptests;

import static jaxp.library.JAXPTestUtilities.FILE_SEP;
import static jaxp.library.JAXPTestUtilities.getPathByClassName;


/**
 * Utility interface which includes final variables of XML, golden file
 * directories.
 */
public class ParserTestConst {
    /**
     * XML source file directory.
     */
    public static final String XML_DIR = getPathByClassName(ParserTestConst.class,
            ".." + FILE_SEP + "xmlfiles");


    /**
     * Golden validation files directory.
     */
    public static final String GOLDEN_DIR = getPathByClassName(ParserTestConst.class,
            ".." + FILE_SEP + "xmlfiles" + FILE_SEP + "out");
}
