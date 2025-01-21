/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package p1;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.stream.Collectors;

import p2.TestIntf;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class Main {
    public interface A {
        default String aConcat(Object... objs) { return Arrays.deepToString(objs); }
    }

    public interface B {
        default String bConcat(Object[] objs) { return Arrays.deepToString(objs); }
    }

    public interface C extends A, B {
        String c(Object... objs);
    }

    private static String concat(Object... objs) {
        return Arrays.stream(objs).map(Object::toString).collect(Collectors.joining());
    }

    /*
     * Test the invocation of default methods with varargs
     */
    @Test
    public static void testVarargsMethods() throws Throwable {
        MethodHandle target = MethodHandles.lookup().findStatic(Main.class,
                "concat", MethodType.methodType(String.class, Object[].class));
        C proxy = MethodHandleProxies.asInterfaceInstance(C.class, target);

        assertEquals(proxy.c("a", "b", "c"), "abc");
        assertEquals(proxy.aConcat("a", "b", "c"), "[a, b, c]");
        assertEquals(proxy.aConcat(new Object[] { "a", "b", "c" }), "[a, b, c]");
        assertEquals(proxy.bConcat(new Object[] { "a", "b", "c" }), "[a, b, c]");
    }

    /*
     * Test the invocation of a default method of an accessible interface
     */
    @Test
    public static void modulePrivateInterface() {
        MethodHandle target = MethodHandles.constant(String.class, "test");
        TestIntf t = MethodHandleProxies.asInterfaceInstance(TestIntf.class, target);
        assertEquals(t.test(), "test");
    }
}
