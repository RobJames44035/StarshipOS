/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8019486 8026861 8027142
 * @summary javac, generates erroneous LVT for a test case with lambda code
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main WrongLNTForLambdaTest
 */

import java.io.File;
import java.nio.file.Paths;

import com.sun.tools.javac.util.Assert;

import java.lang.classfile.*;
import java.lang.classfile.attribute.*;
import toolbox.JavacTask;
import toolbox.ToolBox;

public class WrongLNTForLambdaTest {

    static final String testSource =
    /* 01 */        "import java.util.List;\n" +
    /* 02 */        "import java.util.Arrays;\n" +
    /* 03 */        "import java.util.stream.Collectors;\n" +
    /* 04 */        "\n" +
    /* 05 */        "public class Foo {\n" +
    /* 06 */        "    void bar(int value) {\n" +
    /* 07 */        "        final List<Integer> numbers = Arrays.asList(1, 2, 3);\n" +
    /* 08 */        "        final List<Integer> numbersPlusOne = \n" +
    /* 09 */        "             numbers.stream().map(number -> number / 1).collect(Collectors.toList());\n" +
    /* 10 */        "    }\n" +
    /* 11 */        "    void variablesInLambdas(int value) {\n" +
    /* 12 */        "        Runnable r1 = () -> {\n" +
    /* 13 */        "            int i  = value;\n" +
    /* 14 */        "            class FooBar<T extends CharSequence> {\n" +
    /* 15 */        "                public void run() {\n" +
    /* 16 */        "                    T t = null;\n" +
    /* 17 */        "                }\n" +
    /* 18 */        "            }\n" +
    /* 19 */        "        };\n" +
    /* 20 */        "        Runnable r2 = () -> System.err.println(1);\n" +
    /* 21 */        "        Runnable r3 = (Runnable & java.io.Serializable) this::foo;\n" +
    /* 22 */        "        Runnable r4 = super :: notify;\n" +
    /* 23 */        "    }\n" +
    /* 24 */        "    private void foo() {}\n" +
    /* 25 */        "    void assignLambda() {\n" +
    /* 26 */        "        Runnable r = () -> { };\n" +
    /* 27 */        "    }\n" +
    /* 28 */        "    void callLambda(int i, Runnable r) {\n" +
    /* 29 */        "        callLambda(0,\n" +
    /* 30 */        "                   () -> { });\n" +
    /* 31 */        "    }\n" +
    /* 32 */        "}";

    static final int[][] simpleLambdaExpectedLNT = {
    //  {line-number, start-pc},
        {9,           0},       //number -> number / 1
    };

    static final int[][] lambdaWithVarsExpectedLNT = {
    //  {line-number, start-pc},
        {13,           0},       //number -> number / 1
        {19,           2},       //number -> number / 1
    };

    static final int[][] insideLambdaWithVarsExpectedLNT = {
    //  {line-number, start-pc},
        {16,           0},       //number -> number / 1
        {17,           2},       //number -> number / 1
    };

    static final int[][] lambdaVoid2VoidExpectedLNT = {
    //  {line-number, start-pc},
        {20,           0},       //number -> number / 1
    };

    static final int[][] deserializeExpectedLNT = {
    //  {line-number, start-pc},
        {05,           0},       //number -> number / 1
    };

    static final int[][] lambdaBridgeExpectedLNT = {
    //  {line-number, start-pc},
        {22,           0},       //number -> number / 1
    };

    static final int[][] assignmentExpectedLNT = {
    //  {line-number, start-pc},
        {26,           0},       //number -> number / 1
        {27,           6},       //number -> number / 1
    };

    static final int[][] callExpectedLNT = {
    //  {line-number, start-pc},
        {29,           0},       //number -> number / 1
        {31,           10},       //number -> number / 1
    };

    public static void main(String[] args) throws Exception {
        new WrongLNTForLambdaTest().run();
    }

    ToolBox tb = new ToolBox();

    void run() throws Exception {
        compileTestClass();
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "Foo.class").toUri()), "lambda$bar$0", simpleLambdaExpectedLNT);
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "Foo.class").toUri()), "lambda$variablesInLambdas$0", lambdaWithVarsExpectedLNT);
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "Foo$1FooBar.class").toUri()), "run", insideLambdaWithVarsExpectedLNT);
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "Foo.class").toUri()), "lambda$variablesInLambdas$1", lambdaVoid2VoidExpectedLNT);
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "Foo.class").toUri()), "$deserializeLambda$", deserializeExpectedLNT);
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "Foo.class").toUri()), "lambda$variablesInLambdas$2", lambdaBridgeExpectedLNT);
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "Foo.class").toUri()), "assignLambda", assignmentExpectedLNT);
        checkClassFile(new File(Paths.get(System.getProperty("user.dir"),
                "Foo.class").toUri()), "callLambda", callExpectedLNT);
    }

    void compileTestClass() throws Exception {
        new JavacTask(tb)
                .sources(testSource)
                .run();
    }

    void checkClassFile(final File cfile, String methodToFind, int[][] expectedLNT) throws Exception {
        ClassModel classFile = ClassFile.of().parse(cfile.toPath());
        boolean methodFound = false;
        for (MethodModel method : classFile.methods()) {
            if (method.methodName().equalsString(methodToFind)) {
                methodFound = true;
                CodeAttribute code = method.findAttribute(Attributes.code()).orElseThrow();
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
