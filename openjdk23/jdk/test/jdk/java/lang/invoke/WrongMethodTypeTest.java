/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/* @test 8299183
 * @run testng WrongMethodTypeTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.VarHandle;
import java.lang.invoke.WrongMethodTypeException;

import static java.lang.invoke.MethodType.methodType;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.*;

public class WrongMethodTypeTest {
    static final Lookup LOOKUP = MethodHandles.lookup();

    @Test
    public void checkExactType() throws Throwable {
        String expectedMessage = "handle's method type (int)int but found ()boolean";
        try {
            MethodHandle mh = LOOKUP.findStatic(WrongMethodTypeTest.class, "m", methodType(int.class, int.class));
            boolean b = (boolean)mh.invokeExact();
            fail("Expected WrongMethodTypeException");
        } catch (WrongMethodTypeException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }

    @Test
    public void checkAccessModeInvokeExact() throws Throwable {
        String expectedMessage = "handle's method type ()int but found ()Void";
        VarHandle vh = LOOKUP.findStaticVarHandle(WrongMethodTypeTest.class, "x", int.class)
                             .withInvokeExactBehavior();
        try {
            Void o = (Void) vh.get();
        } catch (WrongMethodTypeException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }

    @Test
    public void checkVarHandleInvokeExact() throws Throwable {
        String expectedMessage = "handle's method type (WrongMethodTypeTest)boolean but found (WrongMethodTypeTest)int";
        VarHandle vh = LOOKUP.findVarHandle(WrongMethodTypeTest.class, "y", boolean.class)
                             .withInvokeExactBehavior();
        try {
            int o = (int) vh.get(new WrongMethodTypeTest());
        } catch (WrongMethodTypeException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }

    static int m(int x) {
        return x;
    }

    static int x = 200;
    boolean y = false;
}
