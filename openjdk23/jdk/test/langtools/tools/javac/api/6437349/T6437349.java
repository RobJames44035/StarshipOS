/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6437349
 * @summary JSR 199: JavaFileObject.isNameCompatible() will give true with some incompatible kinds
 * @library ../lib
 * @modules java.compiler
 *          jdk.compiler
 * @build ToolTester
 * @compile T6437349.java
 * @run main T6437349
 */

import java.io.IOException;
import javax.tools.*;
import static javax.tools.StandardLocation.*;
import static javax.tools.JavaFileObject.Kind.*;

public class T6437349 extends ToolTester {
    void test(String... args) throws IOException {
        task = tool.getTask(null, fm, null, null, null, null);
        JavaFileObject fo = fm.getJavaFileForInput(SOURCE_PATH, "T6437349", SOURCE);
        if (fo.isNameCompatible("T6437349.java", OTHER))
            throw new AssertionError();
        if (!fo.isNameCompatible("T6437349", SOURCE))
            throw new AssertionError();
        fo = fm.getJavaFileForInput(PLATFORM_CLASS_PATH, "java.lang.Object", CLASS);
        if (fo.isNameCompatible("Object.class", OTHER))
            throw new AssertionError();
        if (!fo.isNameCompatible("Object", CLASS))
            throw new AssertionError();
    }
    public static void main(String... args) throws IOException {
        try (T6437349 t = new T6437349()) {
            t.test(args);
        }
    }
}
