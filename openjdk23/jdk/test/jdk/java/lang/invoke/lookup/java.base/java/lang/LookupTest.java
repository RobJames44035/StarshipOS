/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package java.lang;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.InvocationTargetException;

import static java.lang.invoke.MethodType.*;

/*
 * Verify that a Lookup object can be obtained statically from java.base
 * but fails when it's obtained via reflection from java.base.
 */
public class LookupTest {
    public static void main(String... args) throws Throwable {
        // Get a full power lookup
        Lookup lookup1 =  MethodHandles.lookup();
        MethodHandle mh1 = lookup1.findStatic(lookup1.lookupClass(),
                                              "foo",
                                              methodType(String.class));
        assertEquals((String) mh1.invokeExact(), foo());

        // access protected member
        MethodHandle mh2 = lookup1.findVirtual(java.lang.ClassLoader.class,
                                               "getPackage",
                                               methodType(Package.class, String.class));
        ClassLoader loader = ClassLoader.getPlatformClassLoader();
        Package pkg = (Package)mh2.invokeExact(loader, "java.lang");
        assertEquals(pkg.getName(), "java.lang");

        // MethodHandles.lookup will fail if it's called reflectively
        try {
            MethodHandles.class.getMethod("lookup").invoke(null);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof InternalError)) {
                throw e.getCause();
            }
        }
    }

    static String foo() { return "foo!"; }

    static void assertEquals(Object o1, Object o2) {
        if (!o1.equals(o2)) {
            throw new RuntimeException(o1 + " != " + o2);
        }
    }
}
