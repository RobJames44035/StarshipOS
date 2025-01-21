/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary sourcefile attribute test for two type in one file.
 * @bug 8040129
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestBase SourceFileTestBase
 * @run main TopLevelClassesOneFileTest
 */

public class TopLevelClassesOneFileTest extends SourceFileTestBase {
    public static void main(String[] args) throws Exception {
        new TopLevelClassesOneFileTest().run();
    }

    public void run() throws Exception {
        int failed = 0;
        for (Type firstType : Type.values()) {
            for (Type secondType : Type.values()) {
                if (firstType != secondType) {
                    try {
                        compileAndTest("public " + firstType.source + secondType.source,
                                firstType.name(), secondType.name());
                    } catch (AssertionFailedException | CompilationException ex) {
                        System.err.println("Failed with public type " + firstType.name()
                                + " and type " + secondType.name());
                        ex.printStackTrace();
                        failed++;
                    }
                }
            }
        }
        if (failed > 0)
            throw new AssertionFailedException("Test failed. Failed cases count = " + failed + " .See log.");
    }

    enum Type {
        CLASS("class CLASS{}"),
        INTERFACE("interface INTERFACE{}"),
        ENUM("enum ENUM{;}"),
        ANNOTATION("@interface ANNOTATION{}");

        String source;

        Type(String source) {
            this.source = source;
        }
    }
}
