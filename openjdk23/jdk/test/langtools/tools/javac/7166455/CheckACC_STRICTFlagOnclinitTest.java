/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 7166455
 * @summary javac doesn't set ACC_STRICT bit on <clinit> for strictfp class
 * @library /tools/lib /test/lib
 */

import jdk.test.lib.compiler.CompilerUtils;
import toolbox.ToolBox;

import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.MethodModel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class CheckACC_STRICTFlagOnclinitTest {
    private static final String AssertionErrorMessage =
        "All methods should have the ACC_STRICT access flag " +
        "please check output";
    private static final String offendingMethodErrorMessage =
        "Method %s of class %s doesn't have the ACC_STRICT access flag";

    private static final String SOURCE = """
            public strictfp class Test {
                static {
                    class Foo {
                        class Bar {
                            void m11() {}
                        }
                        void m1() {}
                    }
                }
                void m2() {
                    class Any {
                        void m21() {}
                    }
                }
            }
            """;

    private final List<String> errors = new ArrayList<>();

    public static void main(String[] args)
            throws IOException {
        new CheckACC_STRICTFlagOnclinitTest().run();
    }

    private void run()
            throws IOException {
        Path in = Path.of("in");
        Path out = Path.of("out");
        ToolBox toolBox = new ToolBox();
        toolBox.writeJavaFiles(in, SOURCE);
        CompilerUtils.compile(in, out, "--release", "16");
        check(out,
              "Test.class",
              "Test$1Foo.class",
              "Test$1Foo$Bar.class",
              "Test$1Any.class");
        if (!errors.isEmpty()) {
            for (String error: errors) {
                System.err.println(error);
            }
            throw new AssertionError(AssertionErrorMessage);
        }
    }

    void check(Path dir, String... fileNames) throws IOException {
        for (String fileName : fileNames) {
            ClassModel classFileToCheck = ClassFile.of().parse(dir.resolve(fileName));

            for (MethodModel method : classFileToCheck.methods()) {
                if ((method.flags().flagsMask() & ClassFile.ACC_STRICT) == 0) {
                    errors.add(String.format(offendingMethodErrorMessage,
                            method.methodName().stringValue(),
                            classFileToCheck.thisClass().asInternalName()));
                }
            }
        }
    }
}
