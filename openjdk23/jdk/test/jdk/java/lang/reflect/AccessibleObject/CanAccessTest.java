/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @build CanAccessTest
 * @modules java.base/jdk.internal.misc:+open
 * @run testng/othervm CanAccessTest
 * @summary Test AccessibleObject::canAccess method
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.SecureClassLoader;

import jdk.internal.misc.Unsafe;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test
public class CanAccessTest {
    private static Unsafe INSTANCE = Unsafe.getUnsafe();

    /**
     * null object parameter for Constructor
     */
    public void testConstructor() throws Exception {
        Constructor<?> ctor = Unsafe.class.getDeclaredConstructor();
        assertFalse(ctor.canAccess(null));
        assertTrue(ctor.trySetAccessible());

        try {
            // non-null object parameter
            ctor.canAccess(INSTANCE);
            assertTrue(false);
        } catch (IllegalArgumentException expected) {}
    }

    /**
     * Test protected constructors
     */
    public void testProtectedConstructor() throws Exception {
        TestLoader.testProtectedConstructorNonOpenedPackage();

        Constructor<?> ctor = TestLoader.class.getDeclaredConstructor();
        assertTrue(ctor.canAccess(null));
    }

    /**
     * null object parameter  for static members
     */
    public void testStaticMember() throws Exception {
        Method m = Unsafe.class.getDeclaredMethod("throwIllegalAccessError");
        assertFalse(m.canAccess(null));
        assertTrue(m.trySetAccessible());

        try {
            // non-null object parameter
            m.canAccess(INSTANCE);
            assertTrue(false);
        } catch (IllegalArgumentException expected) { }
    }

    /**
     * Test protected static
     */
    public void testProtectedStatic() throws Exception {
        Method m = TestLoader.testProtectedStatic();
        assertFalse(m.canAccess(null));
    }

    /**
     * the specified object must be an instance of the declaring class
     * for instance members
     */
    public void testInstanceMethod() throws Exception {
        Method m = Unsafe.class.getDeclaredMethod("allocateMemory0", long.class);
        assertFalse(m.canAccess(INSTANCE));

        try {
            m.canAccess(null);
            assertTrue(false);
        } catch (IllegalArgumentException expected) { }
    }

    /**
     * the specified object must be an instance of the declaring class
     * for instance members
     */
    public void testInvalidInstanceObject() throws Exception {
        Class<?> clazz = Class.forName("sun.security.x509.X500Name");
        Method m = clazz.getDeclaredMethod("size");

        try {
            m.canAccess(INSTANCE);
            assertTrue(false);
        } catch (IllegalArgumentException expected) { }
    }


    static class TestLoader extends SecureClassLoader {
        public static Method testProtectedStatic() throws Exception {
            Method m = ClassLoader.class.getDeclaredMethod("registerAsParallelCapable");
            assertTrue(m.canAccess(null));
            return m;
        }

        protected TestLoader() throws Exception {
            Constructor<?> ctor = SecureClassLoader.class.getDeclaredConstructor();
            assertFalse(ctor.canAccess(null));
            assertFalse(ctor.trySetAccessible());
        }

        public static void testProtectedConstructorNonOpenedPackage() throws Exception {
            new TestLoader();
        }
    }
}
