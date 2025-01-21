/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Basic test for Class.getPackage
 * @compile Foo.java
 * @run testng GetPackageTest
 */

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.testng.Assert.*;

public class GetPackageTest {
    private static Class<?> fooClass; // definePackage is not called for Foo class

    @BeforeTest
    public static void loadFooClass() throws ClassNotFoundException {
        TestClassLoader loader = new TestClassLoader();
        fooClass = loader.loadClass("foo.Foo");
        assertEquals(fooClass.getClassLoader(), loader);
    }

    @DataProvider(name = "testClasses")
    public Object[][] testClasses() {
        return new Object[][] {
                // primitive type, void, array types
                { int.class,            null },
                { long[].class,         null },
                { Object[][].class,     null },
                { void.class,           null },

                // unnamed package
                { GetPackageTest.class, "" },

                // named package
                { fooClass,             "foo" },
                { Object.class,         "java.lang" },
                { Properties.class,     "java.util" },
                { BigInteger.class,     "java.math" },
                { Test.class,           "org.testng.annotations" },
        };
    }

    @Test(dataProvider = "testClasses")
    public void testGetPackage(Class<?> type, String expected) {
        Package p = type.getPackage();
        if (expected == null) {
            assertTrue(p == null);
        } else {
            assertEquals(p.getName(), expected);
        }
    }

    static class TestClassLoader extends ClassLoader {
        public TestClassLoader() {
            super();
        }

        public TestClassLoader(ClassLoader parent) {
            super(parent);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Path p = Paths.get(System.getProperty("test.classes", "."));

            try {
                byte[] bb = Files.readAllBytes(p.resolve("foo/Foo.class"));
                return defineClass(name, bb, 0, bb.length);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        @Override
        protected Class<?> loadClass(String cn, boolean resolve) throws ClassNotFoundException {
            if (!cn.equals("foo.Foo"))
                return super.loadClass(cn, resolve);
            return findClass(cn);
        }

    }
}


