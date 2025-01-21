/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package com.sun.jmx.defaults;

/**
 * Used for storing default values used by JMX services.
 *
 * @since 1.5
 */
public class ServiceName {

    // private constructor defined to "hide" the default public constructor
    private ServiceName() {
    }

    /**
     * The object name of the MBeanServer delegate object
     * <BR>
     * The value is <CODE>JMImplementation:type=MBeanServerDelegate</CODE>.
     */
    public static final String DELEGATE =
        "JMImplementation:type=MBeanServerDelegate" ;

    /**
     * The default domain.
     * <BR>
     * The value is <CODE>DefaultDomain</CODE>.
     */
    public static final String DOMAIN = "DefaultDomain";

    /**
     * The name of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>Java Management Extensions</CODE>.
     */
    public static final String JMX_SPEC_NAME = "Java Management Extensions";

    /**
     * The version of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>1.4</CODE>.
     */
    public static final String JMX_SPEC_VERSION = "1.4";

    /**
     * The vendor of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>Oracle Corporation</CODE>.
     */
    public static final String JMX_SPEC_VENDOR = "Oracle Corporation";

    /**
     * The name of this product implementing the  JMX specification.
     * <BR>
     * The value is <CODE>JMX</CODE>.
     */
    public static final String JMX_IMPL_NAME = "JMX";

    /**
     * The name of the vendor of this product implementing the
     * JMX specification.
     * <BR>
     * The value is <CODE>Oracle Corporation</CODE>.
     */
    public static final String JMX_IMPL_VENDOR = "Oracle Corporation";
}
