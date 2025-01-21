/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8207214
 * @summary Test package-level at-serial tags
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.* toolbox.ToolBox
 * @run main TestSerialTag
 */

import java.io.IOException;
import java.nio.file.Path;

import toolbox.ToolBox;

import javadoc.tester.JavadocTester;

public class TestSerialTag extends JavadocTester {
    public static void main(String... args) throws Exception {
        var tester = new TestSerialTag();
        tester.runTests();
    }

    private final ToolBox tb;

    TestSerialTag() {
        tb = new ToolBox();
    }

    @Test
    public void testCombo(Path base) throws IOException {
        boolean[] moduleValues = { false, true };
        String[] tagValues = { "", "@serial include", "@serial exclude" };
        for (boolean module : moduleValues ) {
            for (String tag : tagValues ) {
                String name = (module ? "module-" : "")
                              + (tag.isEmpty() ? "default" : tag.replace("@serial ", ""));
                Path dir = base.resolve(name);
                test(dir, module, tag);
            }
        }

    }

    void test(Path base, boolean module, String tag) throws IOException {
        out.println("Test: module:" + module + ", tag:" + tag);

        Path srcDir = generateSource(base, module, tag);

        Path outDir = base.resolve("out");
        if (module) {
            javadoc("-d", outDir.toString(),
                "--module-source-path", srcDir.toString(),
                "--module", "m");
        } else {
            javadoc("-d", outDir.toString(),
                "-sourcepath", srcDir.toString(),
                "p", "q");
        }
        checkExit(Exit.OK);

        boolean expectLink = !tag.equals("@serial exclude");
        checkSeeSerializedForm(module, expectLink);
    }

    /**
     * Generates source for a test case.
     * Two classes are generated, in two different packages.
     * One package has a variable at-serial tag to test;
     * The other package is a control and always has no special tag.
     *
     * @param base the working directory for the test case
     * @param module whether or not to enclose the packages in a module
     * @param tag the at-serial tag to be tested
     * @return the directory in which the source was created
     */
    Path generateSource(Path base, boolean module, String tag) throws IOException {
        Path srcDir = base.resolve("src");

        Path dir;
        if (module) {
            dir = srcDir.resolve("m");
            tb.writeJavaFiles(dir,
                "module m { exports p; exports q; }");
        } else {
            dir = srcDir;
        }

        tb.writeJavaFiles(dir,
            "/** This is package p;\n * " + tag + "\n */\n"
            + "package p;",
            """
                /** This is class p.C1;
                 */
                package p; public class C1 implements java.io.Serializable { }""",
            """
                /** This is package q;
                 */
                package q;""",
            """
                /** This is class q.C2;
                 */
                package q; public class C2 implements java.io.Serializable { }"""
        );

        return srcDir;
    }

    /**
     * Checks the link to the serialized form page,
     * and whether classes are documented on that page.
     *
     * @param module whether or not the output is module-oriented
     * @param b whether or not class p.C1 should be documented as serializable
     */
    void checkSeeSerializedForm(boolean module, boolean b) {
        String prefix = module ? "m/" : "";

        checkOutput(prefix + "p/C1.html", b,
            "serialized-form.html");
        checkOutput("serialized-form.html", b,
            "C1");

        checkOutput(prefix + "q/C2.html", true,
            "serialized-form.html");
        checkOutput("serialized-form.html", true,
            "C2");
    }
}

