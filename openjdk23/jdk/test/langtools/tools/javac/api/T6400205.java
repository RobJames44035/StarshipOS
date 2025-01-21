/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6400205
 * @summary getClassLoader(location) returns null if getLocation(location) returns null
 * @author  Peter von der Ah\u00e9
 * @modules java.compiler
 *          jdk.compiler
 */

import java.io.IOException;
import javax.tools.*;
import static javax.tools.StandardLocation.*;

public class T6400205 {
    public static void main(String... args) throws IOException {
        try (JavaFileManager fm =
                ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null)) {
            try {
                fm.getClassLoader(null);
                throw new AssertionError("NullPointerException not thrown");
            } catch (NullPointerException e) {
                // expected result
            }
            ClassLoader cl = fm.getClassLoader(locationFor("bogus"));
            if (cl != null)
                throw new AssertionError("non-null class loader for bogus location");
            System.err.println("Test PASSED.");
        }
    }
}
