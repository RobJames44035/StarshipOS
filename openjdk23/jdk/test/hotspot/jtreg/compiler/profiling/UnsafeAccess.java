/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
/*
 * @test
 * @bug 8134918
 * @modules java.base/jdk.internal.misc
 *
 * @requires vm.compMode != "Xcomp"
 * @run main/bootclasspath/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:TypeProfileLevel=222 -XX:+UseTypeSpeculation -Xbatch
 *                                 -XX:CompileCommand=dontinline,compiler.profiling.UnsafeAccess::test*
 *                                 compiler.profiling.UnsafeAccess
 */

package compiler.profiling;

import jdk.internal.misc.Unsafe;

public class UnsafeAccess {
    private static final Unsafe U = Unsafe.getUnsafe();

    static Class cls = Object.class;
    static long off = U.ARRAY_OBJECT_BASE_OFFSET;

    static Object testUnsafeAccess(Object o, boolean isObjArray) {
        if (o != null && cls.isInstance(o)) { // speculates "o" type to int[]
            return helperUnsafeAccess(o, isObjArray);
        }
        return null;
    }

    static Object helperUnsafeAccess(Object o, boolean isObjArray) {
        if (isObjArray) {
            U.putReference(o, off, new Object());
        }
        return o;
    }

    static Object testUnsafeLoadStore(Object o, boolean isObjArray) {
        if (o != null && cls.isInstance(o)) { // speculates "o" type to int[]
            return helperUnsafeLoadStore(o, isObjArray);
        }
        return null;
    }

    static Object helperUnsafeLoadStore(Object o, boolean isObjArray) {
        if (isObjArray) {
            Object o1 = U.getReference(o, off);
            U.compareAndSetReference(o, off, o1, new Object());
        }
        return o;
    }

    public static void main(String[] args) {
        Object[] objArray = new Object[10];
        int[]    intArray = new    int[10];

        for (int i = 0; i < 20_000; i++) {
            helperUnsafeAccess(objArray, true);
        }
        for (int i = 0; i < 20_000; i++) {
            testUnsafeAccess(intArray, false);
        }

        for (int i = 0; i < 20_000; i++) {
            helperUnsafeLoadStore(objArray, true);
        }
        for (int i = 0; i < 20_000; i++) {
            testUnsafeLoadStore(intArray, false);
        }

        System.out.println("TEST PASSED");
    }
}
