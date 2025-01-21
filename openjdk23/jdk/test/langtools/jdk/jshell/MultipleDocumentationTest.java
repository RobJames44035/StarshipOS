/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import jdk.jshell.JShell;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

/*
 * @test
 * @bug 8274734
 * @summary Verify multiple SourceCodeAnalysis instances can concurrently provide documentation.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 *          jdk.jshell/jdk.internal.jshell.tool
 * @build Compiler toolbox.ToolBox
 * @run testng MultipleDocumentationTest
 */
@Test
public class MultipleDocumentationTest {

    public void testMultipleDocumentation() {
        String input = "java.lang.String";

        try (var state1 = JShell.builder()
                                .out(new PrintStream(new ByteArrayOutputStream()))
                                .err(new PrintStream(new ByteArrayOutputStream()))
                                .build()) {
            var sca1 = state1.sourceCodeAnalysis();
            List<String> javadocs1 =
                    sca1.documentation(input, input.length(), true)
                        .stream()
                        .map(d -> d.javadoc())
                        .collect(Collectors.toList());

            try (var state2 = JShell.builder()
                                    .out(new PrintStream(new ByteArrayOutputStream()))
                                    .err(new PrintStream(new ByteArrayOutputStream()))
                                    .build()) {
                var sca2 = state2.sourceCodeAnalysis();
                List<String> javadocs2 = sca2.documentation(input, input.length(), true)
                                             .stream()
                                             .map(d -> d.javadoc())
                                             .collect(Collectors.toList());

                assertEquals(javadocs2, javadocs1);
            }
        }
    }

}
