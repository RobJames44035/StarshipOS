/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8067951 8236075
 * @summary Unit test for internal ClassLoaderHelper#parsePath().
 *          Quoted entries should get unquoted on Windows.
 *          Empty entries should be replaced with dot.
 * @library /test/lib
 * @modules java.base/jdk.internal.loader:open
 * @build jdk.test.lib.Platform
 * @run main LibraryPathProperty
 */

import java.lang.reflect.Method;
import java.io.File;
import java.util.Arrays;
import jdk.test.lib.Platform;

public class LibraryPathProperty {

    static final String SP = File.pathSeparator;
    static Method method;

    public static void main(String[] args) throws Throwable {
        Class<?> klass = Class.forName("jdk.internal.loader.ClassLoaderHelper");
        method = klass.getDeclaredMethod("parsePath", String.class);
        method.setAccessible(true);

        test("", ".");
        test(SP, ".", ".");
        test("a" + SP, "a", ".");
        test(SP + "b", ".", "b");
        test("a" + SP + SP + "b", "a", ".", "b");

        if (Platform.isWindows()) {
            // on Windows parts of paths may be quoted
            test("\"\"", ".");
            test("\"\"" + SP, ".", ".");
            test(SP + "\"\"", ".", ".");
            test("a" + SP + "\"b\"" + SP, "a", "b", ".");
            test(SP + "\"a\"" + SP + SP + "b", ".", "a", ".", "b");
            test("\"a\"" + SP + "\"b\"", "a", "b");
            test("\"/a/\"b" + SP + "c", "/a/b", "c");
            test("\"/a;b\"" + SP + "c", "/a;b", "c");
            test("\"/a:b\"" + SP + "c", "/a:b", "c");
            test("\"/a" + SP + "b\"" + SP + "c", "/a" + SP + "b", "c");
            test("/\"a\"\";\"\"b\"" + SP + "\"c\"", "/a;b", "c");
            test("/\"a:\"b" + SP + "c", "/a:b", "c");
        }
    }

    static void test(String s, String... expected) throws Throwable {
        String[] res = (String[])method.invoke(null, s);
        if (!Arrays.asList(res).equals(Arrays.asList(expected))) {
            throw new RuntimeException("Parsing [" + s + "] " +
                    " result " + Arrays.asList(res) +
                    " doesn't match " + Arrays.asList(expected));
        }
    }
}
