/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8301721
 * @library /test/lib
 * @run main FindSpecialObjectMethod
 * @summary Test findSpecial on Object methods calling from a class or interface.
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

import static java.lang.invoke.MethodType.*;
import static jdk.test.lib.Asserts.*;

public class FindSpecialObjectMethod {
    static class C {
        public static Object test(C o) throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle mh = lookup.findSpecial(Object.class, "toString", methodType(String.class), C.class);
            return mh.invoke(o);
        }

        public String toString() {
            return "C";
        }
    }

    interface I {
        static Object test(I o) throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle mh = lookup.findSpecial(Object.class, "toString", methodType(String.class), I.class);
            return mh.invoke(o);
        }

        static void noAccess() throws Throwable {
            try {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                MethodHandle mh = lookup.findSpecial(String.class, "hashCode", methodType(int.class), I.class);
                throw new RuntimeException("IllegalAccessException not thrown");
            } catch (IllegalAccessException ex) {}
        }
    }

    public static void main(String... args) throws Throwable {
        // Object.toString can be called from invokespecial from within
        // a special caller class C or interface I
        C c = new C();
        I i = new I() {};
        assertEquals(C.test(c), Objects.toIdentityString(c));
        assertEquals(I.test(i), Objects.toIdentityString(i));

        // I has no access to methods in other class besides Object
        I.noAccess();
    }
}
