/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6421756
 * @summary 6421756 JSR 199: In the method JavaCompilerTool.getTask 'options' can be supplied in the place of 'classes'
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 * @modules java.compiler
 *          jdk.compiler
 * @build ToolTester
 * @compile T6421756.java
 * @run main T6421756
 */

import java.io.IOException;
import java.util.Collections;

public class T6421756 extends ToolTester {
    void test(String... args) {
        Iterable<String> options = Collections.singleton("-verbose");
        try {
            task = tool.getTask(null, fm, null, null, options, null);
            throw new AssertionError("Expected IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: got expected error " + e.getLocalizedMessage());
        }
    }
    public static void main(String... args) throws IOException {
        try (T6421756 t = new T6421756()) {
            t.test(args);
        }
    }
}
