/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8046060
 * @summary Different results of floating point multiplication for lambda code block
 * @library /tools/lib /test/lib
 * @run main LambdaTestStrictFPFlag
 */

import jdk.test.lib.compiler.CompilerUtils;
import toolbox.ToolBox;

import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.MethodModel;
import java.nio.file.Path;

public class LambdaTestStrictFPFlag {
    private static final String SOURCE = """
            class Test {
                strictfp void test() {
                    Face itf = () -> { };
                }
            }

            interface Face {
                void m();
            }
            """;

    public static void main(String[] args) throws Exception {
        new LambdaTestStrictFPFlag().run();
    }

    void run() throws Exception {
        Path src = Path.of("src");
        Path out = Path.of("out");

        ToolBox toolBox = new ToolBox();
        toolBox.writeJavaFiles(src, SOURCE);
        CompilerUtils.compile(src, out, "--release", "16");

        ClassModel cm = ClassFile.of().parse(out.resolve("Test.class"));
        boolean found = false;
        for (MethodModel meth: cm.methods()) {
            if (meth.methodName().stringValue().startsWith("lambda$")) {
                if ((meth.flags().flagsMask() & ClassFile.ACC_STRICT) == 0){
                    throw new Exception("strict flag missing from lambda");
                }
                found = true;
            }
        }
        if (!found) {
            throw new Exception("did not find lambda method");
        }
    }
}
