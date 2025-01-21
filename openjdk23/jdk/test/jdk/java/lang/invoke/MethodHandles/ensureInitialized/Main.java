/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @test
 * @bug 8235521
 * @summary Tests for Lookup::ensureClassInitialized
 * @build java.base/* m1/* m2/* Main
 * @run testng/othervm --add-modules m1 Main
 */

public class Main {
    // Test access to public java.lang class
    @Test
    public void testPublic() throws Exception {
        assertFalse(Helper.isInitialized(PublicInit.class));
        MethodHandles.lookup().ensureInitialized(PublicInit.class);
        assertTrue(Helper.isInitialized(PublicInit.class));
        // no-op if already initialized
        MethodHandles.lookup().ensureInitialized(PublicInit.class);
    }

    // access denied to package-private java.lang class
    @Test(expectedExceptions = { IllegalAccessException.class })
    public void testPackagePrivate() throws Exception {
        Class<?> c = Class.forName("java.lang.DefaultInit", false, null);
        assertFalse(Helper.isInitialized(c));
        // access denied
        MethodHandles.lookup().ensureInitialized(c);
    }

    // access denied to public class in a non-exported package
    @Test(expectedExceptions = { IllegalAccessException.class })
    public void testNonExportedPackage() throws Exception {
        Class<?> c = Class.forName("jdk.internal.misc.VM", false, null);
        // access denied
        MethodHandles.lookup().ensureInitialized(c);
    }

    // invoke p1.Test::test to test module boundary access
    @Test
    public void testModuleAccess() throws Exception {
        Class<?> c = Class.forName("p1.Test");
        Method m = c.getMethod("test");
        m.invoke(null);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void testArrayType() throws Exception {
        Class<?> arrayType = PublicInit.class.arrayType();
        MethodHandles.lookup().ensureInitialized(arrayType);
    }
}
