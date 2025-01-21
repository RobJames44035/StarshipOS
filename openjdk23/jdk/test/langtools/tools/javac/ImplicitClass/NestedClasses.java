/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8315458
 * @enablePreview
 * @summary Make sure nesting classes don't create symbol conflicts with implicit name.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main NestedClasses
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import toolbox.ToolBox;
import toolbox.JavaTask;
import toolbox.JavacTask;
import toolbox.Task;

public class NestedClasses {
    private static ToolBox TOOLBOX = new ToolBox();
    private static final String JAVA_VERSION = System.getProperty("java.specification.version");

    public static void main(String... arg) throws IOException {
        compPass("A.java", """
            void main() {}
            class A {} // okay
            """);

        compPass("A.java", """
            void main() {}
            class B {
               class A { } // okay
            }
            """);

        compFail("A.java", """
            void main() {}
            class B {
               class B { } //error
            }
            """);
    }

    /*
     * Test source for successful compile.
     */
    static void compPass(String fileName, String code) throws IOException {
        Path path = Path.of(fileName);
        Files.writeString(path, code);
        String output = new JavacTask(TOOLBOX)
                .files(List.of(path))
                .classpath(".")
                .options("-encoding", "utf8", "--enable-preview", "-source", JAVA_VERSION)
                .run()
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);

        if (output.contains("compiler.err")) {
            throw new RuntimeException("Error detected");
        }
    }

    /*
     * Test source for unsuccessful compile and specific error.
     */
    static void compFail(String fileName, String code) throws IOException {
        Path path = Path.of(fileName);
        Files.writeString(path, code);
        String output = new JavacTask(TOOLBOX)
                .files(List.of(path))
                .classpath(".")
                .options("-XDrawDiagnostics", "-encoding", "utf8", "--enable-preview", "-source", JAVA_VERSION)
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);

        if (!output.contains("compiler.err")) {
            throw new RuntimeException("No error detected");
        }
    }

 }
