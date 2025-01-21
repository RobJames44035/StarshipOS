/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6257235
 * @summary setOption() and setExtendedOption() of JavacTool throws NullPointerException for undefined options
 * @author  Peter von der Ah\u00e9
 * @modules java.compiler
 *          jdk.compiler
 */

import java.util.Arrays;
import javax.tools.*;

public class T6257235 {
    public static void main(String... args) {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        try {
            javac.getTask(null, null, null, Arrays.asList("seetharam", "."), null, null);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) { }
    }
}
