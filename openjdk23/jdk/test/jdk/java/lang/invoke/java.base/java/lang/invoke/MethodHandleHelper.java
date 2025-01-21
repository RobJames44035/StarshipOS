/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package java.lang.invoke;

import java.lang.invoke.MethodHandles.Lookup;

/**
 * Helper class to inject into java.lang.invoke that provides access to
 * package-private methods in this package.
 */

public class MethodHandleHelper {

    private MethodHandleHelper() { }

    public static final Lookup IMPL_LOOKUP = Lookup.IMPL_LOOKUP;

    public static void customize(MethodHandle mh) {
        mh.customize();
    }

    public static MethodHandle varargsArray(int nargs) {
        return MethodHandleImpl.varargsArray(nargs);
    }

    public static MethodHandle varargsArray(Class<?> arrayType, int nargs) {
        return MethodHandleImpl.varargsArray(arrayType, nargs);
    }

}

