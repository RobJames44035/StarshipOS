/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package sun.security.util;

import jdk.internal.util.StaticProperty;

import java.io.File;

public class FilePaths {
    public static String cacerts() {
        return StaticProperty.javaHome() + File.separator + "lib"
                + File.separator + "security" + File.separator + "cacerts";
    }
}
