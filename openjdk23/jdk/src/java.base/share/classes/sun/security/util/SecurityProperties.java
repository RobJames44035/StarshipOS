/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package sun.security.util;

import java.security.Security;
import java.util.Locale;

/**
 * Utility methods for retrieving security and system properties.
 */
public class SecurityProperties {

    public static final boolean INCLUDE_JAR_NAME_IN_EXCEPTIONS
        = includedInExceptions("jar");

    /**
     * Returns the value of the security property propName, which can be overridden
     * by a system property of the same name
     *
     * @param  propName the name of the system or security property
     * @return the value of the system or security property
     */
    public static String getOverridableProperty(String propName) {
        String val = System.getProperty(propName);
        if (val == null) {
            return Security.getProperty(propName);
        } else {
            return val;
        }
    }

    /**
     * Returns true in case the system or security property "jdk.includeInExceptions"
     * contains the category refName
     *
     * @param refName the category to check
     * @return true in case the system or security property "jdk.includeInExceptions"
     *         contains refName, false otherwise
     */
    public static boolean includedInExceptions(String refName) {
        String val = getOverridableProperty("jdk.includeInExceptions");
        if (val == null) {
            return false;
        }

        String[] tokens = val.split(",");
        for (String token : tokens) {
            token = token.trim();
            if (token.equalsIgnoreCase(refName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convenience method for fetching System property values that are timeouts.
     * Accepted timeout values may be purely numeric, a numeric value
     * followed by "s" (both interpreted as seconds), or a numeric value
     * followed by "ms" (interpreted as milliseconds).
     *
     * @param prop the name of the System property
     * @param def a default value (in milliseconds)
     * @param dbg a Debug object, if null no debug messages will be sent
     *
     * @return an integer value corresponding to the timeout value in the System
     *      property in milliseconds.  If the property value is empty, negative,
     *      or contains non-numeric characters (besides a trailing "s" or "ms")
     *      then the default value will be returned.  If a negative value for
     *      the "def" parameter is supplied, zero will be returned if the
     *      property's value does not conform to the allowed syntax.
     */
    public static int getTimeoutSystemProp(String prop, int def, Debug dbg) {
        if (def < 0) {
            def = 0;
        }

        String rawPropVal = System.getProperty(prop, "").trim();
        if (rawPropVal.length() == 0) {
            return def;
        }

        // Determine if "ms" or just "s" is on the end of the string.
        // We may do a little surgery on the value so we'll retain
        // the original value in rawPropVal for debug messages.
        boolean isMillis = false;
        String propVal = rawPropVal;
        if (rawPropVal.toLowerCase(Locale.ROOT).endsWith("ms")) {
            propVal = rawPropVal.substring(0, rawPropVal.length() - 2);
            isMillis = true;
        } else if (rawPropVal.toLowerCase(Locale.ROOT).endsWith("s")) {
            propVal = rawPropVal.substring(0, rawPropVal.length() - 1);
        }

        // Next check to make sure the string is built only from digits
        if (propVal.matches("^\\d+$")) {
            try {
                int timeout = Integer.parseInt(propVal);
                return isMillis ? timeout : timeout * 1000;
            } catch (NumberFormatException nfe) {
                if (dbg != null) {
                    dbg.println("Warning: Unexpected " + nfe +
                            " for timeout value " + rawPropVal +
                            ". Using default value of " + def + " msec.");
                }
                return def;
            }
        } else {
            if (dbg != null) {
                dbg.println("Warning: Incorrect syntax for timeout value " +
                        rawPropVal + ". Using default value of " + def +
                        " msec.");
            }
            return def;
        }
    }

    /**
     * Convenience method for fetching System property values that are booleans.
     *
     * @param prop the name of the System property
     * @param def a default value
     * @param dbg a Debug object, if null no debug messages will be sent
     *
     * @return a boolean value corresponding to the value in the System property.
     *      If the property value is neither "true" or "false", the default value
     *      will be returned.
     */
    public static boolean getBooleanSystemProp(String prop, boolean def, Debug dbg) {
        String rawPropVal = System.getProperty(prop, "");
        if ("".equals(rawPropVal)) {
            return def;
        }

        String lower = rawPropVal.toLowerCase(Locale.ROOT);
        if ("true".equals(lower)) {
            return true;
        } else if ("false".equals(lower)) {
            return false;
        } else {
            if (dbg != null) {
                dbg.println("Warning: Unexpected value for " + prop +
                            ": " + rawPropVal +
                            ". Using default value: " + def);
            }
            return def;
        }
    }
}
