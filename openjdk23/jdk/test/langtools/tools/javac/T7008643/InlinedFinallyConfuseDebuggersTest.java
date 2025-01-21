/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 7008643
 * @summary inlined finally clauses confuse debuggers
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main InlinedFinallyConfuseDebuggersTest
 */

import java.io.File;
import java.nio.file.Paths;

import java.lang.classfile.*;
import java.lang.classfile.attribute.*;
import com.sun.tools.javac.util.Assert;

import toolbox.JavacTask;
import toolbox.ToolBox;

public class InlinedFinallyConfuseDebuggersTest {

    static final String testSource =
    /* 01 */        "public class InlinedFinallyTest {\n" +
    /* 02 */        "    void lookForThisMethod(int value) {\n" +
    /* 03 */        "        try {\n" +
    /* 04 */        "            if (value > 0) {\n" +
    /* 05 */        "                System.out.println(\"if\");\n" +
    /* 06 */        "                return;\n" +
    /* 07 */        "            }\n" +
    /* 08 */        "        } finally {\n" +
    /* 09 */        "            System.out.println(\"finally\");\n" +
    /* 10 */        "        }\n" +
    /* 11 */        "    }\n" +
    /* 12 */        "}";

    static final int[][] expectedLNT = {
    //  {line-number, start-pc},
        {4,           0},       //if (value > 0) {
        {5,           4},       //    System.out.println("if");
        {9,           12},      //System.out.println("finally");
        {6,           20},      //    return;
        {9,           21},      //System.out.println("finally");
        {10,          29},
        {9,           32},      //System.out.println("finally");
        {10,          41},      //}
        {11,          43},
    };

    static final String methodToLookFor = "lookForThisMethod";

    public static void main(String[] args) throws Exception {
        new InlinedFinallyConfuseDebuggersTest().run();
    }

    ToolBox tb = new ToolBox();

    void run() throws Exception {
        compileTestClass();
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "InlinedFinallyTest.class").toUri()), methodToLookFor);
    }

    void compileTestClass() throws Exception {
        new JavacTask(tb)
                .sources(testSource)
                .run();
    }

    void checkClassFile(final File cfile, String methodToFind) throws Exception {
        ClassModel classFile = ClassFile.of().parse(cfile.toPath());
        boolean methodFound = false;
        for (MethodModel m : classFile.methods()) {
            if (m.methodName().equalsString(methodToFind)) {
                methodFound = true;
                CodeAttribute code = m.findAttribute(Attributes.code()).orElseThrow();
                LineNumberTableAttribute lnt = code.findAttribute(Attributes.lineNumberTable()).orElseThrow();
                Assert.check(lnt.lineNumbers().size() == expectedLNT.length,
                        "The LineNumberTable found has a length different to the expected one");
                int i = 0;
                for (LineNumberInfo entry: lnt.lineNumbers()) {
                    Assert.check(entry.lineNumber() == expectedLNT[i][0] &&
                            entry.startPc() == expectedLNT[i][1],
                            "LNT entry at pos " + i + " differ from expected." +
                            "Found " + entry.lineNumber() + ":" + entry.startPc() +
                            ". Expected " + expectedLNT[i][0] + ":" + expectedLNT[i][1]);
                    i++;
                }
            }
        }
        Assert.check(methodFound, "The seek method was not found");
    }

    void error(String msg) {
        throw new AssertionError(msg);
    }

}
