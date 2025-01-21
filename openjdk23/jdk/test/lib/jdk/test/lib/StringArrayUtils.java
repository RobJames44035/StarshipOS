/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.test.lib;

import java.util.ArrayList;

public class StringArrayUtils {
    /**
     * The various concat() functions in this class can be used for building
     * a command-line argument array for ProcessTools.createTestJavaProcessBuilder(),
     * etc. When some of the arguments are conditional, this is more convenient
     * than alternatives like ArrayList.
     *
     * Example:
     *
     * <pre>
     *     String args[] = StringArrayUtils.concat("-Xint", "-Xmx32m");
     *     if (verbose) {
     *         args = StringArrayUtils.concat(args, "-verbose");
     *     }
     *     args = StringArrayUtils.concat(args, "HelloWorld");
     *     ProcessTools.createTestJavaProcessBuilder(args);
     * </pre>
     */
    public static String[] concat(String... args) {
        return args;
    }

    public static String[] concat(String[] prefix, String... extra) {
        String[] ret = new String[prefix.length + extra.length];
        System.arraycopy(prefix, 0, ret, 0, prefix.length);
        System.arraycopy(extra, 0, ret, prefix.length, extra.length);
        return ret;
    }

    public static String[] concat(String prefix, String[] extra) {
        String[] ret = new String[1 + extra.length];
        ret[0] = prefix;
        System.arraycopy(extra, 0, ret, 1, extra.length);
        return ret;
    }
}
