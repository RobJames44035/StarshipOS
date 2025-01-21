/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @summary define a lambda proxy class whose target class has an invalid
 *          nest membership
 * @run testng/othervm p.LambdaNestedInnerTest
 */

package p;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.testng.Assert.*;

public class LambdaNestedInnerTest {
    private static final String INNER_CLASSNAME = "p.LambdaNestedInnerTest$Inner";
    private static final String DIR = "missingOuter";
    public static class Inner implements Runnable {
        // generate lambda proxy class
        private Runnable lambda1 = this::doit;

        @Override
        public void run() {
            // validate the lambda proxy class
            Class<?> lambdaProxyClass = lambda1.getClass();
            assertTrue(lambdaProxyClass.isHidden());
            System.out.format("%s nest host %s nestmate of Inner class %s%n",
                    lambdaProxyClass, lambdaProxyClass.getNestHost(),
                    lambdaProxyClass.isNestmateOf(Inner.class));
            assertTrue(lambdaProxyClass.getNestHost() == Inner.class.getNestHost());
            assertTrue(Arrays.equals(lambdaProxyClass.getNestMembers(), Inner.class.getNestMembers()));
            assertTrue(lambdaProxyClass.isNestmateOf(Inner.class));
            lambda1.run();
        }

        // testng may not be visible to this class
        private static void assertTrue(boolean x) {
            if (!x) {
                throw new AssertionError("expected true but found false");
            }
        }

        private void doit() {
        }
    }

    @BeforeTest
    public void setup() throws IOException {
        String filename = INNER_CLASSNAME.replace('.', File.separatorChar) + ".class";
        Path src = Paths.get(System.getProperty("test.classes"), filename);
        Path dest = Paths.get(DIR, filename);
        Files.createDirectories(dest.getParent());
        Files.copy(src, dest, REPLACE_EXISTING);
    }

    @Test
    public void test() throws Exception {
        Class<?> inner = Class.forName(INNER_CLASSNAME);
        // inner class is a nest member of LambdaNestedInnerTest
        Class<?> nestHost = inner.getNestHost();
        assertTrue(nestHost == LambdaNestedInnerTest.class);
        Set<Class<?>> members = Arrays.stream(nestHost.getNestMembers()).collect(Collectors.toSet());
        assertEquals(members, Set.of(nestHost, inner, TestLoader.class));

        // spin lambda proxy hidden class
        Runnable runnable = (Runnable) inner.newInstance();
        runnable.run();
    }

    @Test
    public void nestHostNotExist() throws Exception {
        URL[] urls = new URL[] { Paths.get(DIR).toUri().toURL() };
        URLClassLoader loader = new URLClassLoader(urls, null);
        Class<?> inner = loader.loadClass(INNER_CLASSNAME);
        assertTrue(inner.getClassLoader() == loader);
        assertTrue(inner.getNestHost() == inner);   // linkage error ignored

        Runnable runnable = (Runnable) inner.newInstance();
        // this validates the lambda proxy class
        runnable.run();
    }

    /*
     * Tests IncompatibleClassChangeError thrown since the true nest host is not
     * in the same runtime package as the hidden class
     */
    @Test
    public void nestHostNotSamePackage() throws Exception {
        URL[] urls = new URL[] { Paths.get(DIR).toUri().toURL() };
        TestLoader loader = new TestLoader(urls);

        Class<?> inner = loader.loadClass(INNER_CLASSNAME);
        assertTrue(inner.getClassLoader() == loader);
        assertTrue(inner.getNestHost() == inner);   // linkage error ignored.

        Runnable runnable = (Runnable) inner.newInstance();
        // this validates the lambda proxy class
        runnable.run();
    }

    static class TestLoader extends URLClassLoader {
        TestLoader(URL[] urls) {
            super(urls, TestLoader.class.getClassLoader());
        }
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (INNER_CLASSNAME.equals(name)) {
                return findClass(name);
            } else {
                // delegate to its parent
                return loadClass(name, false);
            }
        }
    }
}
