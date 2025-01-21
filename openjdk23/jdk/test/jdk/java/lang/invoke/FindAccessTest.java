/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/* @test
 * @bug 8139885
 * @run testng/othervm -ea -esa test.java.lang.invoke.FindAccessTest
 */

package test.java.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.*;

/**
 * Tests for Lookup.findClass/accessClass extensions added in JEP 274.
 */
public class FindAccessTest {

    static final Lookup LOOKUP = MethodHandles.lookup();

    @Test
    public static void testFindSpecial() throws Throwable {
        FindSpecial.C c = new FindSpecial.C();
        assertEquals("I1.m", c.m());
        MethodType t = MethodType.methodType(String.class);
        MethodHandle ci1m = LOOKUP.findSpecial(FindSpecial.I1.class, "m", t, FindSpecial.C.class);
        assertEquals("I1.m", (String) ci1m.invoke(c));
    }

    @Test
    public static void testFindSpecialAbstract() throws Throwable {
        FindSpecial.C c = new FindSpecial.C();
        assertEquals("q", c.q());
        MethodType t = MethodType.methodType(String.class);
        boolean caught = false;
        try {
            MethodHandle ci3q = LOOKUP.findSpecial(FindSpecial.I3.class, "q", t, FindSpecial.C.class);
        } catch (Throwable thrown) {
            if (!(thrown instanceof IllegalAccessException) || !FindSpecial.ABSTRACT_ERROR.equals(thrown.getMessage())) {
                throw new AssertionError(thrown.getMessage(), thrown);
            }
            caught = true;
        }
        assertTrue(caught);
    }

    @Test(expectedExceptions = {ClassNotFoundException.class})
    public static void testFindClassCNFE() throws ClassNotFoundException, IllegalAccessException {
        LOOKUP.findClass("does.not.Exist");
    }

    static class FindSpecial {

        interface I1 {
            default String m() {
                return "I1.m";
            }
        }

        interface I2 {
            default String m() {
                return "I2.m";
            }
        }

        interface I3 {
            String q();
        }

        static class C implements I1, I2, I3 {
            public String m() {
                return I1.super.m();
            }
            public String q() {
                return "q";
            }
        }

        static final String ABSTRACT_ERROR = "no such method: test.java.lang.invoke.FindAccessTest$FindSpecial$I3.q()String/invokeSpecial";

    }

}
