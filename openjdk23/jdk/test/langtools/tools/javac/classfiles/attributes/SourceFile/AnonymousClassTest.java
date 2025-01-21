/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @summary sourcefile attribute test for anonymous class.
 * @bug 8040129
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestBase SourceFileTestBase
 * @run main AnonymousClassTest
 */

public class AnonymousClassTest extends SourceFileTestBase {
    public static void main(String[] args) throws Exception {
        new AnonymousClassTest().test(new Object(){}.getClass(), "AnonymousClassTest.java");
    }
}
