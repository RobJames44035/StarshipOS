/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xalan.internal.res;

import com.sun.org.apache.xpath.internal.res.XPATHMessages;
import java.util.ResourceBundle;
import jdk.xml.internal.SecuritySupport;

/**
 * Sets things up for issuing error messages. This class is misnamed, and should
 * be called XalanMessages, or some such.
 *
 * @xsl.usage internal
 * @LastModified: Sep 2017
 */
public class XSLMessages extends XPATHMessages {

    /**
     * The language specific resource object for Xalan messages.
     */
    private static ResourceBundle XSLTBundle = null;
    /**
     * The class name of the Xalan error message string table.
     */
    private static final String XSLT_ERROR_RESOURCES =
            "com.sun.org.apache.xalan.internal.res.XSLTErrorResources";

    /**
     * Creates a message from the specified key and replacement arguments,
     * localized to the given locale.
     *
     * @param msgKey The key for the message text.
     * @param args The arguments to be used as replacement text in the message
     * created.
     *
     * @return The formatted message string.
     */
    public static String createMessage(String msgKey, Object args[]) //throws Exception
    {
        if (XSLTBundle == null) {
            XSLTBundle = SecuritySupport.getResourceBundle(XSLT_ERROR_RESOURCES);
        }

        if (XSLTBundle != null) {
            return createMsg(XSLTBundle, msgKey, args);
        } else {
            return "Could not load any resource bundles.";
        }
    }

    /**
     * Creates a message from the specified key and replacement arguments,
     * localized to the given locale.
     *
     * @param msgKey The key for the message text.
     * @param args The arguments to be used as replacement text in the message
     * created.
     *
     * @return The formatted warning string.
     */
    public static String createWarning(String msgKey, Object args[]) //throws Exception
    {
        if (XSLTBundle == null) {
            XSLTBundle = SecuritySupport.getResourceBundle(XSLT_ERROR_RESOURCES);
        }

        if (XSLTBundle != null) {
            return createMsg(XSLTBundle, msgKey, args);
        } else {
            return "Could not load any resource bundles.";
        }
    }
}
