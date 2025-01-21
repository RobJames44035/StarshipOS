/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @run testng/othervm -ea -esa test.java.lang.invoke.ArrayLengthTest
 */
package test.java.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.*;

public class ArrayLengthTest {

    @DataProvider
    Object[][] arrayClasses() {
        return new Object[][] {
                {int[].class},
                {long[].class},
                {float[].class},
                {double[].class},
                {boolean[].class},
                {byte[].class},
                {short[].class},
                {char[].class},
                {Object[].class},
                {StringBuffer[].class}
        };
    }

    @Test(dataProvider = "arrayClasses")
    public void testArrayLength(Class<?> arrayClass) throws Throwable {
        MethodHandle arrayLength = MethodHandles.arrayLength(arrayClass);
        assertEquals(int.class, arrayLength.type().returnType());
        assertEquals(arrayClass, arrayLength.type().parameterType(0));
        Object array = MethodHandles.arrayConstructor(arrayClass).invoke(10);
        assertEquals(10, arrayLength.invoke(array));
    }

    @Test(dataProvider = "arrayClasses", expectedExceptions = NullPointerException.class)
    public void testArrayLengthInvokeNPE(Class<?> arrayClass) throws Throwable {
        MethodHandle arrayLength = MethodHandles.arrayLength(arrayClass);
        arrayLength.invoke(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testArrayLengthNoArray() {
        MethodHandles.arrayLength(String.class);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testArrayLengthNPE() {
        MethodHandles.arrayLength(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullReference() throws Throwable {
        MethodHandle arrayLength = MethodHandles.arrayLength(String[].class);
        int len = (int)arrayLength.invokeExact((String[])null);
    }
}
