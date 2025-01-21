/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package jdk.internal.loader;

import java.io.File;
import java.util.ArrayList;

import jdk.internal.util.OSVersion;

class ClassLoaderHelper {

    // SDK 10.15 and earlier always reports 10.16 instead of 11.x.x
    private static final boolean hasDynamicLoaderCache = OSVersion.current()
            .compareTo(new OSVersion(10, 16)) >= 0;

    private ClassLoaderHelper() {}

    /**
     * Returns true if loading a native library only if
     * it's present on the file system.
     *
     * @implNote
     * On macOS 11.x or later which supports dynamic linker cache,
     * the dynamic library is not present on the filesystem.  The
     * library cannot determine if a dynamic library exists on a
     * given path or not and so this method returns false.
     */
    static boolean loadLibraryOnlyIfPresent() {
        return !hasDynamicLoaderCache;
    }

    /**
     * Returns an alternate path name for the given file
     * such that if the original pathname did not exist, then the
     * file may be located at the alternate location.
     * For mac, this replaces the final .dylib suffix with .jnilib
     */
    static File mapAlternativeName(File lib) {
        String name = lib.toString();
        int index = name.lastIndexOf('.');
        if (index < 0) {
            return null;
        }
        return new File(name.substring(0, index) + ".jnilib");
    }

    /**
     * Parse a PATH env variable.
     *
     * Empty elements will be replaced by dot.
     */
    static String[] parsePath(String ldPath) {
        char ps = File.pathSeparatorChar;
        ArrayList<String> paths = new ArrayList<>();
        int pathStart = 0;
        int pathEnd;
        while ((pathEnd = ldPath.indexOf(ps, pathStart)) >= 0) {
            paths.add((pathStart < pathEnd) ?
                    ldPath.substring(pathStart, pathEnd) : ".");
            pathStart = pathEnd + 1;
        }
        int ldLen = ldPath.length();
        paths.add((pathStart < ldLen) ?
                ldPath.substring(pathStart, ldLen) : ".");
        return paths.toArray(new String[paths.size()]);
    }
}
