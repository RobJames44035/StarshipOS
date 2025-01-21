/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8199307
 * @summary NPE in jdk.javadoc.internal.doclets.toolkit.util.Utils.getLineNumber
 * @library /tools/lib ../../lib
 * @modules
 *      jdk.javadoc/jdk.javadoc.internal.tool
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build javadoc.tester.*
 * @run main TestSerializedFormWithClassFile
 */

import builder.ClassBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

import builder.ClassBuilder.MethodBuilder;
import toolbox.ToolBox;
import toolbox.JavacTask;

import javadoc.tester.JavadocTester;

public class TestSerializedFormWithClassFile extends JavadocTester {

    final ToolBox tb;

    public static void main(String... args) throws Exception {
        var tester = new TestSerializedFormWithClassFile();
        tester.runTests();
    }

    TestSerializedFormWithClassFile() {
        tb = new ToolBox();
    }

    @Test
    public void test(Path base) throws Exception {
        Path srcDir = base.resolve("src");
        createTestClass(base, srcDir);

        Path outDir = base.resolve("out");
        javadoc("-d", outDir.toString(),
                "-linksource",
                "--no-platform-links",
                "-classpath", base.resolve("classes").toString(),
                "-sourcepath", "",
                srcDir.resolve("B.java").toString());

        checkExit(Exit.OK);

        checkOutput("serialized-form.html", true,
                """
                    <div class="member-signature"><span class="modifiers">public</span>&nbsp;<span c\
                    lass="return-type">void</span>&nbsp;<span class="element-name">readObject</span>\
                    <wbr><span class="parameters">(java.io.ObjectInputStream&nbsp;arg0)</span>
                                    throws <span class="exceptions">java.lang.ClassNotFoundException,
                    java.io.IOException</span></div>
                    """);
    }

    void createTestClass(Path base, Path srcDir) throws Exception {
        //create A.java , compile the class in classes dir
        Path classes = base.resolve("classes");
        tb.createDirectories(classes);

        MethodBuilder method = MethodBuilder
                .parse("public void readObject(ObjectInputStream s)"
                        + "throws ClassNotFoundException, IOException {}")
                .setComments(
                    "@param s a serialization stream",
                    "@throws ClassNotFoundException if class not found",
                    "@throws java.io.IOException if an IO error",
                    "@serial");

        new ClassBuilder(tb, "A")
                .setModifiers("public", "abstract", "class")
                .addImplements("Serializable")
                .addImports("java.io.*")
                .addMembers(method)
                .write(srcDir);
        new JavacTask(tb).files(srcDir.resolve("A.java")).outdir(classes).run();

        new ClassBuilder(tb, "B")
                .setExtends("A")
                .setModifiers("public", "class")
                .write(srcDir);
    }
}
