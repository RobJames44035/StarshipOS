/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package pkg;


import java.lang.invoke.MethodHandles;
import java.util.Set;

public class A {
    public static Object f_public_static;
    protected static Object f_protected_static;
    static /*package*/ Object f_package_static;
    private static Object f_private_static;

    public static final Object f_public_static_final = null;
    protected static final Object f_protected_static_final = null;
    static /*package*/ final Object f_package_static_final = null;
    private static final Object f_private_static_final = null;

    public Object f_public;
    protected Object f_protected;
    /*package*/ Object f_package;
    private Object f_private;

    public final Object f_public_final = null;
    protected final Object f_protected_final = null;
    /*package*/ final Object f_package_final = null;
    private final Object f_private_final = null;

    //

    public static MethodHandles.Lookup lookup() {
        return MethodHandles.lookup();
    }

    public static Set<String> inaccessibleFields() {
        // All fields of pkg.A are accessible to itself
        return Set.of();
    }
}
