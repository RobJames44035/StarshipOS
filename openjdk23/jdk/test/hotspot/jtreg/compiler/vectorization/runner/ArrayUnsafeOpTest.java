/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Vectorization test on array unsafe operations
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 *        compiler.vectorization.runner.VectorizationTestRunner
 *
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:.
 *                   -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI
 *                   compiler.vectorization.runner.ArrayUnsafeOpTest
 *
 * @requires vm.compiler2.enabled
 */

package compiler.vectorization.runner;

import compiler.lib.ir_framework.*;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class ArrayUnsafeOpTest extends VectorizationTestRunner {

    private static final int SIZE = 543;

    private static Unsafe unsafe;

    public ArrayUnsafeOpTest() throws Exception {
        Class klass = Unsafe.class;
        Field field = klass.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        unsafe = (Unsafe) field.get(null);
    }

    @Test
    public byte[] arrayUnsafeFill() {
        byte[] res = new byte[SIZE];
        for (int i = 0; i < 500; i++) {
            unsafe.putByte(res, i + 24, (byte) i);
        }
        return res;
    }

    @Test
    public byte[] arrayUnsafeFillWithOneAddp() {
        byte[] res = new byte[SIZE];
        for (int i = 123; i < 500; i++) {
            unsafe.putByte(res, i, (byte) i);
        }
        return res;
    }

    @Test
    // Note that this case cannot be vectorized since data dependence
    // exists between two unsafe stores of different types on the same
    // array reference.
    public int[] arrayUnsafeFillTypeMismatch() {
        int[] res = new int[SIZE];
        for (int i = 0; i < 500; i++) {
            unsafe.putByte(res, i + 24, (byte) i);
            unsafe.putShort(res, i + 28, (short) 0);
        }
        return res;
    }

    @Test
    // Note that this case cannot be vectorized since data dependence
    // exists between adjacent iterations. (The memory address storing
    // an int array is not increased by 4 per iteration.)
    @IR(failOn = {IRNode.STORE_VECTOR})
    public int[] arrayUnsafeFillAddrIncrMismatch() {
        int[] res = new int[SIZE];
        for (int i = 0; i < 500; i++) {
            unsafe.putInt(res, i + 24, i);
        }
        return res;
    }
}
