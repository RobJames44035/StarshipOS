/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8258662
 * @summary Types.isCastable crashes when involving sealed interface and type variable.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main T8258662
 */

import java.util.List;

import toolbox.ToolBox;
import toolbox.TestRunner;
import toolbox.JavacTask;

public class T8258662 extends TestRunner {
    private ToolBox tb;

    public T8258662() {
        super(System.err);
        tb = new ToolBox();
    }

    public static void main(String[] args) throws Exception {
        T8258662 t = new T8258662();
        t.runTests();
    }

    @Test
    public void testSealedClassIsCastable() throws Exception {
        String code = """
                class Test8258662 {
                    sealed interface I<T> {
                        final class C implements I<Object> { }
                    }
                    static <T extends I<Object>> void f(T x) {
                        if (x instanceof I<Object>) { }
                    }
                }""";
        new JavacTask(tb)
                .sources(code)
                .classpath(".")
                .run();
    }

}
