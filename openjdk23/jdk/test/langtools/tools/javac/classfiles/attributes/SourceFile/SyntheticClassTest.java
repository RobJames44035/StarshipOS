/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary sourcefile attribute test for synthetic class.
 * @bug 8040129
 * @library /tools/lib /tools/javac/lib /test/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestBase SourceFileTestBase
 * @run main SyntheticClassTest
 */

import jdk.test.lib.compiler.CompilerUtils;
import toolbox.ToolBox;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class SyntheticClassTest extends SourceFileTestBase {
    public static void main(String[] args) throws Exception {
        String sourceCode = """
                public class SyntheticClass {
                    static class Inner {
                        private Inner() {
                        }
                    }

                    public SyntheticClass() {
                        new Inner();
                    }
                }
                """;
        Path srcDir = Path.of("src");
        Path v10Dir = Path.of("out10");
        Path modernDir = Path.of("out");
        ToolBox toolBox = new ToolBox();
        toolBox.writeJavaFiles(srcDir, sourceCode);
        CompilerUtils.compile(srcDir, v10Dir, "--release", "10");
        CompilerUtils.compile(srcDir, modernDir);
        test(v10Dir, true);
        test(modernDir, false);
    }

    private static void test(Path path, boolean expectSynthetic) throws Exception {
        try {
            new SyntheticClassTest().test(path.resolve("SyntheticClass$1.class"), "SyntheticClass.java");
            if (!expectSynthetic) {
                throw new AssertionError("Synthetic class should not have been emitted!");
            }
        } catch (NoSuchFileException ex) {
            if (expectSynthetic) {
                throw new AssertionError("Synthetic class should have been emitted!");
            }
        }
    }
}
