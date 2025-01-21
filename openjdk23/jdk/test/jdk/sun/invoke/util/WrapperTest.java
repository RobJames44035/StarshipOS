/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package test.sun.invoke.util;

import sun.invoke.util.ValueConversions;
import sun.invoke.util.Wrapper;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandle;
import java.io.Serializable;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/* @test
 * @summary unit tests to assert Wrapper zero identities and conversion behave correctly
 * @modules java.base/sun.invoke.util
 * @compile -XDignore.symbol.file WrapperTest.java
 * @run junit test.sun.invoke.util.WrapperTest
 */
public class WrapperTest {

    @Test
    public void testShortZeroConversion() throws Throwable {
        MethodHandle h1 = MethodHandles.constant(Short.class, (short)42);
        MethodHandle h2 = h1.asType(MethodType.methodType(void.class));  // drop 42
        MethodHandle h3 = h2.asType(MethodType.methodType(short.class));  // add 0
        MethodHandle h4 = h3.asType(MethodType.methodType(Object.class));  // box

        Object x = h4.invokeExact();
        assertEquals(x, (short)0);
        assertTrue(x == Short.valueOf((short)0));
        assertTrue(x == Wrapper.SHORT.zero());
    }

    @Test
    public void testIntZeroConversion() throws Throwable {
        MethodHandle h1 = MethodHandles.constant(Integer.class, 42);
        MethodHandle h2 = h1.asType(MethodType.methodType(void.class));  // drop 42
        MethodHandle h3 = h2.asType(MethodType.methodType(int.class));  // add 0
        MethodHandle h4 = h3.asType(MethodType.methodType(Object.class));  // box

        Object x = h4.invokeExact();
        assertEquals(x, 0);
        assertTrue(x == Integer.valueOf(0));
        assertTrue(x == Wrapper.INT.zero());
    }

    @Test
    public void testLongZeroConversion() throws Throwable {
        MethodHandle h1 = MethodHandles.constant(Long.class, 42L);
        MethodHandle h2 = h1.asType(MethodType.methodType(void.class));  // drop 42
        MethodHandle h3 = h2.asType(MethodType.methodType(long.class));  // add 0
        MethodHandle h4 = h3.asType(MethodType.methodType(Object.class));  // box

        Object x = h4.invokeExact();
        assertEquals(x, 0L);
        assertTrue(x == Long.valueOf(0));
        assertTrue(x == Wrapper.LONG.zero());
    }

    @Test
    public void testByteZeroConversion() throws Throwable {
        MethodHandle h1 = MethodHandles.constant(Byte.class, (byte)42);
        MethodHandle h2 = h1.asType(MethodType.methodType(void.class));  // drop 42
        MethodHandle h3 = h2.asType(MethodType.methodType(byte.class));  // add 0
        MethodHandle h4 = h3.asType(MethodType.methodType(Object.class));  // box

        Object x = h4.invokeExact();
        assertEquals(x, (byte)0);
        assertTrue(x == Byte.valueOf((byte)0));
        assertTrue(x == Wrapper.BYTE.zero());
    }

    @Test
    public void testCharacterZeroConversion() throws Throwable {
        MethodHandle h1 = MethodHandles.constant(Character.class, (char)42);
        MethodHandle h2 = h1.asType(MethodType.methodType(void.class));  // drop 42
        MethodHandle h3 = h2.asType(MethodType.methodType(char.class));  // add 0
        MethodHandle h4 = h3.asType(MethodType.methodType(Object.class));  // box

        Object x = h4.invokeExact();
        assertEquals(x, (char)0);
        assertTrue(x == Character.valueOf((char)0));
        assertTrue(x == Wrapper.CHAR.zero());
    }
}
