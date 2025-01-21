/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 6493690 8246774
 * @summary javadoc should have a javax.tools.Tool service provider
 * @modules java.compiler
 *          jdk.compiler
 * @build APITest
 * @run main GetTask_DiagListenerTest
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DocumentationTool;
import javax.tools.DocumentationTool.DocumentationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * Tests for DocumentationTool.getTask  diagnosticListener  parameter.
 */
public class GetTask_DiagListenerTest extends APITest {
    public static void main(String... args) throws Exception {
        new GetTask_DiagListenerTest().run();
    }

    /**
     * Verify that a diagnostic listener can be specified.
     * Note that messages from the tool and doclet are imperfectly modeled
     * because the Reporter API works in terms of localized strings.
     * Therefore, messages reported via Reporter are simply wrapped and passed through.
     */
    @Test
    public void testDiagListener() throws Exception {
        JavaFileObject srcFile = createSimpleJavaFileObject("pkg/C", "package pkg; public error { }");
        DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
        try (StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null)) {
            File outDir = getOutDir();
            fm.setLocation(DocumentationTool.Location.DOCUMENTATION_OUTPUT, Arrays.asList(outDir));
            Iterable<? extends JavaFileObject> files = Arrays.asList(srcFile);
            DiagnosticCollector<JavaFileObject> dc = new DiagnosticCollector<JavaFileObject>();
            DocumentationTask t = tool.getTask(null, fm, dc, null, null, files);
            if (t.call()) {
                throw new Exception("task succeeded unexpectedly");
            } else {
                List<String> diagCodes = new ArrayList<String>();
                for (Diagnostic d: dc.getDiagnostics()) {
                    System.err.println("[" + d.getCode() + "]: " + d);
                    diagCodes.add(d.getCode());
                }
                List<String> expect = Arrays.asList(
                        "compiler.err.expected4",   // class, interface, enum, or record expected
                        "javadoc.note.message");    // 1 error
                if (!diagCodes.equals(expect))
                    throw new Exception("unexpected diagnostics occurred");
                System.err.println("diagnostics received as expected");
            }
        }
    }

}

