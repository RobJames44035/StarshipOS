/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @bug 8150782 8207027 8266269
 * @compile TestFindClass.java TestCls.java p/Foo.java q/Bar.java
 * @run testng/othervm -ea -esa test.java.lang.invoke.TestFindClass
 */
package test.java.lang.invoke;

import java.lang.invoke.*;
import p.Foo;
import q.Bar;

import static java.lang.invoke.MethodHandles.*;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.*;

public class TestFindClass {

    private static final String PACKAGE_PREFIX = "test.java.lang.invoke.";

    private static boolean initializedClass1;

    private static class Class1 {
        static {
            initializedClass1 = true;
        }
    }

    @Test
    public void initializerNotRun() throws IllegalAccessException, ClassNotFoundException {
        lookup().findClass(PACKAGE_PREFIX + "TestFindClass$Class1");
        assertFalse(initializedClass1);
    }

    @Test
    public void returnsRequestedClassInSamePackage() throws IllegalAccessException, ClassNotFoundException {
        Class<?> aClass = lookup().findClass(PACKAGE_PREFIX + "TestFindClass$Class1");
        assertEquals(Class1.class, aClass);
    }

    @Test
    public void returnsRequestedArrayClassInSamePackage() throws IllegalAccessException, ClassNotFoundException {
        Class<?> aClass = lookup().findClass("[L" + PACKAGE_PREFIX + "TestFindClass$Class1;");
        assertEquals(Class1[].class, aClass);
    }

    @Test(expectedExceptions = {ClassNotFoundException.class})
    public void classNotFoundExceptionTest() throws IllegalAccessException, ClassNotFoundException {
        lookup().findClass(PACKAGE_PREFIX + "TestFindClass$NonExistent");
    }

    @DataProvider
    Object[][] illegalAccessFind() {
        return new Object[][] {
                {publicLookup(), PACKAGE_PREFIX + "TestFindClass$Class1"},
                {publicLookup(), PACKAGE_PREFIX + "TestCls$PrivateSIC"}
        };
    }

    /**
     * Assertion: @throws IllegalAccessException if the class is not accessible, using the allowed access modes.
     */
    @Test(dataProvider = "illegalAccessFind", expectedExceptions = {ClassNotFoundException.class})
    public void illegalAccessExceptionTest(Lookup lookup, String className) throws IllegalAccessException, ClassNotFoundException {
        lookup.findClass(className);
    }

    @Test
    public void okAccess() throws IllegalAccessException, ClassNotFoundException {
        lookup().findClass(PACKAGE_PREFIX + "TestCls$PrivateSIC");
    }

    /**
     * Verify that a protected Q is as accessible as a public Q during linkage
     * (see JLS 15.12.4.3).
     */
    @Test
    public void protectedInnerClass() throws IllegalAccessException, ClassNotFoundException {
        lookup().findClass("p.Foo$T");
        lookup().findClass("[Lp.Foo$T;");
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void illegalArgument() throws IllegalAccessException, ClassNotFoundException {
        lookup().findClass(null);
    }
}
