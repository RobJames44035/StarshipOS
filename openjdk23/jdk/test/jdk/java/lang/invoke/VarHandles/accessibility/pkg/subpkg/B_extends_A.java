/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package pkg.subpkg;


import pkg.A;

import java.lang.invoke.MethodHandles;
import java.util.Set;

public class B_extends_A extends A {
    public static MethodHandles.Lookup lookup() {
        return MethodHandles.lookup();
    }

    public static Set<String> inaccessibleFields() {
        // Only public and protected fields of pkg.A are accessible to subclass
        // pkg.subpkg.B
        return Set.of(
                "f_private",
                "f_private_final",
                "f_package",
                "f_package_final",
                "f_private_static",
                "f_private_static_final",
                "f_package_static",
                "f_package_static_final"
        );
    }
}
