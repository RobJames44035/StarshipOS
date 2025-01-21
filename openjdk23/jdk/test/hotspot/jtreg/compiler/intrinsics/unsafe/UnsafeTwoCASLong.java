/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8143930
 * @summary C1 LinearScan asserts when compiling two back-to-back CompareAndSwapLongs
 * @modules java.base/jdk.internal.misc:+open
 *
 * @run testng/othervm -Diters=200000 -XX:TieredStopAtLevel=1
 *      compiler.intrinsics.unsafe.UnsafeTwoCASLong
 */

package compiler.intrinsics.unsafe;

import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.Assert.*;

public class UnsafeTwoCASLong {
    static final int ITERS = Integer.getInteger("iters", 1);
    static final jdk.internal.misc.Unsafe UNSAFE;
    static final long V_OFFSET;

    static {
        try {
            Field f = jdk.internal.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (jdk.internal.misc.Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get Unsafe instance.", e);
        }

        try {
            Field vField = UnsafeTwoCASLong.class.getDeclaredField("v");
            V_OFFSET = UNSAFE.objectFieldOffset(vField);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    long v;

    @Test
    public void testFieldInstance() {
        UnsafeTwoCASLong t = new UnsafeTwoCASLong();
        for (int c = 0; c < ITERS; c++) {
            testAccess(t, V_OFFSET);
        }
    }

    static void testAccess(Object base, long offset) {
        UNSAFE.compareAndSetLong(base, offset, 1L, 2L);
        UNSAFE.compareAndSetLong(base, offset, 2L, 1L);
    }

}
