/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6411310
 * @summary JSR 199: FileObject should support user-friendly names via getName()
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 * @modules java.compiler
 *          jdk.compiler
 * @build ToolTester
 * @compile T6411310.java
 * @run main T6411310
 */

import java.io.IOException;
import javax.tools.JavaFileObject;
import static javax.tools.StandardLocation.PLATFORM_CLASS_PATH;
import static javax.tools.StandardLocation.CLASS_PATH;
import static javax.tools.JavaFileObject.Kind.CLASS;

// Limited test while we wait for 6419926: 6419926 is now closed

public class T6411310 extends ToolTester {

    void test(String... args) throws IOException {
        JavaFileObject file = fm.getJavaFileForInput(PLATFORM_CLASS_PATH,
                                                     "java.lang.Object",
                                                     CLASS);
        String fileName = file.getName();
        if (!fileName.matches(".*java/lang/Object.class\\)?")) {
            System.err.println(fileName);
            throw new AssertionError(file);
        }
    }

    public static void main(String... args) throws IOException {
        try (T6411310 t = new T6411310()) {
            t.test(args);
        }
    }
}
