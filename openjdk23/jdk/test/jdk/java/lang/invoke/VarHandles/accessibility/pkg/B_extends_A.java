/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package pkg;


import java.lang.invoke.MethodHandles;
import java.util.Set;

public class B_extends_A extends A {
    public static MethodHandles.Lookup lookup() {
        return MethodHandles.lookup();
    }

    public static Set<String> inaccessibleFields() {
        // Only private fields of pkg.A are not accessible to subclass pkg.B
        // Note: protected fields are also package accessible
        return Set.of(
                "f_private",
                "f_private_final",
                "f_private_static",
                "f_private_static_final"
        );
    }
}
