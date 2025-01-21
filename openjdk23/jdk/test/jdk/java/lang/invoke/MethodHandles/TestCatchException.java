/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 8027823
 * @run junit test.java.lang.invoke.TestCatchException
 */
package test.java.lang.invoke;

import java.lang.invoke.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TestCatchException {
    static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    static final MethodType M_TYPE = MethodType.methodType(int.class, Object.class, Object.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class);

    private static int noThrow(Object o1, Object o2, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
        return 42;
    }

    private static int throwEx(Object o1, Object o2, int i1, int i2, int i3, int i4, int i5, int i6, int i7) throws Exception {
        throw new Exception();
    }

    private static int handler(Exception e) {
        return 17;
    }

    @Test
    public void testNoThrowPath() throws Throwable {
        MethodHandle target = LOOKUP.findStatic(TestCatchException.class, "noThrow", M_TYPE);
        MethodHandle handler = LOOKUP.findStatic(TestCatchException.class, "handler", MethodType.methodType(int.class, Exception.class));

        MethodHandle h = MethodHandles.catchException(target, Exception.class, handler);

        int x = (int)h.invokeExact(new Object(), new Object(), 1, 2, 3, 4, 5, 6, 7);
        assertEquals(x, 42);
    }

    @Test
    public void testThrowPath() throws Throwable {
        MethodHandle target = LOOKUP.findStatic(TestCatchException.class, "throwEx", M_TYPE);
        MethodHandle handler = LOOKUP.findStatic(TestCatchException.class, "handler", MethodType.methodType(int.class, Exception.class));

        MethodHandle h = MethodHandles.catchException(target, Exception.class, handler);

        int x = (int)h.invokeExact(new Object(), new Object(), 1, 2, 3, 4, 5, 6, 7);
        assertEquals(x, 17);
    }

    static final Object masterParam = new Object();
    static final Object[] masterTail = new Object[] { "str" };
    static Exception masterEx = new Exception();

    public static Object m1(Object o1, Object o2, Object o3, Object o4, Object o5,
                            Object o6, Object o7, Object o8, Object... tail) {
        assertEquals(masterParam, o1);
        assertEquals(masterParam, o2);
        assertEquals(masterParam, o3);
        assertEquals(masterParam, o4);
        assertEquals(masterParam, o5);
        assertEquals(masterParam, o6);
        assertEquals(masterParam, o7);
        assertEquals(masterParam, o8);
        assertEquals(masterTail, tail);
        return tail;
    }

    public static Object m2(Exception e, Object o1, Object o2, Object o3, Object o4,
                            Object o5, Object o6, Object o7, Object o8, Object... tail) {
        assertEquals(masterEx, e);
        assertEquals(masterParam, o1);
        assertEquals(masterParam, o2);
        assertEquals(masterParam, o3);
        assertEquals(masterParam, o4);
        assertEquals(masterParam, o5);
        assertEquals(masterParam, o6);
        assertEquals(masterParam, o7);
        assertEquals(masterParam, o8);
        assertEquals(masterTail, tail);
        return tail;
    }

    public static Object throwEx(Object o1, Object o2, Object o3, Object o4, Object o5,
                                 Object o6, Object o7, Object o8, Object... tail) throws Exception {
        assertEquals(masterParam, o1);
        assertEquals(masterParam, o2);
        assertEquals(masterParam, o3);
        assertEquals(masterParam, o4);
        assertEquals(masterParam, o5);
        assertEquals(masterParam, o6);
        assertEquals(masterParam, o7);
        assertEquals(masterParam, o8);
        assertEquals(masterTail, tail);
        throw masterEx;
    }

    @Test
    public void testVarargsCollectorNoThrow() throws Throwable {
        MethodType t1 = MethodType.methodType(Object.class, Object.class, Object.class, Object.class, Object.class,
                Object.class, Object.class, Object.class, Object.class, Object[].class);

        MethodType t2 = t1.insertParameterTypes(0, Exception.class);

        MethodHandle target = LOOKUP.findStatic(TestCatchException.class, "m1", t1)
                                    .asVarargsCollector(Object[].class);
        MethodHandle catcher = LOOKUP.findStatic(TestCatchException.class, "m2", t2)
                                     .asVarargsCollector(Object[].class);
        MethodHandle gwc = MethodHandles.catchException(target, Exception.class, catcher);

        Object o = masterParam;
        Object[] obj1 = masterTail;

        Object r2 = gwc.invokeExact(o, o, o, o, o, o, o, o, obj1);
        assertEquals(r2, obj1);
    }

    @Test
    public void testVarargsCollectorThrow() throws Throwable {
        MethodType t1 = MethodType.methodType(Object.class, Object.class, Object.class, Object.class, Object.class,
                Object.class, Object.class, Object.class, Object.class, Object[].class);

        MethodType t2 = t1.insertParameterTypes(0, Exception.class);

        MethodHandle target = LOOKUP.findStatic(TestCatchException.class, "throwEx", t1)
                                    .asVarargsCollector(Object[].class);
        MethodHandle catcher = LOOKUP.findStatic(TestCatchException.class, "m2", t2)
                                     .asVarargsCollector(Object[].class);
        MethodHandle gwc = MethodHandles.catchException(target, Exception.class, catcher);

        Object o = masterParam;
        Object[] obj1 = masterTail;

        Object r2 = gwc.invokeExact(o, o, o, o, o, o, o, o, obj1);
        assertEquals(r2, obj1);
    }

    public static void main(String[] args) throws Throwable {
        TestCatchException test = new TestCatchException();
        test.testNoThrowPath();
        test.testThrowPath();
        test.testVarargsCollectorNoThrow();
        test.testVarargsCollectorThrow();
        System.out.println("TEST PASSED");
    }
}
