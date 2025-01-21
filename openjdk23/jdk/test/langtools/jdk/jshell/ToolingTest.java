/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8306560
 * @summary Tests for snippets and methods defined in TOOLING.jsh
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 *          jdk.jshell/jdk.internal.jshell.tool
 * @build KullaTesting TestingInputStream
 * @run testng ToolingTest
 */

import org.testng.Assert;
import org.testng.annotations.Test;

public class ToolingTest extends ReplToolTesting {
    @Test
    public void testListToolingSnippets() {
        test(
                a -> assertCommand(a, "/open TOOLING",
                        ""),
                a -> assertCommandOutputContains(a, "/list",
                        // Tool methods
                        "void jar(String... args)",
                        // ...
                        "void jpackage(String... args)",
                        // Utility methods
                        "void javap(Class<?> type) throws Exception",
                        "void run(String name, String... args)",
                        "void tools()")
        );
    }

    @Test
    public void testDisassembleJavaLangObjectClass() {
        test(
                a -> assertCommand(a, "/open TOOLING",
                        ""),
                a -> assertCommandUserOutputContains(a, "javap(Object.class)",
                        "Classfile jrt:/java.base/java/lang/Object.class",
                        "SourceFile: \"Object.java\"")
        );
    }

    @Test
    public void testDisassembleNewRecordClass() {
        test(
                a -> assertCommand(a, "record Point(int x, int y) {}",
                        "|  created record Point"),
                a -> assertCommand(a, "/open TOOLING",
                        ""),
                a -> assertCommandUserOutputContains(a, "javap(Point.class)",
                        "Classfile ", // Classfile /.../TOOLING-13366652659767559204.class
                        "Point extends java.lang.Record", // public final class REPL.$JShell$11$Point extends java.lang.Record
                        "SourceFile: \"$JShell$" // SourceFile: "$JShell$11.java"
                )
        );
    }
}
