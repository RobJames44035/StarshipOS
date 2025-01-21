/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8016474
 * @summary The bug only happens with C1 and G1 using a different ObjectAlignmentInBytes than KlassAlignmentInBytes (which is 8)
 *
 * @modules java.base/jdk.internal.misc:+open
 * @run main/othervm -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:ObjectAlignmentInBytes=32
 *                   compiler.unsafe.GetUnsafeObjectG1PreBarrier
 */

package compiler.unsafe;

import jdk.internal.misc.Unsafe;

import java.lang.reflect.Field;

public class GetUnsafeObjectG1PreBarrier {
    private static final Unsafe unsafe;
    private static final int N = 100_000;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public Object a;

    public static void main(String[] args) throws Throwable {
        new GetUnsafeObjectG1PreBarrier();
    }

    public GetUnsafeObjectG1PreBarrier() throws Throwable {
        doit();
    }

    private void doit() throws Throwable {
        Field field = GetUnsafeObjectG1PreBarrier.class.getField("a");
        long fieldOffset = unsafe.objectFieldOffset(field);

        for (int i = 0; i < N; i++) {
            readField(this, fieldOffset);
        }
    }

    private void readField(Object o, long fieldOffset) {
        unsafe.getReference(o, fieldOffset);
    }
}
