/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file, and Oracle licenses the original version of this file under the BSD
 * license:
 */
package jdk.dynalink.beans;

import java.lang.reflect.Modifier;

/**
 * A utility class to check whether a given class is in a package with restricted access e.g. "sun.*" etc.
 */
class CheckRestrictedPackage {

    /**
     * Returns true if the class is either not public, or it resides in a package with restricted access.
     * @param clazz the class to test
     * @return true if the class is either not public, or it resides in a package with restricted access.
     */
    static boolean isRestrictedClass(final Class<?> clazz) {
        if(!Modifier.isPublic(clazz.getModifiers())) {
            // Non-public classes are always restricted
            return true;
        }
        final String name = clazz.getName();
        final int i = name.lastIndexOf('.');
        if (i == -1) {
            // Classes in default package are never restricted
            return false;
        }
        final String pkgName = name.substring(0, i);
        final Module module = clazz.getModule();
        if (module != null && !module.isExported(pkgName)) {
            // Classes in unexported packages of modules are always restricted
            return true;
        }
        return false;
    }
}
