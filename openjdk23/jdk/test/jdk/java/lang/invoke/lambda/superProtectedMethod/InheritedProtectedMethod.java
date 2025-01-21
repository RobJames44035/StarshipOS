/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8239384
 * @modules jdk.compiler
 * @library /test/lib
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.compiler.CompilerUtils
 * @run testng/othervm InheritedProtectedMethod
 * @summary Test method reference to a method inherited from its
 *          superclass in a different package.  Such method's modifier
 *          is changed from public to protected.
 */

import jdk.test.lib.compiler.CompilerUtils;
import jdk.test.lib.Utils;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.*;

public class InheritedProtectedMethod {
    private static final Path SRC_DIR = Paths.get(Utils.TEST_SRC, "src");
    private static final Path CLASSES_DIR = Paths.get("classes");

    @BeforeTest
    static void setup() throws IOException {
        assertTrue(CompilerUtils.compile(SRC_DIR, CLASSES_DIR));

        // compile the modified version of MethodSupplierOuter.java
        Path file = Paths.get(Utils.TEST_SRC, "modified", "MethodSupplierOuter.java");
        assertTrue(CompilerUtils.compile(file, CLASSES_DIR));
    }

    @Test
    public static void run() throws Exception {
        URLClassLoader loader = new URLClassLoader("loader", new URL[]{ CLASSES_DIR.toUri().toURL()},
                ClassLoader.getPlatformClassLoader());
        Class<?> methodInvokeClass = Class.forName("MethodInvoker", false, loader);
        Method invokeMethod = methodInvokeClass.getMethod("invoke");

        String result = (String)invokeMethod.invoke(null);
        assertEquals(result, "protected inherited method");
    }
}
