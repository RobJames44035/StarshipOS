/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package test.auctionportal;

import static jaxp.library.JAXPTestUtilities.getPathByClassName;

/**
 * This is the Base test class provide basic support for Auction portal test.
 */
public class HiBidConstants {
    /**
     * XML source file directory.
     */
    public static final String XML_DIR = getPathByClassName(HiBidConstants.class, "content");

    /**
     * Golden validation files directory.
     */
    public static final String GOLDEN_DIR = getPathByClassName(HiBidConstants.class, "golden");

    /**
     * Name space for account operation.
     */
    public static final String PORTAL_ACCOUNT_NS = "http://www.auctionportal.org/Accounts";

    /**
     * JAXP schema language property name.
     */
    public static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    /**
     * JAXP schema source property name.
     */
    public static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    /**
     * Name of system property JDK entity expansion limit
     */
    public static final String SP_ENTITY_EXPANSION_LIMIT = "jdk.xml.entityExpansionLimit";

    /**
     * Name of system property JDK maxOccur limit
     */
    public static final String SP_MAX_OCCUR_LIMIT = "jdk.xml.maxOccurLimit";
}
