/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * $Id: Utils.java,v 1.1.4.1 2005/09/08 11:03:21 suresh_emailid Exp $
 */
package com.sun.org.apache.xml.internal.serializer.utils;

/**
 * This class contains utilities used by the serializer.
 *
 * This class is not a public API, it is only public because it is
 * used by com.sun.org.apache.xml.internal.serializer.
 *
 * @xsl.usage internal
 */
public final class Utils
{
    /**
     * A singleton Messages object is used to load the
     * given resource bundle just once, it is
     * used by multiple transformations as long as the JVM stays up.
     */
    public static final com.sun.org.apache.xml.internal.serializer.utils.Messages messages=
        new com.sun.org.apache.xml.internal.serializer.utils.Messages(
            "com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages");
}
