/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @summary unit tests for java.lang.invoke.MethodHandles
 * @run testng/othervm -ea -esa test.java.lang.invoke.ConstantIdentityMHTest
 */
package test.java.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
import static org.testng.Assert.*;
import org.testng.annotations.*;

public class ConstantIdentityMHTest {

    @DataProvider(name = "testZeroData")
    private Object[][] testZeroData() {
       return new Object[][] {
           {void.class, "()void"},
           {int.class, "()int"},
           {byte.class, "()byte"},
           {short.class, "()short"},
           {long.class, "()long"},
           {float.class, "()float"},
           {double.class, "()double"},
           {boolean.class, "()boolean"},
           {char.class, "()char"},
           {Integer.class, "()Integer"}
       };
    }

    @Test(dataProvider = "testZeroData")
    public void testZero(Class<?> expectedtype, String expected) throws Throwable {
        assertEquals(MethodHandles.zero(expectedtype).type().toString(), expected);
    }

    @Test(expectedExceptions={ NullPointerException.class })
    public void testZeroNPE() {
        MethodHandle mh = MethodHandles.zero(null);
    }

    @Test
    void testEmpty() throws Throwable {
        MethodHandle cat = lookup().findVirtual(String.class, "concat", methodType(String.class, String.class));
        assertEquals((String)cat.invoke("x","y"), "xy");
        MethodHandle mhEmpty = MethodHandles.empty(cat.type());
        assertEquals((String)mhEmpty.invoke("x","y"), null);
    }

    @Test(expectedExceptions = { NullPointerException.class })
    void testEmptyNPE() {
        MethodHandle lenEmptyMH = MethodHandles.empty(null);
    }
}
