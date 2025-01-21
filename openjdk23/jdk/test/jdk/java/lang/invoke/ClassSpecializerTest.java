/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/* @test
 * @summary Smoke-test class specializer, used to create BoundMethodHandle classes
 * @compile/module=java.base java/lang/invoke/ClassSpecializerHelper.java
 * @run testng/othervm/timeout=250 -ea -esa ClassSpecializerTest
 */

// Useful diagnostics to try:
//   -Djava.lang.invoke.MethodHandle.TRACE_RESOLVE=true
//   -Djava.lang.invoke.MethodHandle.DUMP_CLASS_FILES=true


import org.testng.annotations.*;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.invoke.ClassSpecializerHelper.*;


public class ClassSpecializerTest {
    @Test
    public void testFindSpecies() throws Throwable {
        System.out.println("testFindSpecies");
        System.out.println("test = " + SPEC_TEST);
        ArrayList<Object> args = new ArrayList<>();
        for (int key = 0; key <= Kind.MAX_KEY; key++) {
            Kind k = SpecTest.kind(key);
            System.out.println("k = " + k);
            MethodHandle mh = k.factory();
            System.out.println("k.f = " + mh);
            args.clear();
            for (Class<?> pt : mh.type().parameterList()) {
                args.add(coughUpA(pt));
            }
            args.set(0, key * 1000 + 42);
            Frob f = (Frob) mh.invokeWithArguments(args.toArray());
            assert(f.kind() == k);
            System.out.println("k.f(...) = " + f.toString());
            List<Object> l = f.asList();
            System.out.println("f.l = " + l);
            args.subList(0,1).clear();  // drop label
            assert(args.equals(l));
        }
    }
    private static Object coughUpA(Class<?> pt) throws Throwable {
        if (pt == String.class)  return "foo";
        if (pt.isArray()) return java.lang.reflect.Array.newInstance(pt.getComponentType(), 2);
        if (pt == Integer.class)  return 42;
        if (pt == Double.class)  return 3.14;
        if (pt.isAssignableFrom(List.class))
            return Arrays.asList("hello", "world", "from", pt.getSimpleName());
        return MethodHandles.zero(pt).invoke();
    }
    public static void main(String... av) throws Throwable {
        System.out.println("TEST: ClassSpecializerTest");
        new ClassSpecializerTest().testFindSpecies();
    }
}
