/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8222791
 * @summary Order of evaluation of -link params in Javadoc tool reversed:
 *          regression with split packages
 * @library /tools/lib ../../lib
 * @modules
 *      jdk.javadoc/jdk.javadoc.internal.api
 *      jdk.javadoc/jdk.javadoc.internal.tool
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.JavacTask toolbox.JavadocTask toolbox.ToolBox
 * @build javadoc.tester.*
 * @run main TestOptionOrder
 */

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import toolbox.JavacTask;
import toolbox.JavadocTask;
import toolbox.Task;
import toolbox.ToolBox;

import javadoc.tester.JavadocTester;

public class TestOptionOrder extends JavadocTester {
    final ToolBox tb;

    public static void main(String... args) throws Exception {
        var tester = new TestOptionOrder();
        tester.runTests();
    }

    TestOptionOrder() throws Exception {
        tb = new ToolBox();
    }

    enum Kind { PACKAGE_LIST, ELEMENT_LIST };

    @Test
    public void testLib1Lib2PackageList(Path base) throws Exception {
        test(base, "lib1", "lib2", Kind.PACKAGE_LIST);
    }

    @Test
    public void testLib1Lib2ElementList(Path base) throws Exception {
        test(base, "lib1", "lib2", Kind.ELEMENT_LIST);
    }

    @Test
    public void testLib2Lib1PackageList(Path base) throws Exception {
        test(base, "lib2", "lib1", Kind.PACKAGE_LIST);
    }

    @Test
    public void testLib2Lib1ElementList(Path base) throws Exception {
        test(base, "lib2", "lib1", Kind.ELEMENT_LIST);
    }

    private void test(Path base, String first, String second, Kind kind) throws Exception {
        createLib(base, first, kind);
        createLib(base, second, kind);

        Path src = base.resolve("src");

        tb.writeJavaFiles(src,
            """
                package app;
                /** Lorem ipsum.
                 *  @see lib.LibClass
                 */
                public class App {
                    /** Reference to LibClass. */
                    public lib.LibClass lc;
                }
                """);

        javadoc("-d", base.resolve("out").toString(),
                "-classpath",
                    base.resolve(first).resolve("classes")
                    + File.pathSeparator
                    + base.resolve(second).resolve("classes"),
                "-linkoffline",
                    "http://example.com/" + first,
                    base.resolve(first).resolve("api").toString(),
                "-linkoffline",
                    "http://example.com/" + second,
                    base.resolve(second).resolve("api").toString(),
                "-sourcepath", src.toString(),
                "app");

         checkOrder("app/App.html",
                // Instance in See Also
                "<li><a href=\"http://example.com/" + first + "/lib/LibClass.html",
                // Instance in Field declaration
                """
                    <div class="col-first even-row-color"><code><a href="http://example.com/""" + first + "/lib/LibClass.html"
                );
    }

    private void createLib(Path base, String name, Kind kind) throws Exception {
        Path libBase = Files.createDirectories(base.resolve(name));
        Path libSrc = libBase.resolve("src");

        tb.writeJavaFiles(libSrc,
            "package lib;\n"
            + "/** Library " + name + ".*/\n"
            + "public class LibClass { }\n");

        new JavacTask(tb)
            .outdir(Files.createDirectories(libBase.resolve("classes")))
            .files(tb.findJavaFiles(libSrc))
            .run(Task.Expect.SUCCESS);

        Path libApi = libBase.resolve("api");
        new JavadocTask(tb)
            .sourcepath(libSrc)
            .outdir(Files.createDirectories(libBase.resolve("api")))
            .options("lib")
            .run(Task.Expect.SUCCESS);

        if (kind == Kind.PACKAGE_LIST) {
            Path elementList = libApi.resolve("element-list");
            Path packageList = libApi.resolve("package-list");
            Files.move(elementList, packageList);
        }
    }
}
