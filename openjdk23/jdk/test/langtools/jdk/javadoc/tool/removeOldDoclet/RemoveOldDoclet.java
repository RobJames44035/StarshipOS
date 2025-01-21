/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8215584
 * @summary Remove support for the "old" doclet API in com/sun/javadoc
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.* toolbox.ToolBox builder.ClassBuilder
 * @run main RemoveOldDoclet
 */


import java.nio.file.Path;
import java.nio.file.Paths;

import builder.ClassBuilder;
import toolbox.ToolBox;

import javadoc.tester.JavadocTester;

public class RemoveOldDoclet extends JavadocTester {

    final ToolBox tb;
    static final String Doclet_CLASS_NAME = TestDoclet.class.getName();

    public static void main(String... args) throws Exception {
        var tester = new RemoveOldDoclet();
        tester.runTests();
    }

    RemoveOldDoclet() {
        tb = new ToolBox();
    }

    @Test
    public void testInvalidDoclet(Path base) throws Exception {
        Path srcDir = base.resolve("src");
        Path outDir = base.resolve("out");

        new ClassBuilder(tb, "pkg.A")
                .setModifiers("public", "class")
                .write(srcDir);

        javadoc("-d", outDir.toString(),
                "-doclet", Doclet_CLASS_NAME,
                "-docletpath", System.getProperty("test.classes", "."),
                "-sourcepath", srcDir.toString(),
                "pkg");

        checkExit(Exit.ERROR);
        checkOutput(Output.OUT, true, String.format("""
                error: Class %s is not a valid doclet.
                  Note: As of JDK 13, the com.sun.javadoc API is no longer supported.""",
                Doclet_CLASS_NAME));
    }

    static class TestDoclet {
        public static boolean start() {
            System.out.println("OLD_DOCLET_MARKER");
            return true;
        }
    }
}
