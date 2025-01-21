/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 6479237
 * @summary Basic test StackTraceElement with class loader names
 * @library lib /lib/testlibrary /test/lib
 * @modules jdk.compiler
 * @build jdk.test.lib.compiler.CompilerUtils
 *        m1/* WithClassLoaderName
 * @run main/othervm m1/com.app.Main
 * @run main/othervm WithClassLoaderName
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.test.lib.compiler.CompilerUtils;

import com.app.Utils;

public class WithClassLoaderName {
    private static final String TEST_SRC = System.getProperty("test.src");
    private static final String SRC_FILENAME = "WithClassLoaderName.java";

    private static final Path SRC_DIR = Paths.get(TEST_SRC, "src");
    private static final Path CLASSES_DIR = Paths.get("classes");
    private static final String THROW_EXCEPTION_CLASS = "p.ThrowException";

    public static void main(String... args) throws Exception {
        /*
         * Test the following frames both have the same class loader name "app"
         *   com.app.Test::test
         *   WithClassLoaderName::test
         */
        Utils.verify(WithClassLoaderName.class, "app", "main", SRC_FILENAME);

        /*
         * Test StackTraceElement for a class loaded by a named URLClassLoader
         */
        compile();
        testURLClassLoader("myloader");

        // loader name same as application class loader
        testURLClassLoader("app");
    }

    private static void compile() throws Exception {
        boolean rc = CompilerUtils.compile(SRC_DIR, CLASSES_DIR);
        if (!rc) {
            throw new RuntimeException("compilation fails");
        }
    }

    public static void testURLClassLoader(String loaderName) throws Exception {
        System.err.println("---- test URLClassLoader name: " + loaderName);

        URL[] urls = new URL[] { CLASSES_DIR.toUri().toURL() };
        ClassLoader parent = ClassLoader.getSystemClassLoader();
        URLClassLoader loader = new URLClassLoader(loaderName, urls, parent);

        Class<?> c = Class.forName(THROW_EXCEPTION_CLASS, true, loader);
        Method method = c.getMethod("throwError");
        try {
            // invoke p.ThrowException::throwError
            method.invoke(null);
        } catch (InvocationTargetException x) {
            Throwable e = x.getCause();
            e.printStackTrace();

            StackTraceElement[] stes = e.getStackTrace();
            StackWalker.StackFrame[] frames = new StackWalker.StackFrame[] {
                Utils.makeStackFrame(c, "throwError", "ThrowException.java"),
                Utils.makeStackFrame(WithClassLoaderName.class, "testURLClassLoader",
                                     SRC_FILENAME),
                Utils.makeStackFrame(WithClassLoaderName.class, "main", SRC_FILENAME),
            };

            // p.ThrowException.throwError
            Utils.checkFrame(loaderName, frames[0], stes[0]);
            // skip reflection frames
            int i = 1;
            while (i < stes.length) {
                String cn = stes[i].getClassName();
                if (!cn.startsWith("java.lang.reflect.") &&
                    !cn.startsWith("jdk.internal.reflect."))
                    break;
                i++;
            }
            // WithClassLoaderName.testURLClassLoader
            Utils.checkFrame("app", frames[1], stes[i]);

            // WithClassLoaderName.main
            Utils.checkFrame("app", frames[2], stes[i+1]);

        }
    }

}

