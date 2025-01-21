/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/* @test
 * @summary basic tests for MethodHandle.invokeWithArguments
 * @run testng test.java.lang.invoke.InvokeWithArgumentsTest
 */

package test.java.lang.invoke;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.WrongMethodTypeException;

import static java.lang.invoke.MethodType.methodType;

public class InvokeWithArgumentsTest {
    static final MethodHandles.Lookup L = MethodHandles.lookup();

    static Object[] arity(Object o1, Object o2, Object... a) {
        return a;
    }

    @Test
    public void testArity() throws Throwable {
        MethodHandle mh = L.findStatic(L.lookupClass(), "arity",
                                       methodType(Object[].class, Object.class, Object.class, Object[].class));

        try {
            mh.invokeWithArguments("");
            Assert.fail("WrongMethodTypeException expected");
        } catch (WrongMethodTypeException e) {}
    }

    static Object[] passThrough(String... a) {
        return a;
    }

    static Object[] pack(Object o, Object... a) {
        return a;
    }

    @Test
    public void testArrayNoPassThrough() throws Throwable {
        String[] actual = {"A", "B"};

        MethodHandle mh = L.findStatic(L.lookupClass(), "passThrough",
                                       methodType(Object[].class, String[].class));

        // Note: the actual array is not preserved, the elements will be
        // unpacked and then packed into a new array before invoking the method
        String[] expected = (String[]) mh.invokeWithArguments(actual);

        Assert.assertTrue(actual != expected, "Array should not pass through");
        Assert.assertEquals(actual, expected, "Array contents should be equal");
    }

    @Test
    public void testArrayPack() throws Throwable {
        String[] actual = new String[]{"A", "B"};

        MethodHandle mh = L.findStatic(L.lookupClass(), "pack",
                                       methodType(Object[].class, Object.class, Object[].class));

        // Note: since String[] can be cast to Object, the actual String[] array
        // will cast to Object become the single element of a new Object[] array
        Object[] expected = (Object[]) mh.invokeWithArguments("", actual);

        Assert.assertEquals(1, expected.length, "Array should contain just one element");
        Assert.assertTrue(actual == expected[0], "Array should pass through");
    }

    static void intArray(int... a) {
    }

    @Test
    public void testPrimitiveArrayWithNull() throws Throwable {
        MethodHandle mh = L.findStatic(L.lookupClass(), "intArray",
                                       methodType(void.class, int[].class));
        try {
            mh.invokeWithArguments(null, null);
            Assert.fail("NullPointerException expected");
        } catch (NullPointerException e) {}
    }

    @Test
    public void testPrimitiveArrayWithRef() throws Throwable {
        MethodHandle mh = L.findStatic(L.lookupClass(), "intArray",
                                       methodType(void.class, int[].class));
        try {
            mh.invokeWithArguments("A", "B");
            Assert.fail("ClassCastException expected");
        } catch (ClassCastException e) {}
    }


    static void numberArray(Number... a) {
    }

    @Test
    public void testRefArrayWithCast() throws Throwable {
        MethodHandle mh = L.findStatic(L.lookupClass(), "numberArray",
                                       methodType(void.class, Number[].class));
        // All numbers, should not throw
        mh.invokeWithArguments(1, 1.0, 1.0F, 1L);

        try {
            mh.invokeWithArguments("A");
            Assert.fail("ClassCastException expected");
        } catch (ClassCastException e) {}
    }
}
